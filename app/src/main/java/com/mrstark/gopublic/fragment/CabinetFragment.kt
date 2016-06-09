package com.mrstark.gopublic.fragment

import android.app.Fragment
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mrstark.gopublic.MainActivity
import com.mrstark.gopublic.R
import com.mrstark.gopublic.adapter.ListOrdersAdapter
import com.mrstark.gopublic.entity.Order
import retrofit.RetrofitError

class CabinetFragment : Fragment() {

    private var toolbar: Toolbar? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_cabinet, container, false)
        setupToolbar(view)
        setupRecyclerView(view)
        return view
    }

    private fun setupToolbar(view: View?) {
        toolbar = view?.findViewById(R.id.toolbar) as Toolbar
        toolbar?.setNavigationOnClickListener { (activity as MainActivity).onBackPressed() }
    }

    private fun setupRecyclerView(root: View?) {
        val recyclerView = root?.findViewById(R.id.list_orders) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)
        (activity as MainActivity).client.getOrders(
                activity.getPreferences(AppCompatActivity.MODE_PRIVATE).getString((activity as MainActivity).KEY_CREDENTIAL, ""),
                object : retrofit.Callback<List<Order>> {
                    override fun failure(error: RetrofitError?) {

                    }

                    override fun success(t: List<Order>?, response: retrofit.client.Response?) {
                        recyclerView.adapter = ListOrdersAdapter(t!!)
                    }

                })
    }

}