package app.covidstats.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import app.covidstats.db.*
import app.covidstats.db.getAllContinents
import app.covidstats.db.getContinentStats
import app.covidstats.db.getCountryStats
import app.covidstats.db.getWorldStats
import app.covidstats.model.data.covid_stats.Stats
import app.covidstats.model.data.news.Collection
import app.covidstats.model.data.news.Item
import app.covidstats.model.data.news.News

class Model() {
    var stats by mutableStateOf<Stats?>(null)
    var location by mutableStateOf<String?>(null)
    var news by mutableStateOf<List<Item>?>(null)
    // TODO -> fetch all continents at start

    /**
     * Fetches worldwide COVID-19 stats.
     */
    suspend fun loadWorldCovidStats() {
        stats = getWorldStats()
        location = "World"
    }

    /**
     * Fetches worldwide COVID-19 stats.
     */
    suspend fun loadAllContinents() = getAllContinents()

    /**
     * Fetches worldwide COVID-19 stats.
     */
    suspend fun loadAllCountries(continent: String) = getAllCountries(continent)

    /**
     * Fetches COVID-19 stats for a specific continent.
     */
    suspend fun loadContinentCovidStats(continent: String) {
        stats = getContinentStats(continent)
        location = continent
    }

    /**
     * Fetches COVID-19 stats for a specific country.
     */
    suspend fun loadCountryCovidStats(country: String) {
        stats = getCountryStats(country)
        location = country
    }

    /**
     * Fetches COVID-19 stats for a specific country.
     */
    suspend fun loadCovidNews() {
        news = getCovidNews()
    }

    fun dumpResults() {
        stats = null
        location = null
    }
}
