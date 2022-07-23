package app.covidstats.model

import android.content.Context
import android.provider.ContactsContract.DisplayNameSources.EMAIL
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import app.covidstats.db.*
import app.covidstats.model.data.other.Continent
import app.covidstats.model.data.covid_stats.CovidStats
import app.covidstats.model.data.news.Item
import app.covidstats.model.data.other.formattedName
import app.covidstats.model.data.other.toContinent
import app.covidstats.model.data.time.TimeZone

class Model(context: Context) {
    companion object {
        /** List of all continents */
        val continents: List<String> = listOf("Africa", "Asia", "Europe", "North America", "Australia Oceania", "South America")
    }

    val appInfo: String = getAppTextInfo()

    private val storage: Storage = Storage(context)

    var stats by mutableStateOf<Pair<String, CovidStats>?>(null)

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
     * Stores given [locationStats] in cache.
     */
    private fun saveLocationStat(locationStats: Pair<String, CovidStats>) {
        Log.i("Model", "Saving stats for ${locationStats.first}")
        storage.saveLocationStats(locationStats, TimeZone())
        Log.i("Model", "Saved location stats for ${locationStats.first}")
    }

    /**
     * Sets global variable countries with the obtained list of countries, given a [continent].
     * If the data is already in cache, obtains it from there. Otherwise, requests the API, and stores the data in cache to further use.
     */
    fun loadContinentCountries(continent: Continent) {
        val locations = storage.getContinentLocations(continent)
        if (locations != null) {
            val expired = locations.first.timeExpired()
            if (expired) {
                Log.i("Model", "Continent ${continent.formattedName()} expired")
            } else {
                countries = continent.formattedName() to locations.second
                Log.i("Model", "${continent.formattedName()} countries loaded from cache")
                return
            }
        }
        fetchSaveAndSetCountries(continent)
    }

    private fun fetchSaveAndSetCountries(continent: Continent) {
        Log.i("Model", "Fetching locations, for $continent, from API")
        val locations = fetchContinentCountries(continent)
        Log.i("Model", "Locations fetched successfully from API")
        Log.i("Model", "Saving locations, for $continent, to storage")
        storage.saveContinentLocations(continent, locations, TimeZone())
        Log.i("Model", "Saved locations, for $continent, to storage")
        countries = continent.formattedName() to locations
        Log.i("Model", "${continent.formattedName()} countries set")
    }

    /**
     * Fetches COVID-19 stats for a specific continent.
     */
    fun loadContinentCovidStats(continent: Continent) {
        val statsResp = getContinentStats(continent)
        stats = continent.formattedName() to statsResp
    }

    /**
     * Sets global variable stats with the obtained stats, given a [location].
     * If the data is already in cache, obtains it from there. Otherwise, requests the API, and stores the data in cache to further use.
     */
    fun loadLocationCovidStats(location: String) {
        val localStats = fetchLocationStatsFromStorage(location)
        if (localStats != null) {
            stats = location to localStats
            Log.i("Model", "Restoring stats, for $location, from storage")
        } else
            fetchSaveAndSetLocationStats(location)
    }

    private fun fetchLocationStatsFromStorage(location: String): CovidStats? {
        val stats = storage.getLocationStats(location)
        if (stats != null) {
            val timeExpired = stats.first.timeExpired()
            return if (!timeExpired) {
                stats.second
            } else {
                Log.i("Model", "Stats for $location expired")
                null
            }
        }
        return null
    }

    private fun fetchSaveAndSetLocationStats(location: String) {
        Log.i("Model", "Fetching stats, for $location, from API")

        val statsResp = if (location == "World") {
            getWorldStats()
        } else fetchContinentStats(location) ?: getCountryStats(location)

        Log.i("Model", "Stats for $location fetched successfully from API")
        val locStat = location to statsResp
        Log.i("Model", "Saving stats, for $location, to storage")
        saveLocationStat(locStat)
        Log.i("Model", "Saved stats, for $location, to storage")
        this.stats = locStat
        Log.i("Model", "Stats for $location set")
    }

    /**
     * Fetches COVID-19 stats for continent [name] or null if name is invalid.
     * @param name continent name
     */
    private fun fetchContinentStats(name: String): CovidStats? {
        return if (continents.any { it == name }) {
            val continent = name.toContinent()
                ?: throw IllegalStateException("Invalid continent name: $name")
            getContinentStats(continent)
        } else null
    }

    /**
     * Clears the global variable stats.
     */
    fun dumpStats() {
        stats = null
    }

    /**
     * Clears the global variable countries.
     */
    fun dumpCountries() {
        countries = null
    }

    /**
     * Filters current list of countries by the given [filter].
     */
    fun filterLocations(name: String) {
        val observedCountries = countries ?: return
        val continent = observedCountries.first.toContinent() ?: return
        loadContinentCountries(continent)
        if (name.length < 3) return
        val observedCountriesAfterRefresh = countries ?: return
        countries = observedCountriesAfterRefresh.first to
                observedCountriesAfterRefresh.second.filter { it.contains(name, true) }
    }

    /**
     * Filters current list of countries by the given [filter].
     */
    fun filterFavorites(name: String) {
        favoriteLocations = storage.getFavoriteCountries()
        val observedFavorites = favoriteLocations
        if (name.length < 3) return
        favoriteLocations = observedFavorites.filter { it.contains(name, true) }
    }

    private fun getAppTextInfo() =
        "This app was designed so the user can consult the current covid 19 situation, around the globe.\n" +
                "It is a work in progress, and is not meant to be used as a replacement for the official website.\n" +
                "The programmer (me) is still in college, and doesn't have much experience in Android. So, if" +
                " you find any bugs, please report them to me at <a href=\"mailto: ${EMAIL}\">${EMAIL}</a>\n"
}

