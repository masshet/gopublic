package com.mrstark.gopublic.adapter

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.mrstark.gopublic.MainActivity
import com.mrstark.gopublic.R
import com.mrstark.gopublic.entity.Screen
import com.squareup.picasso.Picasso

class ListScreensAdapter(var list: List<Screen>) : RecyclerView.Adapter<ListScreensAdapter.ViewHolder>() {
    var context: Context? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = list[position].address
        holder.coast.text = list[position].cost.toString()
        holder.workTime.text = list[position].workTime
        Picasso.with(context).load(list[position].image).into(holder.image)
        holder.card.setOnClickListener { (context as MainActivity).makeAnOrder(list[position]) }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder? {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.list_item_screen, parent, false)
        context = view.context
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val card = view.findViewById(R.id.card_view) as CardView
        val title = view.findViewById(R.id.title) as TextView
        val coast = view.findViewById(R.id.coast) as TextView
        val workTime = view.findViewById(R.id.work_time) as TextView
        val image = view.findViewById(R.id.image) as ImageView
    }
}