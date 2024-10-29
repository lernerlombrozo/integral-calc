package com.caca.calc;

import android.util.Log;
import android.webkit.JavascriptInterface;

class AdListener() {
    private var tag = "dl interface"
    private var loadAdListener: (()->Unit)? = null
    private var showAdListener: (()->Unit)? = null

    fun setLoadAdListener(listener: ()->Unit){
        this.loadAdListener = listener;
    }

    fun setShowAdListener(listener: ()->Unit){
        this.showAdListener = listener;
    }

    @JavascriptInterface
    fun loadAd()
    {
        Log.d(tag ,"should load ad")
        loadAdListener?.invoke()
    }

    @JavascriptInterface
    fun showAd()
    {
        Log.d(tag ,"should show ad")
        showAdListener?.invoke()
    }
}
