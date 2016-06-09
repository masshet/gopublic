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
import com.mrstark.gopublic.MainActivity
import com.mrstark.gopublic.R
import com.mrstark.gopublic.adapter.ListScreensAdapter
import com.mrstark.gopublic.entity.Balance
import com.mrstark.gopublic.entity.Screen
import retrofit.Callback
import retrofit.RetrofitError

class CityScreensFragment: Fragment(), Toolbar.OnMenuItemClickListener {

    private var toolbar: Toolbar? = null

    private var layout: DrawerLayout? = null
    private var recyclerView: RecyclerView? = null
    private var navigationView: NavigationView? = null
//    var screensList: List<Screen>? = null
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
        val info = header?.findViewById(R.id.header_info) as LinearLayout
        val auth = header?.findViewById(R.id.header_auth) as DigitsAuthButton
        val ava = header?.findViewById(R.id.avatar) as ImageView
        val balanceLayout = header?.findViewById(R.id.balance_layout) as RelativeLayout
        val balance = header?.findViewById(R.id.balance) as TextView
        val refill = header?.findViewById(R.id.refill) as Button
        refill.setOnClickListener { refill() }
        if (credentials.length != 0) {
            auth.visibility = View.GONE
            ava.setImageResource(R.drawable.oi8z2kc0)
            (activity as MainActivity).client.balance(credentials,
                    object : Callback<Balance> {
                        override fun failure(error: RetrofitError?) {
                            Log.d("MyLOG", "Bad")
                        }

                        override fun success(t: Balance?, response: retrofit.client.Response?) {
                            balance.text = t?.balance
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
        val fragment = PayFragment()
        fragment.show(childFragmentManager, "Payment")
    }

    private fun setupRecycleView(root: View?) {
        recyclerView = root?.findViewById(R.id.list_screens) as RecyclerView?
        recyclerView?.layoutManager = LinearLayoutManager(activity)

        (activity as MainActivity).client.getAllScreens(
                object : Callback<List<Screen>> {
                    override fun success(t: List<Screen>?, response: retrofit.client.Response?) {
                        recyclerView?.adapter = ListScreensAdapter(t!!)
                    }

                    override fun failure(error: RetrofitError?) {
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
