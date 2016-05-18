package com.mrstark.gopublic

import android.app.FragmentTransaction
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.digits.sdk.android.AuthCallback
import com.digits.sdk.android.Digits
import com.digits.sdk.android.DigitsException
import com.digits.sdk.android.DigitsSession
import com.mrstark.gopublic.entity.Screen
import com.mrstark.gopublic.fragment.CityScreensFragment
import com.mrstark.gopublic.fragment.ScreenFragment
import com.mrstark.gopublic.fragment.StartFragment
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.core.TwitterCore
import io.fabric.sdk.android.Fabric

class MainActivity : AppCompatActivity() {

    private val TWITTER_KEY = "FmoMoerEnmzdmEchbhVuxGa4L";
    private val TWITTER_SECRET = "qXhrLyreLEcAE0bErfBKjOiHyUuKk1u1Oip39pW1ba6zgnxh0P";

    var callback: AuthCallback? = null
    var transaction: FragmentTransaction? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val authConfig = TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET)
        Fabric.with(this, TwitterCore(authConfig), Digits())
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            loadFragment()
        }

        callback = object: AuthCallback {
            override fun success(session: DigitsSession?, phoneNumber: String?) {
                Log.d("MyLOG", "Good")
            }

            override fun failure(error: DigitsException?) {
                Log.d("MyLOG", "Bad")
            }

        }
    }

    fun loadCityScreens() {
        transaction = fragmentManager.beginTransaction()
        transaction?.replace(R.id.container, CityScreensFragment())
        transaction?.commit()
    }

    private fun loadFragment() {
        transaction = fragmentManager.beginTransaction()
        transaction?.add(R.id.container, StartFragment())
        transaction?.commit()
    }

    fun loadDetails(screen: Screen) {
        transaction = fragmentManager.beginTransaction()
        var fragment = ScreenFragment()
        fragment.screen = screen
        transaction?.add(R.id.container, fragment)
        transaction?.commit()
    }
}
