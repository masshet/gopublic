package com.mrstark.gopublic.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.mrstark.gopublic.R
import com.mrstark.gopublic.entity.Order
import com.mrstark.gopublic.entity.Screen
import com.squareup.picasso.Picasso

class ListOrdersAdapter(
        var list: List<Order>
) : RecyclerView.Adapter<ListOrdersAdapter.ViewHolder>() {
    var context: Context? = null

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.title?.text = list[position].screenId.toString()
        Picasso.with(context).load(list[position].path).into(holder?.image)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder? {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.list_item_order, parent, false)
        context = view.context
        return ViewHolder(view)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image = view.findViewById(R.id.order_image) as ImageView
        val title = view.findViewById(R.id.order_title) as TextView
    }
}