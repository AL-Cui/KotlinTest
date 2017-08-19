package com.example.cuiduo.kotlin.ui.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem

import com.example.cuiduo.kotlin.R
import com.example.cuiduo.kotlin.domain.model.*
import com.example.cuiduo.kotlin.domain.network.*
import com.example.cuiduo.kotlin.snackbar
import com.example.cuiduo.kotlin.ui.WebDetailDialog
import com.example.cuiduo.kotlin.ui.adapter.PageAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_about.*
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.async
import org.jetbrains.anko.find
import org.jetbrains.anko.uiThread

class BookDetailActivity2 : AppCompatActivity() {

    lateinit var url:String
    lateinit var pageList:RecyclerView
    lateinit var adapter:PageAdapter
    lateinit var pageRefresh:SwipeRefreshLayout
    lateinit var bookDetail:BookDetail

    companion object{
        val INTENT_COVER_URL = "cover"
        val INTENT_URL = "url"
        val INTENT_TITLE = "title"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail2)
        setSupportActionBar(toolbar)
        init()
    }

    private fun init() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        val coverUrl = intent.getStringExtra(INTENT_COVER_URL)
        val title = intent.getStringExtra(INTENT_TITLE)
        url = intent.getStringExtra(INTENT_URL)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        collapsing_toolbar.title = title
        Picasso.with(this).load(coverUrl).into(backgroundImage)
        pageRefresh = find(R.id.pageRefresh)
        pageRefresh.setOnRefreshListener { load() }

        pageList = find(R.id.pageList)
        pageList.layoutManager = GridLayoutManager(this,4)

        adapter = PageAdapter{_,positopn ->
            if (title.contains("SBS")){
                val news = News(bookDetail[positopn].title,"",bookDetail[positopn].link)
                WebDetailDialog(this,news,SBSSource())
            }else{
                jump2read(positopn)
            }
        }

        pageList.adapter = adapter
    }

    private fun  jump2read(position: Int) {

        var intent = Intent(this, ComicActivity().javaClass)
        intent.putExtra(ComicActivity.INTENT_COMIC_URL, bookDetail[position].link)
        startActivity(intent)
    }

    private fun load() = async {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        bookDetail = BookDetailSource().obtain(url)
        val data = bookDetail.pages as ArrayList<Page>
        uiThread {
            adapter.refreshData(data)
            pageRefresh.isRefreshing = false
            if (bookDetail.size() == 0){
                showError()
            }
        }

    }

    private fun showError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        pageList.snackbar("页面加载失败，那有啥办法")
    }

    override fun onResume() {
        super.onResume()
        pageRefresh.post { pageRefresh.isRefreshing = true }
        load()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
       menuInflater.inflate(R.menu.menu_book_detail,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home){
            onBackPressed()
            return true
        }else{
            showBookInfo()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showBookInfo() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        val  bookInfo = bookDetail.info
        pageList.snackbar(bookInfo.description + "\n" + bookInfo.updateTime)
    }
}
