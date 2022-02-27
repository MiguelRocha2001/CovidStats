package app.covidstats.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.github.kittinunf.fuel.Fuel

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

// API for covid-19 stats: https://disease.sh/docs/?urls.primaryName=version%203.0.0
// API for news: https://developer.nytimes.com/docs/articlesearch-product/1/overview

    private const val WORLD_CASES_URI = "https://disease.sh/v3/covid-19/all"

/** @return String URI for covid-19 stats for [continent] */
private fun String.getContinentUri(continent: String) = "https://disease.sh/v3/covid-19/continents/${continent}?strict=true"

/** @return String URI for covid-19 stats for [country] */
private fun String.getCountryUri(country: String) = "https://disease.sh/v3/covid-19/countries/${country}?strict=true"

class Model() {
    var results by mutableStateOf<WorldData?>(null)
    var location by mutableStateOf<String?>(null)

    private val json = Json { ignoreUnknownKeys = true }

    /**
     * Fetches worldwide COVID-19 stats.
     */
    fun loadWorldCovidStats() {
        Fuel.get(WORLD_CASES_URI)
            .response { request, response, result ->
                // to show body response
                // Log.v("response", result.get().decodeToString())
                results = json.decodeFromString<WorldData>(result.get().decodeToString())
                location = "World"
                // to show json object with response
                // Log.v("response", obj.toString())
            }
    }

    /**
     * Fetches COVID-19 stats for a specific continent.
     */
    fun loadContinentCovidStats(continent: String) {
        Fuel.get(String().getContinentUri(continent))
            .response { request, response, result ->
                results = json.decodeFromString<WorldData>(result.get().decodeToString())
                location = continent
            }
    }

    /**
     * Fetches COVID-19 stats for a specific country.
     */
    fun loadCountryCovidStats(country: String) {
        Fuel.get(String().getCountryUri(country))
            .response { request, response, result ->
                results = json.decodeFromString<WorldData>(result.get().decodeToString())
                location = country
            }
    }

    fun dumpResults() {
        results = null
        location = null
    }
}
