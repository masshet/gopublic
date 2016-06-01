package com.mrstark.gopublic

import android.app.FragmentTransaction
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.digits.sdk.android.AuthCallback
import com.digits.sdk.android.DigitsException
import com.digits.sdk.android.DigitsOAuthSigning
import com.digits.sdk.android.DigitsSession
import com.mrstark.gopublic.api.Api
import com.mrstark.gopublic.entity.Screen
import com.mrstark.gopublic.entity.User
import com.mrstark.gopublic.fragment.CityScreensFragment
import com.mrstark.gopublic.fragment.ScreenFragment
import com.mrstark.gopublic.fragment.StartFragment
import com.mrstark.gopublic.util.CameraIntentHelper
import com.twitter.sdk.android.core.TwitterAuthToken
import com.twitter.sdk.android.core.TwitterCore
import ly.img.android.ui.utilities.PermissionRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), PermissionRequest.Response {
    private var BASE_URL = "http://gopublic.by/api/"
    private var CAMERA_PREVIEW_RESULT = 2

    private var credentials: String? = null
    val KEY_SCREEN = "screen"
    val KEY_REGISTER = "register"
    val KEY_CREDENTIAL = "X-Verify-Credentials-Authorization"
    var callback: AuthCallback? = null


    var transaction: FragmentTransaction? = null
    var detailsFragment: ScreenFragment? = null
    private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val api = retrofit.create(Api::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppDefault)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            loadCredentials()
            loadFragment()
        }

        callback = object: AuthCallback {
            override fun success(session: DigitsSession?, phoneNumber: String?) {
                val authConfig = TwitterCore.getInstance().authConfig
                val authToken = session?.authToken as TwitterAuthToken
                val digitsOAuthSigning = DigitsOAuthSigning(authConfig, authToken)
                val authHeaders = digitsOAuthSigning.oAuthEchoHeadersForVerifyCredentials
                var login = api.login(authHeaders[KEY_CREDENTIAL]!!)
                login.enqueue(object : Callback<User> {
                    override fun onFailure(call: Call<User>?, t: Throwable?) {

                    }

                    override fun onResponse(call: Call<User>?, response: Response<User>?) {
                        credentials = authHeaders[KEY_CREDENTIAL]
                        saveCredentials()
                        loadCityScreens()
                    }

                })
            }

            override fun failure(error: DigitsException?) {
                Log.d("MyLOG", "Bad")
            }

        }
    }

    private fun loadCredentials() {
        val pref = getPreferences(MODE_PRIVATE)
        credentials = pref.getString(KEY_CREDENTIAL, "")
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
                CAMERA_PREVIEW_RESULT -> {
                    var extras = data.extras;
                    var imageBitmap = extras.get("data") as Bitmap;
                   // detailsFragment?.loadPhoto(imageBitmap)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        PermissionRequest.onRequestPermissionsResult(requestCode, permissions, grantResults)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun permissionDenied() {
    }

    override fun permissionGranted() {

    }

    fun loadCityScreens() {
        transaction = fragmentManager.beginTransaction()
        var bundle = Bundle()
        bundle.putString(KEY_CREDENTIAL, credentials)
        var fragment = CityScreensFragment()
        fragment.arguments = bundle
        transaction?.replace(R.id.container, fragment)
        transaction?.commitAllowingStateLoss()
    }

    private fun loadFragment() {
        transaction = fragmentManager.beginTransaction()
        if (credentials?.length != 0) {
            var fragment = CityScreensFragment()
            transaction?.add(R.id.container, fragment)
        } else {
            transaction?.add(R.id.container, StartFragment())
        }
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
        CameraIntentHelper().getCameraIntent(this)
                .startActivityForResult(CAMERA_PREVIEW_RESULT)
    }

    fun loadImages() {
        var intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, 1)
    }

    private fun saveCredentials() {
        val pref = getPreferences(MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString(KEY_CREDENTIAL, credentials)
        editor.commit()
    }
}
