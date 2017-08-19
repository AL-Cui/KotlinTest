package com.example.cuiduo.kotlin.ui

import android.content.Context
import android.support.design.widget.BottomSheetDialog
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.webkit.WebSettings
import com.example.cuiduo.kotlin.R
import com.example.cuiduo.kotlin.domain.model.News
import com.example.cuiduo.kotlin.domain.network.NewsDetailSource
import com.example.cuiduo.kotlin.domain.network.Source
import com.example.cuiduo.kotlin.load

import org.jetbrains.anko.async
import org.jetbrains.anko.find
import org.jetbrains.anko.uiThread

/**
 * A bottom dialog to display the news detail
 * @author wupanjie
 */
class WebDetailDialog(val context: Context, val news: News, val source: Source<String>) {
    val dialog = BottomSheetDialog(context)

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_web_detail, null)
        val webView = view.find<ScrollWebView>(R.id.webView)
        val titleBar = view.find<Toolbar>(R.id.titleBar)
        val refresh = view.find<SwipeRefreshLayout>(R.id.newsDetailRefresh)

        titleBar.subtitle = news.title

        webView.settings.textZoom = 80
        webView.settings.displayZoomControls = true
        webView.settings.setSupportZoom(true)
        webView.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
//        webView.bottomListener = { isScrollToBottom: Boolean -> if (isScrollToBottom) dialog.dismiss() }

        dialog.setContentView(view)

        refresh.post { refresh.isRefreshing = true }


        async() {
            val html = NewsDetailSource().obtain(news.link)
            uiThread {
                webView.load(html)
                refresh.isRefreshing = false
            }
        }

        refresh.setOnRefreshListener {
            async() {
                val html = source.obtain(news.link)
                uiThread {
                    webView.load(html)
                    refresh.isRefreshing = false
                }
            }
        }

        dialog.show()

    }

}




