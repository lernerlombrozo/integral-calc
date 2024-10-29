package com.caca.calc

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxInterstitialAd
import java.util.concurrent.TimeUnit

class ApplovinInterstitialHandler : MaxAdListener {
    private var applovinTag = "dl al i"

    //  applovin rewarded
    private lateinit var interstitialAd: MaxInterstitialAd
    private var retryAttempt = 0.0

    fun loadInterstitialAd(context:Context)
    {
        interstitialAd = MaxInterstitialAd("34adb7240802bc22", context )
        interstitialAd.setListener( this )
        interstitialAd.loadAd()
    }

    fun showInterstitialAd(activity: Activity)
    {
        if ( interstitialAd.isReady )
        {
            // `this` is the activity that will be used to show the ad
            interstitialAd.showAd(activity);
        }
    }


    override fun onAdLoaded(var1: MaxAd){
        Log.d(applovinTag, "ad loaded")
        retryAttempt = 0.0
    }

    override fun onAdDisplayed(var1: MaxAd){
        Log.d(applovinTag, "ad displayed")
    }

    override fun onAdHidden(var1: MaxAd){
        Log.d(applovinTag, "ad hidden")
        // rewarded ad is hidden. Pre-load the next ad
        interstitialAd.loadAd()
    }

    override fun onAdClicked(var1: MaxAd){
        Log.d(applovinTag, "ad clicked")
    }

    override fun onAdLoadFailed(var1: String, var2: MaxError){
        Log.d(applovinTag, "ad load failed")
        // Rewarded ad failed to load
        // AppLovin recommends that you retry with exponentially higher delays up to a maximum delay (in this case 64 seconds)
        retryAttempt++
        val delayMillis = TimeUnit.SECONDS.toMillis( Math.pow( 2.0, Math.min( 6.0, retryAttempt ) ).toLong() )
        Handler(Looper.getMainLooper()).postDelayed( { interstitialAd.loadAd() }, delayMillis )
    }

    override fun onAdDisplayFailed(var1: MaxAd, var2: MaxError){
        Log.d(applovinTag, "ad display failed")
    }
}