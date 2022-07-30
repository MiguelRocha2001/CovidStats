package app.covidstats

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import app.covidstats.model.data.app.Continent
import app.covidstats.model.data.app.Locations
import app.covidstats.model.data.app.Stats
import app.covidstats.model.opers.Model
import app.covidstats.ui.MainWindow
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class MainActivity : ComponentActivity() {
    private var mInterstitialAd: InterstitialAd? = null

    private lateinit var model: Model

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // MobileAds.initialize(this) {}
        model = Model(this)
        setContent {
            MainWindow(this, model)
        }

        loadAd(
            this,
            onLoad = {
                mInterstitialAd = it
                loadCallbacks(
                    mInterstitialAd,
                    onDismissed = { mInterstitialAd = null },
                    onFailed = { mInterstitialAd = null }
                )
                showAd(this, mInterstitialAd)
             },
            onFailed = { mInterstitialAd = null }
        )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.i("MainActivityLogger", "onSaveInstanceState() called")
        super.onSaveInstanceState(outState)
        val countries = model.countries
        val stats = model.stats
        if (countries != null)
            outState.putSerializable("countries", countries)
        if (stats != null)
            outState.putSerializable("stats", stats)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        Log.i("MainActivityLogger", "onRestoreInstanceState() called")
        super.onRestoreInstanceState(savedInstanceState)
        val countries = savedInstanceState.getSerializable("countries") as? Pair<Continent, Locations>
        val stats = savedInstanceState.getSerializable("stats") as? Stats
        if (countries != null) {
            model.countries = countries
        }
        if (stats != null) {
            model.stats = stats
        }
    }
}