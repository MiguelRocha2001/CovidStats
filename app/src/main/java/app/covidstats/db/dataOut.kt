package app.covidstats.db

import app.covidstats.model.data.World

val service = RetrofitInstance.api

/**
 * Fetches Covid-19 stats worldwide.
 */
internal suspend fun getWorldStats(): World? {
    val call = service.getWorldStats()
    if (call.isSuccessful)
        return call.body()
    throw InternalError()
}

/**
 * Fetches Covid-19 stats for [continent].
 */
internal suspend fun getContinentStats(continent: String): World? {
    val call = service.getContinentStats(continent)
    if (call.isSuccessful)
        return call.body()
    throw InternalError()
}

/**
 * Fetches Covid-19 stats for [country].
 */
internal suspend fun getCountryStats(country: String): World? {
    val call = service.getCountryStats(country)
    if (call.isSuccessful)
        return call.body()
    throw InternalError()
}