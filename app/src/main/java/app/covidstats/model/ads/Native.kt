package app.covidstats.model.ads

import android.content.Context
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.nativead.NativeAd

private const val TEST_ADD_ID = "ca-app-pub-3940256099942544/2247696110"
private const val PRODUCTION_ADD_ID = "ca-app-pub-9782463980121956/7649483941"

fun loadNativeAd(context: Context, onSuccess: (nativeAd: NativeAd) -> Unit) {
    lateinit var adLoader: AdLoader
    adLoader = AdLoader.Builder(context, TEST_ADD_ID)
        .forNativeAd {
            if (adLoader.isLoading) {
                // The AdLoader is still loading ads.
                // Expect more adLoaded or onAdFailedToLoad callbacks.
            } else {
                // The AdLoader has finished loading ads.
                onSuccess(it)
            }
        }.build()
    adLoader.loadAd(AdRequest.Builder().build())
}