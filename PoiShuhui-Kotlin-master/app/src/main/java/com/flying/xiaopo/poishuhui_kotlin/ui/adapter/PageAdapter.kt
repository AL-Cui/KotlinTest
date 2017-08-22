package com.flying.xiaopo.poishuhui_kotlin.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.flying.xiaopo.poishuhui_kotlin.R
import com.flying.xiaopo.poishuhui_kotlin.domain.model.Page

import kotlinx.android.synthetic.main.item_page.view.*
import java.util.*


class PageAdapter(var data: List<Page> = ArrayList(), val itemClick: (View, Int) -> Unit)
: RecyclerView.Adapter<PageAdapter.PageAdapterViewHolder>() {
    override fun onBindViewHolder(holder: PageAdapterViewHolder, position: Int) {
        bindView(holder.itemView, position)
    }

    private fun bindView(itemView: View, position: Int) {
        val page = data[position]
        itemView.tv_page.text = page.title
        itemView.tv_page.setOnClickListener { itemClick(itemView, position) }

    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): PageAdapterViewHolder? {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_page, parent, false)
        return PageAdapterViewHolder(itemView)
    }

    fun refreshData(newData: List<Page>) {
        data = newData
        notifyDataSetChanged()
    }

    class PageAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

}
