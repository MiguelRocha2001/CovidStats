package app.covidstats.model.opers

import android.util.Log
import app.covidstats.db.getContinentStats
import app.covidstats.db.getCountryStats
import app.covidstats.db.getWorldStats
import app.covidstats.error.AppError
import app.covidstats.model.Storage
import app.covidstats.model.data.app.*

/**
 * Fetches COVID-19 stats for a specific continent.
 */
fun loadContinentCovidStats(continent: Continent, callback: (Stats) -> Unit) {
    val statsResp = getContinentStats(continent)
    callback(StatsSuccess(continent.formattedName(), statsResp))
}

/**
 * Sets global variable stats with the obtained stats, given a [location].
 * If the data is already in cache, obtains it from there. Otherwise, requests the API, and stores the data in cache to further use.
 */
fun loadLocationCovidStats(location: String, storage: Storage, callback: (Stats) -> Unit) {
    val localStats = fetchLocationStatsFromStorage(location, storage)
    if (localStats != null) {
        callback(localStats)
        Log.i("Model", "Restoring stats, for $location, from storage")
    } else {
        val stats = fetchSaveAndSetLocationStats(location, storage)
        callback(stats)
    }
}

private fun fetchLocationStatsFromStorage(location: String, storage: Storage): Stats? {
    val stats = storage.getLocationStats(location)
    if (stats != null) {
        val statsSuccess = StatsSuccess(location, stats.second)
        val timeExpired = stats.first.timeExpired()
        return if (!timeExpired) {
            statsSuccess
        } else {
            Log.i("Model", "Stats for $location expired")
            null
        }
    }
    return null
}

private fun fetchSaveAndSetLocationStats(location: String, storage: Storage): Stats {
    Log.i("Model", "Fetching stats, for $location, from API")

    val statsResp = if (location == "World") {
        fetchWorldStats()
    } else {
        val continent = location.toContinent()
        if (continent != null) {
            fetchContinentStats(continent)
        } else {
            fetchCountryStats(location)
        }
    }

    return if (statsResp is StatsSuccess) {
        Log.i("Model", "Stats for $location fetched successfully from API")
        Log.i("Model", "Saving stats, for $location, to storage")
        saveLocationStat(statsResp, storage)
        Log.i("Model", "Saved stats, for $location, to storage")
        statsResp
    } else {
        Log.i("Model", "Stats for $location failed to fetch from API")
        statsResp
    }
}

/**
 * Fetches COVID-19 stats for continent [continent].
 * @param continent Continent
 * @return Stats object that could be either a [StatsSuccess] or a [StatsError]
 */
private fun fetchContinentStats(continent: Continent): Stats {
    return try {
        val statsResp = getContinentStats(continent)
        StatsSuccess(continent.formattedName(), statsResp)
    } catch (e: AppError) {
        StatsError(e, continent.name)
    }
}

/**
 * Fetches COVID-19 stats for country [name].
 * @param name country name
 * @return Stats object that could be either a [StatsSuccess] or a [StatsError]
 */
private fun fetchCountryStats(name: String): Stats {
    return try {
        val statsResp = getCountryStats(name)
        StatsSuccess(name.formattedName(), statsResp)
    } catch (e: AppError) {
        StatsError(e, name)
    }
}

/**
 * Fetches COVID-19 stats for "World".
 * @return Stats object that could be either a [StatsSuccess] or a [StatsError]
 */
private fun fetchWorldStats(): Stats {
    val location = "World"
    return try {
        val statsResp = getWorldStats()
        StatsSuccess(location, statsResp)
    } catch (e: AppError) {
        StatsError(e, location)
    }
}

/**
 * Stores given [locationStats] in cache.
 */
private fun saveLocationStat(locationStats: StatsSuccess, storage: Storage) {
    Log.i("Model", "Saving stats for ${locationStats.location}")
    storage.saveLocationStats(locationStats, TimeZone())
    Log.i("Model", "Saved location stats for ${locationStats.location}")
}