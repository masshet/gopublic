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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        var recyclerView = root?.findViewById(R.id.list_orders) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val call = (activity as MainActivity).api.getOrders(
                activity.getPreferences(AppCompatActivity.MODE_PRIVATE).getString((activity as MainActivity).KEY_CREDENTIAL, "")
        )
        call.enqueue(object : Callback<List<Order>> {
            override fun onResponse(call: Call<List<Order>>?, response: Response<List<Order>>?) {
                recyclerView.adapter = ListOrdersAdapter(response?.body()!!)
            }

            override fun onFailure(call: Call<List<Order>>?, t: Throwable?) {

            }

        })
    }

}