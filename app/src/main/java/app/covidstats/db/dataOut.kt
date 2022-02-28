package app.covidstats.db

import app.covidstats.model.data.Continent
import app.covidstats.model.data.Continents
import app.covidstats.model.data.Country
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
 * @return a List with all available continents to fetch stats.
 */
internal suspend fun getAllContinents(): List<String> {
    val call = service.getAllContinents(strict = true)
    if (call.isSuccessful) {
        val continentsRsp = call.body() ?: return emptyList()
        return continentsRsp.map { continent -> continent.continent }
    }
    throw InternalError()
}

/**
 * @return a List with all available continents to fetch stats.
 */
internal suspend fun getAllCountries(continent: String): List<String> =
    getContinentStats(continent)?.countries ?: emptyList()

/**
 * Fetches Covid-19 stats for [continent].
 */
internal suspend fun getContinentStats(continent: String): Continent? {
    val call = service.getContinentStats(continent, strict = true)
    if (call.isSuccessful)
        return call.body()
    throw InternalError()
}

/**
 * Fetches Covid-19 stats for [country].
 */
internal suspend fun getCountryStats(country: String): Country? {
    val call = service.getCountryStats(country, strict = true)
    if (call.isSuccessful)
        return call.body()
    throw InternalError()
}