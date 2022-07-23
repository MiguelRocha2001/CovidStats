package app.covidstats.model.opers

import android.util.Log
import app.covidstats.db.getContinentStats
import app.covidstats.db.getCountryStats
import app.covidstats.db.getWorldStats
import app.covidstats.model.Storage
import app.covidstats.model.data.covid_stats.CovidStats
import app.covidstats.model.data.other.Continent
import app.covidstats.model.data.other.formattedName
import app.covidstats.model.data.other.toContinent
import app.covidstats.model.data.time.TimeZone

/**
 * Fetches COVID-19 stats for a specific continent.
 */
fun loadContinentCovidStats(continent: Continent, callback: (Pair<String, CovidStats>) -> Unit) {
    val statsResp = getContinentStats(continent)
    val stats = continent.formattedName() to statsResp
    callback(stats)
}

/**
 * Sets global variable stats with the obtained stats, given a [location].
 * If the data is already in cache, obtains it from there. Otherwise, requests the API, and stores the data in cache to further use.
 */
fun loadLocationCovidStats(location: String, storage: Storage, callback: (Pair<String, CovidStats>) -> Unit) {
    val localStats = fetchLocationStatsFromStorage(location, storage)
    if (localStats != null) {
        val stats = location to localStats
        callback(stats)
        Log.i("Model", "Restoring stats, for $location, from storage")
    } else {
        val stats = fetchSaveAndSetLocationStats(location, storage)
        callback(stats)
    }
}

private fun fetchLocationStatsFromStorage(location: String, storage: Storage): CovidStats? {
    val stats = storage.getLocationStats(location)
    if (stats != null) {
        val timeExpired = stats.first.timeExpired()
        return if (!timeExpired) {
            stats.second
        } else {
            Log.i("Model", "Stats for $location expired")
            null
        }
    }
    return null
}

private fun fetchSaveAndSetLocationStats(location: String, storage: Storage): Pair<String, CovidStats> {
    Log.i("Model", "Fetching stats, for $location, from API")

    val statsResp = if (location == "World") {
        getWorldStats()
    } else fetchContinentStats(location) ?: getCountryStats(location)

    Log.i("Model", "Stats for $location fetched successfully from API")
    val locStat = location to statsResp
    Log.i("Model", "Saving stats, for $location, to storage")
    saveLocationStat(locStat, storage)
    Log.i("Model", "Saved stats, for $location, to storage")
    return locStat
}

/**
 * Fetches COVID-19 stats for continent [name] or null if name is invalid.
 * @param name continent name
 */
private fun fetchContinentStats(name: String): CovidStats? {
    return if (Model.continents.any { it == name }) {
        val continent = name.toContinent()
            ?: throw IllegalStateException("Invalid continent name: $name")
        getContinentStats(continent)
    } else null
}

/**
 * Stores given [locationStats] in cache.
 */
private fun saveLocationStat(locationStats: Pair<String, CovidStats>, storage: Storage) {
    Log.i("Model", "Saving stats for ${locationStats.first}")
    storage.saveLocationStats(locationStats, TimeZone())
    Log.i("Model", "Saved location stats for ${locationStats.first}")
}