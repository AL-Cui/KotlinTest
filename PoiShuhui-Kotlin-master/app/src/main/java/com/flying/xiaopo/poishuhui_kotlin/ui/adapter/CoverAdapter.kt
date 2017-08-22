package com.flying.xiaopo.poishuhui_kotlin.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.flying.xiaopo.poishuhui_kotlin.R
import com.flying.xiaopo.poishuhui_kotlin.domain.model.Cover
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_cover.view.*
import java.util.*

/**
 * @author wupanjie
 */
class CoverAdapter(var data: List<Cover> = ArrayList(), var itemClick: (View, Int) -> Unit)
: RecyclerView.Adapter<CoverAdapter.CoverViewHolder>() {

    override fun onBindViewHolder(holder: CoverViewHolder, position: Int) {
        bindView(holder.itemView, position)
    }

    private fun bindView(itemView: View, position: Int) {
        val cover = data[position]
        itemView.tv_cover.text = cover.title
        Picasso.with(itemView.context).load(cover.coverUrl).into(itemView.iv_cover)
        itemView.coverContainer.setOnClickListener {
            itemClick(itemView, position)
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): CoverViewHolder? {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_cover, parent, false)
        return CoverViewHolder(itemView)
    }

    fun refreshData(newData: List<Cover>) {
        data = newData
        notifyDataSetChanged()
    }


    class CoverViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

}