package app.covidstats.model

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import app.covidstats.db.*
import app.covidstats.model.data.Continent
import app.covidstats.model.data.covid_stats.CovidStats
import app.covidstats.model.data.news.Item
import kotlinx.coroutines.CoroutineScope

class Model(context: Context, scope: CoroutineScope) {
    companion object {
        /** List of all continents */
        val continents: List<String> = listOf("Africa", "Asia", "Europe", "North America", "Australia-Oceania", "South America")
    }

    var stats by mutableStateOf<Pair<String, CovidStats>?>(null)

    private val storage: Storage = Storage(context)

    var news by mutableStateOf<List<Item>?>(null)

    /** List of all countries for a given continent */
    var countries by mutableStateOf<Pair<String, List<String>>?>(null)

    val moreCovidInfo = "https://www.who.int/emergencies/diseases/novel-coronavirus-2019"

    var favoriteLocations by mutableStateOf<List<String>>(emptyList())

    init {
        storage.init()
        favoriteLocations = storage.getFavoriteCountries()
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
        stats = "World" to getWorldStats()
    }

    private fun saveLocationStat(locationStats: Pair<String, CovidStats>) {
        storage.saveLocationStats(locationStats)
    }

    /**
     * Fetches worldwide COVID-19 stats.
     */
    fun loadContinentCountries(continent: Continent) {
        val locations = storage.getContinentLocations(continent)?.also {
            Log.i("Model", "Restoring locations, for $continent, from storage")
        } ?: // if no locations are stored, fetch them
        run {
            Log.i("Model", "Fetching locations, for $continent, from API")
            // if no locations are stored, fetch them
            val locations1 = fetchContinentCountries(continent)
            storage.saveContinentLocations(continent, locations1)
            // if no locations are stored, fetch them
            locations1
        }
        countries = continent.name.toLowerCase() to locations
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
        } else {
            val statsResp =
                if (location == "World") getWorldStats()
                else if (continents.any { it == location }) getContinentStats(location)
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

    fun dumpStats() {
        stats = null
    }

    fun dumpCountries() {
        countries = null
    }
}

