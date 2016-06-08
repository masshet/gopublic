package com.mrstark.gopublic.fragment

import android.app.Fragment
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.digits.sdk.android.DigitsAuthButton
import com.google.gson.JsonObject
import com.mrstark.gopublic.MainActivity
import com.mrstark.gopublic.R
import com.mrstark.gopublic.adapter.ListScreensAdapter
import com.mrstark.gopublic.api.Api
import com.mrstark.gopublic.entity.Screen
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CityScreensFragment: Fragment(), Toolbar.OnMenuItemClickListener {
    private var BASE_URL = "http://gopublic.by/api/"

    private var toolbar: Toolbar? = null

    private var layout: DrawerLayout? = null
    private var recyclerView: RecyclerView? = null
    private var navigationView: NavigationView? = null
    public var screensList: List<Screen>? = null
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater?.inflate(R.layout.fragment_city_screens, container, false)
        setupToolbar(root)
        setupNavigationView(root)
        setupRecycleView(root)
        setHasOptionsMenu(true)
        return root
    }

    private fun setupNavigationView(root: View?) {
        layout = root?.findViewById(R.id.drawer_layout) as DrawerLayout

        val toggle = ActionBarDrawerToggle(activity, layout, toolbar, R.string.view_navigation_open, R.string.view_navigation_close)
        layout?.addDrawerListener(toggle)
        toggle.syncState()

        navigationView = root?.findViewById(R.id.navigation_view) as NavigationView
        setupHeader(navigationView?.getHeaderView(0))
    }

    private fun setupHeader(header: View?) {
        val credentials = activity.getPreferences(AppCompatActivity.MODE_PRIVATE).getString((activity as MainActivity).KEY_CREDENTIAL, "")
        var info = header?.findViewById(R.id.header_info) as LinearLayout
        var auth = header?.findViewById(R.id.header_auth) as DigitsAuthButton
        var ava = header?.findViewById(R.id.avatar) as ImageView
        var balanceLayout = header?.findViewById(R.id.balance_layout) as RelativeLayout
        var balance = header?.findViewById(R.id.balance) as TextView
        var refill = header?.findViewById(R.id.refill) as Button
        refill.setOnClickListener { refill() }
        if (credentials.length != 0) {
            auth.visibility = View.GONE
            ava.setImageResource(R.drawable.oi8z2kc0)
            var call = (activity as MainActivity).api.balance(credentials)
            call.enqueue(object : Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
                    balance.text = response?.body()?.get("balance").toString().replace("\"", "")
                }

                override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {
                    Log.d("MyLOG", "Bad")
                }
            })
        } else {
            info.visibility = View.GONE
            balanceLayout.visibility = View.GONE
            auth.setAuthTheme(android.R.style.ThemeOverlay_Material)
            auth.setCallback((activity as MainActivity).callback)
            auth.setBackgroundColor(resources.getColor(R.color.colorAccent, null))
            auth.setText(R.string.authorization)
            ava.setImageResource(R.drawable.account_circle)
        }
    }

    private fun refill() {
        var fragment = PayFragment()
        fragment.show(childFragmentManager, "Payment")
    }

    private fun setupRecycleView(root: View?) {
        recyclerView = root?.findViewById(R.id.list_screens) as RecyclerView?
        recyclerView?.layoutManager = LinearLayoutManager(activity)

        var call = (activity as MainActivity).api.getAllScreens()
        call.enqueue(object : Callback<List<Screen>> {
            override fun onResponse(call: Call<List<Screen>>?, response: Response<List<Screen>>) {
                screensList = response.body()
                recyclerView?.adapter = ListScreensAdapter(response.body())
            }

            override fun onFailure(call: Call<List<Screen>>?, t: Throwable?) {
                Log.d("MyLOG", "Bad")
            }

        })
    }

    private fun setupToolbar(root: View?) {
        toolbar = root?.findViewById(R.id.toolbar) as Toolbar
        toolbar?.setTitle(R.string.city_screens_fragment)
        toolbar?.inflateMenu(R.menu.menu_city_screens)
        toolbar?.setOnMenuItemClickListener(this)
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.cabinet -> (activity as MainActivity).loadCabinet()
        }
        return true
    }
}
