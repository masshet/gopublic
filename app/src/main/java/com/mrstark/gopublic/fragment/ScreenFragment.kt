package com.mrstark.gopublic.fragment

import android.app.Fragment
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.mrstark.gopublic.R
import com.mrstark.gopublic.entity.Screen
import com.squareup.picasso.Picasso

class ScreenFragment(): Fragment() {
    private var toolbar: CollapsingToolbarLayout? = null
    private var description: TextView? = null
    private var cost: TextView? = null
    private var workTime: TextView? = null
    private var price: TextView? = null
    private var image: ImageView? = null
    var screen: Screen? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater?.inflate(R.layout.fragment_screen, container, false)
        init(root)
        initToolbar(root)
        loadData()
        return root
    }

    private fun initToolbar(root: View?) {
        toolbar = root?.findViewById(R.id.collapsing_toolbar) as CollapsingToolbarLayout
        toolbar?.title = screen?.address
        toolbar?.setExpandedTitleColor(android.R.color.transparent)
    }

    private fun loadData() {
        Picasso.with(context).load(screen?.image).into(image)
        description?.text = screen?.description
        cost?.text = screen?.cost.toString()
        workTime?.text = screen?.workTime
        price?.text = screen?.cost.toString()
    }

    private fun init(root: View?) {
        image = root?.findViewById(R.id.image) as ImageView
        description = root?.findViewById(R.id.description) as TextView
        cost = root?.findViewById(R.id.cost) as TextView
        workTime = root?.findViewById(R.id.work_time) as TextView
        price = root?.findViewById(R.id.full_price) as TextView
    }
}
