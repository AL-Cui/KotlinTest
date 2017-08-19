package com.example.cuiduo.kotlin

import android.app.DownloadManager
import android.content.ContentProvider
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.cuiduo.kotlin.ui.activity.AboutActivity
import com.example.cuiduo.kotlin.ui.adapter.ContentPagerAdapter
import com.example.cuiduo.kotlin.ui.fragment.*
import com.squareup.okhttp.*
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.content_main.*
import java.io.IOException
import java.net.URI

class Main2Activity : AppCompatActivity() {

    companion object {
        val GITHUB_URL = "https://github.com/wuapnjie/PoiShuhui-Kotlin"
    }

    val nameReList :ArrayList<Int> = arrayListOf(R.string.tab_one,R.string.tab_two,R.string.tab_three)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        init()

    }

    private fun init() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        setSupportActionBar(toolbar)
        fab.setOnClickListener { junmp2github() }
        val fragments = ArrayList<android.support.v4.app.Fragment>()
        fragments.add(BookFragment())
        fragments.add(HomeFragment())
        fragments.add(NewsFragment())

        val nameList = nameReList.map(this::getString)
        viewPager.adapter = ContentPagerAdapter(fragments,nameList,supportFragmentManager)
        viewPager.offscreenPageLimit =2
    }

    private fun junmp2github() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        val uri = Uri.parse(GITHUB_URL)
        val intent = Intent(this,AboutActivity.javaClass )
        startActivity(intent)
        var mOkHttpClient: OkHttpClient
        val requestBuilder = Request.Builder().url("http://192.168.1.233:8848")
        requestBuilder.method("GET", null)
        val request = requestBuilder.build()
        val call = mOkHttpClient.newCall(request)
        call.enqueue(object: Callback {


            override  fun onFailure(request: Request, e: IOException) {

            }

            @Throws(IOException::class)
            override fun onResponse(response: Response) {

                if (null != response.cacheResponse()) {
                    val str = response.cacheResponse().toString()
                    Log.d("结果", "cache---" + str)
                } else {
                    response.body().string()
                    val str = response.networkResponse().toString()
                    Log.d("结果", "network---" + str)
                }
                runOnUiThread { Toast.makeText(applicationContext, "请求成功", Toast.LENGTH_SHORT).show() }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_about){
            var intent = Intent(this,AboutActivity.javaClass)
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}

