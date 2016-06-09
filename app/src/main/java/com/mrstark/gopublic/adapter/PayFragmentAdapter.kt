package com.mrstark.gopublic.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.mrstark.gopublic.R

class PayFragmentAdapter() : RecyclerView.Adapter<PayFragmentAdapter.ViewHolder>() {
    val icons = arrayListOf(R.drawable.assistlogo, R.drawable.eriplogo, R.drawable.ipaylife, R.drawable.ipaymts, R.drawable.ipayvel, R.drawable.webpaylogo)
    val titles = arrayListOf("Банковской картой", "Банковской картой", "Через SMS", "Через SMS", "Через SMS", "Банковской картой")
    val subTitles = arrayListOf("Visa, MasterCard", "Visa, MasterCard", "Life", "MTC", "Velcom", "Visa, MasterCard")

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.icon.setImageResource(icons[position])
        holder.title.text = titles[position]
        holder.subTitle.text = subTitles[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder? {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.list_item_payment_dialog, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return icons.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icon = view.findViewById(R.id.icon) as ImageView
        val title = view.findViewById(R.id.title) as TextView
        val subTitle = view.findViewById(R.id.sub_title) as TextView
    }
}