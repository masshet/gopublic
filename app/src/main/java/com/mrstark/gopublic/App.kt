package com.mrstark.gopublic

import android.app.Application
import android.graphics.Typeface
import com.digits.sdk.android.Digits
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.core.TwitterCore
import io.fabric.sdk.android.Fabric
import ly.img.android.ImgLySdk
import ly.img.android.sdk.configuration.CropAspectConfig
import ly.img.android.sdk.configuration.PhotoEditorSdkConfig


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
        ImgLySdk.init(this)

        val cropConfig = PhotoEditorSdkConfig.getCropConfig()
        cropConfig.clear()
        cropConfig.add(CropAspectConfig(R.string.imgly_crop_name_custom, R.drawable.imgly_icon_option_crop_custom, 1.4f/1))
    }
}