package app.covidstats.model

import android.content.Context
import app.covidstats.model.data.Continent
import app.covidstats.model.data.covid_stats.CovidStats
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File

class Storage(private val context: Context) {
    private val favoritesFile = File(context.filesDir, "covid_stats_favorites.txt")

    private val json = Json { ignoreUnknownKeys = true }

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
        val locationStatsFile = File.createTempFile(filename, "txt", context.cacheDir)
        locationStatsFile.writeText(location.second.toString())
    }

    /**
     * @Returns the location stats stored locally if it exists or null.
     */
    fun getLocationStats(location: String): Pair<String, CovidStats>? {
        val cacheFile = File(context.cacheDir, "covid_stats_$location")
        if (!cacheFile.exists())
            return null
        val statStr = cacheFile.readText()
        return json.decodeFromString(statStr) ?: throw Exception("Couldn't decode from storage cache file")
    }

    fun saveContinentLocations(continent: Continent, locations: List<String>) {
        val filename = "locations_${continent.name.toLowerCase()}"
        val continentLocationsFile = File.createTempFile(filename, "txt", context.cacheDir)
        continentLocationsFile.writeText(locations.toString())
    }

    fun getContinentLocations(continent: Continent): List<String>? {
        val cacheFile = File(context.cacheDir, "locations_${continent.name.toLowerCase()}")
        if (!cacheFile.exists())
            return null
        val continentLocations = cacheFile.readText()
        return json.decodeFromString<List<String>?>(continentLocations)?.toList()
            ?: throw Exception("Couldn't decode from storage cache file")
    }
}