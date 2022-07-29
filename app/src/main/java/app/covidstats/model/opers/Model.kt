package app.covidstats.model.opers

import android.content.Context
import android.os.Parcelable
import android.provider.ContactsContract.DisplayNameSources.EMAIL
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.setValue
import app.covidstats.model.Storage
import app.covidstats.model.data.app.*
import app.covidstats.model.data.covid_stats.CovidStats
import app.covidstats.model.data.news.Item
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

class Model(context: Context) {
    val appInfo: String = getAppTextInfo()

    private val storage: Storage = Storage(context)

    var stats by mutableStateOf<Stats?>(null)

    var news by mutableStateOf<List<Item>?>(null)

    /** List of all countries for a given continent */
    var countries by mutableStateOf<Pair<Continent, Locations>?>(null)

    val moreCovidInfo = "https://www.who.int/emergencies/diseases/novel-coronavirus-2019"

    var favoriteLocations by mutableStateOf(LocationsSuccess(emptyList()))

    init {
        storage.init()
        favoriteLocations = LocationsSuccess(storage.getFavoriteCountries())
    }

    fun isCountryOnFavorites(country: String) = favoriteLocations.locations.contains(country)

    /**
     * Adds [location] to the list of favorite locations.
     */
    fun addFavoriteLocation(location: String) {
        addFavoriteLocation(location, favoriteLocations.locations, storage) {
            favoriteLocations = LocationsSuccess(it)
        }
    }

    /**
     * Removes [location] (if exists) to the list of favorite locations.
     */
    fun removeFavoriteLocation(location: String) {
        removeFavoriteLocation(location, favoriteLocations.locations, storage) {
            favoriteLocations = LocationsSuccess(it)
        }
    }

    /**
     * Filters current list of countries by the given [filter].
     */
    fun filterFavorites(name: String) {
        filterFavorites(name, storage) {
            favoriteLocations = LocationsSuccess(it)
        }
    }

    /**
     * Sets global variable countries with the obtained list of countries, given a [continent].
     * If the data is already in cache, obtains it from there. Otherwise, requests the API, and stores the data in cache to further use.
     */
    fun loadContinentCountries(continent: Continent) {
        countries = continent to LocationsLoading
        loadContinentCountries(continent, storage) {
            if (it is LocationsSuccess)
                Log.i("Model", "${continent.formattedName()} countries set")
            countries = continent to it
        }
    }

    /**
     * Fetches COVID-19 stats for a specific continent.
     */
    fun loadContinentCovidStats(continent: Continent) {
        loadContinentCovidStats(continent) {
            stats = it
        }
    }

    /**
     * Sets global variable stats with the obtained stats, given a [location].
     * If the data is already in cache, obtains it from there. Otherwise, requests the API, and stores the data in cache to further use.
     */
    fun loadLocationCovidStats(location: String) {
        loadLocationCovidStats(location, storage) {
            stats = it
            Log.i("Model", "Stats for $location set")
        }
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
        if (observedCountries.second is LocationsSuccess) {
            filterLocations(name, storage, observedCountries.first) {
                countries = observedCountries.first to it
            }
        }
    }

    private fun getAppTextInfo() =
        "This app was designed so the user can consult the current covid 19 situation, around the globe.\n" +
                "It is a work in progress, and is not meant to be used as a replacement for the official website.\n" +
                "The programmer (me) is still in college, and doesn't have much experience in Android. So, if" +
                " you find any bugs, please report them to me at <a href=\"mailto: ${EMAIL}\">${EMAIL}</a>\n"
}
