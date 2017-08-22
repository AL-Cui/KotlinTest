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
import com.flying.xiaopo.poishuhui_kotlin.domain.network.CoverSource
import com.flying.xiaopo.poishuhui_kotlin.log
import com.flying.xiaopo.poishuhui_kotlin.ui.activity.ComicActivity
import com.flying.xiaopo.poishuhui_kotlin.ui.adapter.CoverAdapter
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread
import java.util.*

/**
 * First Page
 * @author wupanjie
 */
class HomeFragment : Fragment() {
    companion object{
        val AIM_URL = "http://ishuhui.net/?PageIndex=1"
    }
    var mData = ArrayList<Cover>()

    lateinit var coverList: RecyclerView

    lateinit var homeRefresh: SwipeRefreshLayout

    lateinit var adapter: CoverAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        log("onCreateView")

        return inflater?.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        log("onViewCreated")
        initView(view)
    }

    /**
     * init setting view
     */
    private fun initView(view: View) {
        homeRefresh = view.findViewById(R.id.homeRefresh) as SwipeRefreshLayout
        coverList = view.findViewById(R.id.homeList) as RecyclerView

        coverList.layoutManager = GridLayoutManager(context, 2)

        adapter = CoverAdapter { view: View, position: Int -> jump2Comic(position) }
        coverList.adapter = adapter

        homeRefresh.setOnRefreshListener {
            load()
        }
        homeRefresh.post { homeRefresh.isRefreshing = true }
    }

    private fun jump2Comic(position: Int) {
//        homeRefresh.snackbar(mData[position].link)
        var intent = Intent(context, ComicActivity().javaClass)
        intent.putExtra(ComicActivity.INTENT_COMIC_URL, mData[position].link)
        startActivity(intent)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && mData.size == 0) {
            load()
        }

    }

    private fun load() {
        async() {
            val data = CoverSource().obtain(AIM_URL)

            uiThread {
                mData = data
                adapter.refreshData(data)
                homeRefresh.isRefreshing = false
            }
        }
    }

}