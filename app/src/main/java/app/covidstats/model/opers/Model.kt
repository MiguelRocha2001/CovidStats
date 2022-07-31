package app.covidstats.model.opers

import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import app.covidstats.model.Storage
import app.covidstats.model.ads.loadAd
import app.covidstats.model.ads.loadCallbacks
import app.covidstats.model.ads.showAd
import app.covidstats.model.data.app.*
import app.covidstats.model.data.news.Item
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.nativead.NativeAd
import java.time.Instant

@RequiresApi(Build.VERSION_CODES.O)
class Model(private val context: Context, private val activity: Activity) {
    private var mInterstitialAd: InterstitialAd? = null
    var nativeAd by mutableStateOf<NativeAd?>(null)
        private set

    private var instant = Instant.now()

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
        showInterstitial()
        loadLocationCovidStats(location, storage) {
            stats = it
            Log.i("Model", stats.toString())
            Log.i("Model", "Stats for $location set")
        }
    }

    /**
     * It will display an ad if it is time to do so (60s).
     */
    private fun showInterstitial() {
        activity.runOnUiThread {
            if (elapsedTime()) {
                loadAd(
                    context,
                    onLoad = {
                        mInterstitialAd = it
                        loadCallbacks(
                            mInterstitialAd,
                            onDismissed = { mInterstitialAd = null },
                            onFailed = { mInterstitialAd = null }
                        )
                        showAd(activity, mInterstitialAd)
                    },
                    onFailed = { mInterstitialAd = null }
                )
            }
        }
    }

    /**
     * Checks if passed at least 60 seconds since last time this function was called.
     * If so, resets the timer and returns true.
     * If not, returns false.
     */
    private fun elapsedTime(): Boolean {
        val currentInstant = Instant.now()
        if (!currentInstant.isBefore(instant.plusSeconds(60))) {
            instant = currentInstant
            return true
        }
        return false
    }

    /**
     * Clears the global variable stats.
     */
    fun dumpStats() {
        Log.i("Model", "Stats cleared")
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
                " you find any bugs, please report them to me at miguelasrocha1work@gmail.com\n"
}

