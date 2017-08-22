package com.flying.xiaopo.poishuhui_kotlin.ui.adapter

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.flying.xiaopo.poishuhui_kotlin.R
import com.flying.xiaopo.poishuhui_kotlin.domain.model.NewsContainer

import kotlinx.android.synthetic.main.item_news.view.*
import java.util.*

class NewsContainerAdapter(var data: List<NewsContainer> = ArrayList())
: RecyclerView.Adapter<NewsContainerAdapter.NewsContainerAdapterViewHolder>() {

    override fun onBindViewHolder(holder: NewsContainerAdapterViewHolder, position: Int) {
        bindView(holder.itemView, position)
    }

    private fun bindView(itemView: View, position: Int) {
        val newsContainer = data[position]
        itemView.tv_container_title.text = newsContainer.title

        itemView.rv_child_container.layoutManager = LinearLayoutManager(itemView.context)
        itemView.rv_child_container.adapter =
                NewsAdapter(newsContainer.newsList)
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): NewsContainerAdapterViewHolder? {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return NewsContainerAdapterViewHolder(itemView)
    }

    fun refreshData(newData: List<NewsContainer>) {
        data = newData
        notifyDataSetChanged()
    }

    class NewsContainerAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

}