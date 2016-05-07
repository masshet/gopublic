package com.mrstark.gopublic.fragment

import android.app.Fragment
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mrstark.gopublic.R

/**
 * Created by mrstark on 5/7/16.
 */
class CityScreensFragment: Fragment() {

    private var toolbar: Toolbar? = null
    private var layout: DrawerLayout? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater?.inflate(R.layout.fragment_city_screens, container, false)
        setupToolbar(root)
        setupNavigationView(root)
        setupRecycleView()
        return root
    }

    private fun setupNavigationView(root: View?) {
        layout = root?.findViewById(R.id.drawer_layout) as DrawerLayout

        val toggle = ActionBarDrawerToggle(activity, layout, toolbar, R.string.view_navigation_open, R.string.view_navigation_close)
        layout?.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun setupRecycleView() {
    }

    private fun setupToolbar(root: View?) {
        toolbar = root?.findViewById(R.id.toolbar) as Toolbar
        toolbar?.setTitle(R.string.city_screens_fragment)
    }
}
