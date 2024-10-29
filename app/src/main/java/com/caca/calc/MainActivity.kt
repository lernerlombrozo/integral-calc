package com.caca.calc

import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import androidx.activity.ComponentActivity
import com.applovin.sdk.AppLovinMediationProvider
import com.applovin.sdk.AppLovinSdk
import com.applovin.sdk.AppLovinSdkInitializationConfiguration

class MainActivity : ComponentActivity() {
    private var tag = "dl mainActivity"
    private lateinit var applovinRewardedHandler: ApplovinRewardedHandler
    private lateinit var applovinInterstitialHandler: ApplovinInterstitialHandler
    private lateinit var rewardedAdListener: AdListener
    private lateinit var interstitialAdListener: AdListener
    private lateinit var webView: WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeWebView()
        configureRewardedAd()
        configureInterstitialAd()
        initializeApplovin()
    }

    private fun initializeWebView(){
        webView = findViewById(R.id.webview)
        webView.settings.javaScriptEnabled = true
        webView.settings.loadWithOverviewMode = true
        webView.settings.useWideViewPort = true
        webView.settings.builtInZoomControls = true
        webView.settings.setSupportZoom(false)
        webView.loadUrl("file:///android_asset/index.html")
    }

    private fun initializeApplovin(){
        val initConfig = AppLovinSdkInitializationConfiguration.builder("f4ax0cou61w1Wt8fzyB_quC7usFg2GdQNL46Op0Rwt_afFM0fbf8e2_FtJycdmqRRfTIReGzKMwM0Lvg92FRMm", this)
            .setMediationProvider(AppLovinMediationProvider.MAX)
            .build()
        AppLovinSdk.getInstance(this).initialize(initConfig) { sdkConfig ->
            Log.d(tag, "sdk initialized")
        }
    }

    private fun configureRewardedAd(){
        applovinRewardedHandler = ApplovinRewardedHandler()
        rewardedAdListener = AdListener()
        rewardedAdListener.setLoadAdListener {
            applovinRewardedHandler.loadRewardedAd(this)
        }
        rewardedAdListener.setShowAdListener {
            applovinRewardedHandler.showRewardedAd(this)
        }
        webView.addJavascriptInterface(rewardedAdListener, "ApplovinRewarded")
        Log.d(tag, "added rewarded interface")
    }

    private fun configureInterstitialAd(){
        applovinInterstitialHandler = ApplovinInterstitialHandler()
        interstitialAdListener = AdListener()
        interstitialAdListener.setLoadAdListener {
            applovinInterstitialHandler.loadInterstitialAd(this)
        }
        interstitialAdListener.setShowAdListener {
            applovinInterstitialHandler.showInterstitialAd(this)
        }
        webView.addJavascriptInterface(interstitialAdListener, "ApplovinInterstitial")
        Log.d(tag, "added interstitial interface")
    }
}