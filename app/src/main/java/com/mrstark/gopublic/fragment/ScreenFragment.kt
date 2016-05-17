package com.mrstark.gopublic.fragment

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mrstark.gopublic.R
import com.mrstark.gopublic.entity.Screen

class ScreenFragment(val screen: Screen): Fragment() {
    var description: TextView? = null
    var cost: TextView? = null
    var workTime: TextView? = null
    var price: TextView? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater?.inflate(R.layout.fragment_screen, container, false)
        init(root)
        loadData()
        return root
    }

    private fun loadData() {
        description?.text = screen.description
        cost?.text = screen.cost.toString()
        workTime?.text = screen.workTime
        price?.text = screen.cost.toString()
    }

    private fun init(root: View?) {
        description = root?.findViewById(R.id.description) as TextView
        cost = root?.findViewById(R.id.cost) as TextView
        workTime = root?.findViewById(R.id.work_time) as TextView
        price = root?.findViewById(R.id.full_price) as TextView
    }
}
