package app.covidstats

import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import app.covidstats.model.data.app.Continent
import app.covidstats.model.data.app.Locations
import app.covidstats.model.data.app.Stats
import app.covidstats.model.opers.Model
import app.covidstats.ui.MainWindow
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import java.security.AccessController.getContext

@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : ComponentActivity() {
    lateinit var mAdView : AdView

    private lateinit var model: Model

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        MobileAds.initialize(this) {}

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        model = Model(this, this)
        setContent {
            MainWindow(this, model)
        }
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