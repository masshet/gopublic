package com.mrstark.gopublic

import android.app.FragmentTransaction
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
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

    private val TWITTER_KEY = "FmoMoerEnmzdmEchbhVuxGa4L"
    private val TWITTER_SECRET = "qXhrLyreLEcAE0bErfBKjOiHyUuKk1u1Oip39pW1ba6zgnxh0P"
    val KEY_SCREEN = "screen"

    var callback: AuthCallback? = null
    var transaction: FragmentTransaction? = null
    var detailsFragment: ScreenFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val authConfig = TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET)
        Fabric.with(this, TwitterCore(authConfig), Digits())
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            loadStartFragment()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            when(requestCode) {
                1 -> {
                    var selectedImage = data.data
                    var bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImage)
                    detailsFragment?.loadPhoto(bitmap)
                }
                2 -> {
                    var extras = data.extras;
                    var imageBitmap = extras.get("data") as Bitmap;
                    detailsFragment?.loadPhoto(imageBitmap)
                }
            }
        }
    }

    fun loadCityScreens() {
        transaction = fragmentManager.beginTransaction()
        transaction?.replace(R.id.container, CityScreensFragment())
        transaction?.commit()
    }

    private fun loadStartFragment() {
        transaction = fragmentManager.beginTransaction()
        transaction?.add(R.id.container, StartFragment())
        transaction?.commit()
    }

    fun makeAnOrder(screen: Screen) {
        var bundle = Bundle()
        bundle.putParcelable(KEY_SCREEN, screen)
        transaction = fragmentManager.beginTransaction()
        detailsFragment = ScreenFragment()
        detailsFragment?.arguments = bundle
        transaction?.add(R.id.container, detailsFragment)
        transaction?.commit()
    }

    fun takeAPhoto() {
        var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, 2)
    }

    fun loadImages() {
        var intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, 1)
    }
}
