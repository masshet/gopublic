package com.mrstark.gopublic

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.digits.sdk.android.AuthCallback
import com.digits.sdk.android.Digits
import com.digits.sdk.android.DigitsException
import com.digits.sdk.android.DigitsSession
import com.mrstark.gopublic.fragment.StartFragment
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.core.TwitterCore
import io.fabric.sdk.android.Fabric

class MainActivity : AppCompatActivity() {

    private val TWITTER_KEY = "FmoMoerEnmzdmEchbhVuxGa4L";
    private val TWITTER_SECRET = "qXhrLyreLEcAE0bErfBKjOiHyUuKk1u1Oip39pW1ba6zgnxh0P";

    var callback: AuthCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val authConfig = TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, TwitterCore(authConfig), Digits());
        setContentView(R.layout.activity_main)
        val transaction = fragmentManager.beginTransaction()
        transaction.add(R.id.container, StartFragment())
        transaction.commit()
        callback = object: AuthCallback {
            override fun success(session: DigitsSession?, phoneNumber: String?) {
                Log.d("MyLOG", "Good")
            }

            override fun failure(error: DigitsException?) {
                Log.d("MyLOG", "Bad")
            }

        }
    }
}
