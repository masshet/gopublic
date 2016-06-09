package com.mrstark.gopublic

import android.app.FragmentTransaction
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.digits.sdk.android.AuthCallback
import com.digits.sdk.android.DigitsException
import com.digits.sdk.android.DigitsOAuthSigning
import com.digits.sdk.android.DigitsSession
import com.mrstark.gopublic.activity.GalleryActivity
import com.mrstark.gopublic.api.Api
import com.mrstark.gopublic.entity.Screen
import com.mrstark.gopublic.entity.User
import com.mrstark.gopublic.fragment.CabinetFragment
import com.mrstark.gopublic.fragment.CityScreensFragment
import com.mrstark.gopublic.fragment.ScreenFragment
import com.mrstark.gopublic.fragment.StartFragment
import com.mrstark.gopublic.util.CameraIntentHelper
import com.squareup.okhttp.OkHttpClient
import com.twitter.sdk.android.core.TwitterAuthToken
import com.twitter.sdk.android.core.TwitterCore
import ly.img.android.ui.activities.CameraPreviewActivity
import ly.img.android.ui.utilities.PermissionRequest
import retrofit.RetrofitError

class MainActivity : AppCompatActivity(), PermissionRequest.Response {
    private val BASE_URL = "http://gopublic.by/api"
    private val RESULT = 1

    private var credentials: String? = null
    val KEY_SCREEN = "screen"
    val KEY_CREDENTIAL = "X-Verify-Credentials-Authorization"
    var callback: AuthCallback? = null


    var transaction: FragmentTransaction? = null
    var detailsFragment: ScreenFragment? = null
    var screensFragment: CityScreensFragment? = null

    private val builder = retrofit.RestAdapter.Builder()
            .setEndpoint(BASE_URL)
            .setClient(retrofit.client.OkClient(OkHttpClient()))

    val client = builder.build().create(Api::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppDefault)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
 //           loadCredentials()
            loadFragment()
        }

        callback = object: AuthCallback {
            override fun success(session: DigitsSession?, phoneNumber: String?) {
                val authConfig = TwitterCore.getInstance().authConfig
                val authToken = session?.authToken as TwitterAuthToken
                val digitsOAuthSigning = DigitsOAuthSigning(authConfig, authToken)
                val authHeaders = digitsOAuthSigning.oAuthEchoHeadersForVerifyCredentials
                client.login(authHeaders[KEY_CREDENTIAL]!!,
                        object : retrofit.Callback<User> {
                            override fun failure(error: RetrofitError?) {

                            }

                            override fun success(t: User?, response: retrofit.client.Response?) {
                                credentials = authHeaders[KEY_CREDENTIAL]
                                Log.d("MyTag", authHeaders[KEY_CREDENTIAL])
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
                RESULT -> {
                    val path = data.getStringExtra(CameraPreviewActivity.RESULT_IMAGE_PATH)
                    detailsFragment?.loadPhoto(path)
                }
                2 -> {
                    loadEditor(data.data.path)
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
        val bundle = Bundle()
        bundle.putString(KEY_CREDENTIAL, credentials)
        val fragment = CityScreensFragment()
        fragment.arguments = bundle
        transaction?.replace(R.id.container, fragment)
        transaction?.commitAllowingStateLoss()
    }

    private fun loadFragment() {
        transaction = fragmentManager.beginTransaction()
        if (credentials != null) {
            screensFragment = CityScreensFragment()
            transaction?.add(R.id.container, screensFragment)
        } else {
            transaction?.add(R.id.container, StartFragment())
        }
        transaction?.commit()
    }

    override fun onBackPressed() {
        val count = fragmentManager.backStackEntryCount
        if (count == 0) {
            super.onBackPressed()
        } else {
            fragmentManager.popBackStack()
        }
    }

    fun makeAnOrder(screen: Screen) {
        val bundle = Bundle()
        bundle.putParcelable(KEY_SCREEN, screen)
        transaction = fragmentManager.beginTransaction()
        detailsFragment = ScreenFragment()
        detailsFragment?.arguments = bundle
        transaction?.replace(R.id.container, detailsFragment)
        transaction?.addToBackStack("a")
        transaction?.commit()
    }

    fun takeAPhoto() {
        CameraIntentHelper().getCameraIntent(this)
                .startActivityForResult(RESULT)
    }

    fun loadImages() {
        val intent = Intent(this, GalleryActivity::class.java)
        startActivityForResult(intent, RESULT)
    }

    fun loadEditor(path: String) {
        CameraIntentHelper().getPhotoEditorIntent(this, path)
                .startActivityForResult(RESULT)
    }

    fun loadCabinet() {
        transaction = fragmentManager.beginTransaction()
        val fragment = CabinetFragment()
        transaction?.replace(R.id.container, fragment)
        transaction?.addToBackStack("a")
        transaction?.commit()
    }

    private fun saveCredentials() {
        val pref = getPreferences(MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString(KEY_CREDENTIAL, credentials)
        editor.apply()
    }
}
