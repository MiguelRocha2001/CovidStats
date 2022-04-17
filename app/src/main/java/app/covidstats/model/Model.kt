package app.covidstats.model

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import app.covidstats.db.*
import app.covidstats.model.data.covid_stats.CovidStats
import app.covidstats.model.data.news.Item
import kotlinx.coroutines.CoroutineScope

class Model(context: Context, scope: CoroutineScope) {
    var stats by mutableStateOf<Pair<String, CovidStats>?>(null)

    private val storage: Storage = Storage(context)

    var news by mutableStateOf<List<Item>?>(null)
    /** List of all continents */
    var continents by mutableStateOf<List<String>?>(null)

    /** List of all countries for a given continent */
    var countries by mutableStateOf<Pair<String, List<String>>?>(null)

    val moreCovidInfo = "https://www.who.int/emergencies/diseases/novel-coronavirus-2019"

    var favoriteLocations by mutableStateOf<List<String>>(emptyList())

    init {
        storage.init()
        favoriteLocations = storage.getFavoriteCountries() ?: emptyList()
    }

    fun isCountryOnFavorites(country: String) = favoriteLocations.contains(country)

    /**
     * Adds [location] to the list of favorite locations.
     */
    fun addFavoriteLocation(location: String) {
        val favorites = favoriteLocations.toMutableList()
        favorites.add(location)
        favoriteLocations = favorites
        // stores favorites
        storage.saveFavoriteCountries(favoriteLocations)
    }

    /**
     * Removes [location] (if exists) to the list of favorite locations.
     */
    fun removeFavoriteLocation(location: String) {
        val favorites = favoriteLocations.toMutableList()
        favorites.remove(location)
        favoriteLocations = favorites
        // stores favorites
        storage.saveFavoriteCountries(favoriteLocations)
    }

    /**
     * Fetches worldwide COVID-19 stats.
     */
    fun loadWorldCovidStats() {
        stats = "world" to getWorldStats()
    }

    private fun saveLocationStat(locationStats: Pair<String, CovidStats>) {
        storage.saveLocationStats(locationStats)
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
    fun loadLocationCovidStats(location: String) {
        val stats = storage.getLocationStats(location)
        if (stats != null) {
            this.stats = stats
        }
        else {
            val statsResp =
                if (location == "world") getWorldStats()
                else getCountryStats(location)
            val locStat = location to statsResp
            saveLocationStat(locStat)
            this.stats = locStat
        }
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

