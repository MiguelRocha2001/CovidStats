package app.covidstats.model

import android.content.Context
import android.util.Log
import app.covidstats.model.data.Continent
import app.covidstats.model.data.covid_stats.CovidStats
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class Storage(private val context: Context) {
    private val favoritesFile = File(context.filesDir, "covid_stats_favorites.txt")

    private val json = Json { ignoreUnknownKeys = true }

    private val cacheDir = "/data/user/0/app.covidstats/cache"

    /**
     * Should be called when the app is started and in a coroutine.
     */
    fun init() {
        if (!favoritesFile.exists())
            favoritesFile.createNewFile()
    }

    /**
     * Stores [favoriteCountries] locally.
     */
    fun saveFavoriteCountries(favoriteCountries: List<String>) {
        favoritesFile.writeText(favoriteCountries.toString().trim('[', ']'))
    }

    /**
     * Returns favorite countries stored locally.
     */
    fun getFavoriteCountries(): List<String> {
        return if (favoritesFile.exists()) {
            val fileContent = favoritesFile.readText()
            if (fileContent.isNotEmpty()) {
                fileContent.trim('[', ']').split(",").map { it.trim() }
            } else {
                emptyList()
            }
        } else emptyList()
    }

    fun saveLocationStats(location: Pair<String, CovidStats>) {
        val filename = "covid_stats_${location.first.replace(" ", "_")}"
        val locationStatsFile = File.createTempFile(filename, ".txt", context.cacheDir)
        locationStatsFile.writeText(json.encodeToString(location.second))
        Log.i("Storage", "Saved location stats to ${locationStatsFile.name}")
    }

    /**
     * @Returns the location stats stored locally if it exists or null.
     */
    fun getLocationStats(location: String): Pair<String, CovidStats>? {
        val filename = File(cacheDir).list()?.find {
            it.contains("covid_stats_${location.replace(" ", "_")}")
        } ?: return null.also { Log.i("Storage", "Location stats for $location not found") }
        val cacheFile = File(context.cacheDir, filename)
        if (!cacheFile.exists())
            return null.also { Log.i("Storage", "No location stats found for $location") }
        val statStr = cacheFile.readText()
        val stats = json.decodeFromString<CovidStats>(statStr)
        return (location to stats).also { Log.i("Storage", "Loaded location stats for $location") }
    }

    fun saveContinentLocations(continent: Continent, locations: List<String>) {
        val filename = "locations_${continent.name.toLowerCase()}"
        val continentLocationsFile = File.createTempFile(filename, "txt", context.cacheDir)
        continentLocationsFile.writeText(json.encodeToString(locations))
    }

    fun getContinentLocations(continent: Continent): List<String>? {
        val filename = File(cacheDir).list()?.find {
            it.contains("locations_${continent.name.toLowerCase()}")
        } ?: return null.also { Log.i("Storage", "No locations found for $continent") }
        val cacheFile = File(context.cacheDir, filename)
        if (!cacheFile.exists())
            return null.also { Log.i("Storage", "No locations found for ${continent.name}") }
        val continentLocations = cacheFile.readText()
        return json.decodeFromString<List<String>?>(continentLocations).also { Log.i("Storage", "Loaded locations for $continent") }
            ?: throw Exception("Couldn't decode from storage cache file")
    }
}