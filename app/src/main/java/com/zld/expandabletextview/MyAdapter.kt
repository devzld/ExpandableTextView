package com.zld.expandabletextview

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zld.expandlayout.ExpandLayout

/**
 *  Created by lingdong on 2018/5/6.
 *
 *
 **/
class MyAdapter(private var context: Context, private var list: List<TextBean>) : RecyclerView.Adapter<MyAdapter.Companion.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_text, null))
    }

    override fun getItemCount(): Int {
        return if (list == null) 0 else list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var bean = list[holder.adapterPosition]
        holder.expandLayout.setText(bean.text, bean.expand, object : ExpandLayout.OnExpandListener {
            override fun expandChange() {
                bean.expand = !bean.expand
                notifyItemChanged(holder.adapterPosition)
            }
        })
    }


    companion object {
        class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            var expandLayout = view.findViewById<ExpandLayout>(R.id.expand_layout)
        }
    }
}