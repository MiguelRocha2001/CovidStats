package app.covidstats.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import app.covidstats.db.*
import app.covidstats.model.data.covid_stats.Country
import app.covidstats.model.data.covid_stats.CovidStats
import app.covidstats.model.data.news.Item
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Model(scope: CoroutineScope) {
    var stats by mutableStateOf<Pair<String, CovidStats>?>(null)

    var news by mutableStateOf<List<Item>?>(null)

    /** List of all continents */
    var continents by mutableStateOf<List<String>?>(null)
    /** List of all countries for a given continent */
    var countries by mutableStateOf<Pair<String, List<String>>?>(null)

    val moreCovidInfo = "https://www.who.int/emergencies/diseases/novel-coronavirus-2019"

    private val storage: Storage = Storage()

    var favoriteCountries by mutableStateOf<List<String>>(emptyList())

    init {
        scope.launch(Dispatchers.IO) {
            storage.init()
            favoriteCountries = storage.getFavoriteCountries() ?: emptyList()
        }
    }

    fun addFavoriteCountry(country: String) {
        val favorites = favoriteCountries.toMutableList()
        favorites.add(country)
        favoriteCountries = favorites
        // stores favorites
        storage.saveFavoriteCountries(favoriteCountries)
    }

    /**
     * Fetches worldwide COVID-19 stats.
     */
    fun loadWorldCovidStats() {
        val statsResp = getWorldStats()
        stats = "World" to statsResp
    }

    /**
     * Fetches worldwide COVID-19 stats.
     */
    fun loadAllContinents() {
        continents = fetchAllContinents()
    }

    /**
     * Fetches worldwide COVID-19 stats.
     */
    fun loadAllCountries(continent: String) {
        countries = Pair(continent, fetchAllCountries(continent))
    }

    /**
     * Fetches COVID-19 stats for a specific continent.
     */
    fun loadContinentCovidStats(continent: String) {
        val statsResp = getContinentStats(continent)
        stats = continent to statsResp
    }

    /**
     * Fetches COVID-19 stats for a specific country.
     */
    fun loadCountryCovidStats(country: String) {
        val statsResp = getCountryStats(country)
        stats = country to statsResp
    }

    /**
     * Fetches COVID-19 stats for a specific country.
     */
    fun loadCovidNews() {
        news = getCovidNews()
    }

    fun dumpResults() {
        stats = null
    }
}

