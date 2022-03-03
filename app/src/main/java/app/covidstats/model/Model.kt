package app.covidstats.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import app.covidstats.db.*
import app.covidstats.model.data.covid_stats.CovidStats
import app.covidstats.model.data.news.Item

class Model() {
    var stats by mutableStateOf<Pair<String, CovidStats>?>(null)

    var news by mutableStateOf<List<Item>?>(null)

    /** List of all continents */
    var continents by mutableStateOf<List<String>?>(null)
    /** List of all countries for a given continent */
    var countries by mutableStateOf<Pair<String, List<String>>?>(null)

    val moreCovidInfo = "https://www.who.int/emergencies/diseases/novel-coronavirus-2019"
    // TODO -> fetch all continents at start

    /**
     * Fetches worldwide COVID-19 stats.
     */
    suspend fun loadWorldCovidStats() {
        val statsResp = getWorldStats()
        stats = if (statsResp != null)
            "World" to statsResp
        else null
    }

    /**
     * Fetches worldwide COVID-19 stats.
     */
    suspend fun loadAllContinents() {
        continents = fetchAllContinents()
    }

    /**
     * Fetches worldwide COVID-19 stats.
     */
    suspend fun loadAllCountries(continent: String) {
        countries = Pair(continent, fetchAllCountries(continent))
    }

    /**
     * Fetches COVID-19 stats for a specific continent.
     */
    suspend fun loadContinentCovidStats(continent: String) {
        val statsResp = getContinentStats(continent)
        stats = if (statsResp != null)
            continent to statsResp
        else null
    }

    /**
     * Fetches COVID-19 stats for a specific country.
     */
    suspend fun loadCountryCovidStats(country: String) {
        val statsResp = getCountryStats(country)
        stats = if (statsResp != null)
            country to statsResp
        else null
    }

    /**
     * Fetches COVID-19 stats for a specific country.
     */
    suspend fun loadCovidNews() {
        news = getCovidNews()
    }

    fun dumpResults() {
        stats = null
    }
}

