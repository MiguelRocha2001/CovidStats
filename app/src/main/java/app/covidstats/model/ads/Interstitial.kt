package app.covidstats.model.ads

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

private const val TAG = "Ads_Handler"

private const val TEST_ADD_ID = "ca-app-pub-3940256099942544/1033173712"
private const val PRODUCTION_ADD_ID = "ca-app-pub-9782463980121956/7649483941"

fun loadAd(context: Context, onLoad: (InterstitialAd) -> Unit, onFailed: (LoadAdError) -> Unit) {
    InterstitialAd.load(
        context,
        PRODUCTION_ADD_ID,
        AdRequest.Builder().build(),
        object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d(TAG, adError.toString())
                onFailed(adError)
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d(TAG, "Ad was loaded.")
                onLoad(interstitialAd)
            }
        }
    )
}

fun loadCallbacks(mInterstitialAd: InterstitialAd?, onDismissed: () -> Unit, onFailed: () -> Unit) {
    mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
        override fun onAdClicked() {
            // Called when a click is recorded for an ad.
            Log.d(TAG, "Ad was clicked.")
        }

        override fun onAdDismissedFullScreenContent() {
            // Called when ad is dismissed.
            Log.d(TAG, "Ad dismissed fullscreen content.")
            onDismissed()
        }

        override fun onAdFailedToShowFullScreenContent(p0: AdError) {
            // Called when ad fails to show.
            Log.e(TAG, "Ad failed to show fullscreen content.")
            onFailed()
        }

        override fun onAdImpression() {
            // Called when an impression is recorded for an ad.
            Log.d(TAG, "Ad recorded an impression.")
        }

        override fun onAdShowedFullScreenContent() {
            // Called when ad is shown.
            Log.d(TAG, "Ad showed fullscreen content.")
        }
    }
}

fun showAd(activity: Activity, mInterstitialAd: InterstitialAd?) {
    if (mInterstitialAd != null) {
        mInterstitialAd.show(activity)
    } else {
        Log.d("TAG", "The interstitial ad wasn't ready yet.")
    }
}