package app.covidstats.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import app.covidstats.db.getContinentStats
import app.covidstats.db.getCountryStats
import app.covidstats.db.getWorldStats
import app.covidstats.model.data.Stats

import kotlinx.serialization.json.Json

class Model() {
    var stats by mutableStateOf<Stats?>(null)
    var location by mutableStateOf<String?>(null)

    /**
     * Fetches worldwide COVID-19 stats.
     */
    suspend fun loadWorldCovidStats() {
        stats = getWorldStats()
        location = "World"
    }

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

    fun dumpResults() {
        stats = null
        location = null
    }
}
