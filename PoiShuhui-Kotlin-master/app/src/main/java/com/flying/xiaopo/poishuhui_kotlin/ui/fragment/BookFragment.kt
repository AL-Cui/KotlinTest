package com.flying.xiaopo.poishuhui_kotlin.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.flying.xiaopo.poishuhui_kotlin.R
import com.flying.xiaopo.poishuhui_kotlin.domain.model.Cover
import com.flying.xiaopo.poishuhui_kotlin.domain.network.BookSource
import com.flying.xiaopo.poishuhui_kotlin.log
import com.flying.xiaopo.poishuhui_kotlin.ui.activity.BookDetailActivity
import com.flying.xiaopo.poishuhui_kotlin.ui.adapter.CoverAdapter
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread
import java.util.*

/**
 * Second page
 * @author wupanjie
 */
class BookFragment : Fragment() {
    companion object{
        val AIM_URL = "http://ishuhui.net/ComicBookList/"
    }
    var mData = ArrayList<Cover>()
    lateinit var adapter: CoverAdapter
    lateinit var bookList: RecyclerView
    lateinit var bookRefresh: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_book, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView(view)

    }

    /**
     * init setting views
     */
    private fun initView(view: View) {
        bookRefresh = view.findViewById(R.id.bookRefresh) as SwipeRefreshLayout
        bookList = view.findViewById(R.id.bookList) as RecyclerView

        bookList.layoutManager = GridLayoutManager(context, 2)
        adapter = CoverAdapter { _: View, i: Int -> jump2Detail(i) }
        bookList.adapter = adapter

        bookRefresh.setOnRefreshListener {
            load()
        }
        bookRefresh.post { bookRefresh.isRefreshing = true }
    }

    /**
     * click event to detail activity
     */
    private fun jump2Detail(position: Int) {
        var intent = Intent(context, BookDetailActivity().javaClass)

        intent.putExtra(BookDetailActivity.INTENT_COVER_URL, mData[position].coverUrl)
        intent.putExtra(BookDetailActivity.INTENT_URL, mData[position].link)
        intent.putExtra(BookDetailActivity.INTENT_TITLE, mData[position].title)
        startActivity(intent)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        log("setUserVisibleHint")
        if (isVisibleToUser && mData.size == 0) {
            load()
        }

    }

    private fun load() {
        async() {
            val data = BookSource().obtain(AIM_URL)

            uiThread {
                mData = data
                adapter.refreshData(data)
                bookRefresh.isRefreshing = false
            }
        }
    }

}