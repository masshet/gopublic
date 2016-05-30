package com.mrstark.gopublic

import android.app.Application
import android.graphics.Typeface
import com.digits.sdk.android.Digits
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.core.TwitterCore
import io.fabric.sdk.android.Fabric

/**
 * Created by mrstark on 5/25/16.
 */
class App : Application(){

    private val TWITTER_KEY = "FmoMoerEnmzdmEchbhVuxGa4L"
    private val TWITTER_SECRET = "qXhrLyreLEcAE0bErfBKjOiHyUuKk1u1Oip39pW1ba6zgnxh0P"
    private var siglton: App? = null
    var authConfig: TwitterAuthConfig? = null
    private var avenirFont: Typeface? = null

    fun getInstance(): App = siglton!!

    override fun onCreate() {
        super.onCreate()
        siglton = this
        authConfig = TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET)
        Fabric.with(this, TwitterCore(authConfig), Digits())
    }
}