package app.covidstats.model

import android.content.Context
import android.util.Log
import app.covidstats.model.data.other.Continent
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

    /**
     * Saves location stats, in cache.
     * @param location a pair, containing the name of the location and the acossiated stats.
     */
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
        val file = getFile("covid_stats", location.replace(" ", "_"))
            ?: return null.also { Log.i("Storage", "Location stats for $location not found") }
        val statStr = file.readText()
        val stats = json.decodeFromString<CovidStats>(statStr)
        return (location to stats).also { Log.i("Storage", "Loaded location stats for $location") }
    }

    /**
     * Saves [continent] locations in cache.
     * @param continent the name of the continent, that will be acossiated to [locations]
     * @param locations a list of String, each being a location
     */
    fun saveContinentLocations(continent: Continent, locations: List<String>) {
        val filename = "locations_${continent.name.toLowerCase()}"
        val continentLocationsFile = File.createTempFile(filename, "txt", context.cacheDir)
        continentLocationsFile.writeText(json.encodeToString(locations))
    }

    /**
     * @return the list of location Strings, acossiated to [continent], or null if non existent
     */
    fun getContinentLocations(continent: Continent): List<String>? {
        val file = getFile("locations", continent.name.toLowerCase())
            ?: return null.also { Log.i("Storage", "No locations found for $continent") }
        val continentLocations = file.readText()
        return json.decodeFromString<List<String>?>(continentLocations).also { Log.i("Storage", "Loaded locations for $continent") }
            ?: throw Exception("Couldn't decode from storage cache file")
    }


    /**
     * Fetches a File object, given [prefix] and [suffix].
     * @return the File, if found, or null, otherwise.
     */
    private fun getFile(prefix: String, suffix: String): File? {
        val filename = File(cacheDir).list()?.find {
            it.contains("${prefix}_$suffix")
        } ?: return null
        val cacheFile = File(context.cacheDir, filename)
        if (cacheFile.exists())
            return null
        return cacheFile
    }
}