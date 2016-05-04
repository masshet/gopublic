package com.mrstark.gopublic

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.digits.sdk.android.Digits
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.core.TwitterCore
import io.fabric.sdk.android.Fabric

class MainActivity : AppCompatActivity() {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private val TWITTER_KEY = "FmoMoerEnmzdmEchbhVuxGa4L";
    private val TWITTER_SECRET = "qXhrLyreLEcAE0bErfBKjOiHyUuKk1u1Oip39pW1ba6zgnxh0P";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val authConfig = TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, TwitterCore(authConfig), Digits());
        setContentView(R.layout.activity_main)
    }
}
