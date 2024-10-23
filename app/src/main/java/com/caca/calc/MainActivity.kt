package com.caca.calc

import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import androidx.activity.ComponentActivity
import com.applovin.sdk.AppLovinMediationProvider
import com.applovin.sdk.AppLovinSdk
import com.applovin.sdk.AppLovinSdkInitializationConfiguration

class MainActivity : ComponentActivity() {
    private var applovinTag = "dl applovin"
    private lateinit var applovinRewardedHandler: ApplovinRewardedHandler
    private lateinit var webappListener: WebAppListener
    private lateinit var webView: WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeWebView()
        configureRewardedAd()
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
            Log.d(applovinTag, "sdk initialized")
        }
    }

    private fun configureRewardedAd(){
        applovinRewardedHandler = ApplovinRewardedHandler()
        webappListener = WebAppListener()
        webappListener.setLoadAdListener {
            applovinRewardedHandler.loadRewardedAd(this)
        }
        webappListener.setShowAdListener {
            applovinRewardedHandler.showRewardedAd(this)
        }
        webView.addJavascriptInterface(webappListener, "Applovin")
        Log.d("dl", "added applovin interface")
    }
}