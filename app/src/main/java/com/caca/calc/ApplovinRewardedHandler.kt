package com.caca.calc

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxError
import com.applovin.mediation.MaxReward
import com.applovin.mediation.MaxRewardedAdListener
import com.applovin.mediation.ads.MaxRewardedAd
import java.util.concurrent.TimeUnit

class ApplovinRewardedHandler : MaxRewardedAdListener {
    private var applovinTag = "dl al r"

    //  applovin rewarded
    private lateinit var rewardedAd: MaxRewardedAd
    private var retryAttempt = 0.0

    fun loadRewardedAd(context:Context)
    {
        rewardedAd = MaxRewardedAd.getInstance( "be296788cf9e222c", context )
        rewardedAd.setListener( this )
        rewardedAd.loadAd()
    }

    fun showRewardedAd(activity: Activity)
    {
        if ( rewardedAd.isReady )
        {
            // `this` is the activity that will be used to show the ad
            rewardedAd.showAd(activity);
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
        rewardedAd.loadAd()
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
        Handler(Looper.getMainLooper()).postDelayed( { rewardedAd.loadAd() }, delayMillis )
    }

    override fun onAdDisplayFailed(var1: MaxAd, var2: MaxError){
        Log.d(applovinTag, "ad display failed")
    }

    override fun onUserRewarded(maxAd: MaxAd, maxReward: MaxReward)
    {
        Log.d(applovinTag, "reward granted")
    }
}