package com.mrstark.gopublic.fragment

import android.app.DialogFragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mrstark.gopublic.R
import com.mrstark.gopublic.adapter.PayFragmentAdapter


class PayFragment : DialogFragment() {
    private var recyclerView: RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.setTitle(R.string.way_yo_pay)
        val view = inflater?.inflate(R.layout.dialog_payment, null)
        recyclerView = view?.findViewById(R.id.payment_list) as RecyclerView?
        recyclerView?.layoutManager = LinearLayoutManager(activity)
        recyclerView?.adapter = PayFragmentAdapter()
        return view
    }
}