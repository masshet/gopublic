package com.mrstark.gopublic.fragment

import android.app.Fragment
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mrstark.gopublic.R
import com.mrstark.gopublic.adapter.ListScreensAdapter
import com.mrstark.gopublic.api.Api
import com.mrstark.gopublic.entity.Screen
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CityScreensFragment: Fragment() {

    private var BASE_URL = "http://gopublic.by/"

    private var toolbar: Toolbar? = null
    private var layout: DrawerLayout? = null
    private var recyclerView: RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater?.inflate(R.layout.fragment_city_screens, container, false)
        setupToolbar(root)
        setupNavigationView(root)
        setupRecycleView(root)
        return root
    }

    private fun setupNavigationView(root: View?) {
        layout = root?.findViewById(R.id.drawer_layout) as DrawerLayout

        val toggle = ActionBarDrawerToggle(activity, layout, toolbar, R.string.view_navigation_open, R.string.view_navigation_close)
        layout?.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun setupRecycleView(root: View?) {
        recyclerView = root?.findViewById(R.id.list_screens) as RecyclerView?
        recyclerView?.layoutManager = LinearLayoutManager(context)

        var retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var apiService = retrofit.create(Api::class.java)
        var call = apiService.getAllScreens()
        call.enqueue(object : Callback<List<Screen>> {
            override fun onResponse(call: Call<List<Screen>>?, response: Response<List<Screen>>) {
                recyclerView?.adapter = ListScreensAdapter(response.body())
            }

            override fun onFailure(call: Call<List<Screen>>?, t: Throwable?) {

            }

        })
    }

    private fun setupToolbar(root: View?) {
        toolbar = root?.findViewById(R.id.toolbar) as Toolbar
        toolbar?.setTitle(R.string.city_screens_fragment)
    }
}
