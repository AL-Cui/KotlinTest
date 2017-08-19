package com.example.cuiduo.kotlin.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

import com.example.cuiduo.kotlin.R
import com.example.cuiduo.kotlin.domain.model.Comic
import com.example.cuiduo.kotlin.domain.network.ComicSource
import com.example.cuiduo.kotlin.ui.fragment.ComicFragment
import kotlinx.android.synthetic.main.activity_comic2.*
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread

class ComicActivity2(var mData:ArrayList<Comic> = ArrayList<Comic>()) : AppCompatActivity() {

    companion object{
            val INTENT_COMIC_URL = "url"
    }
    lateinit var url:String
    lateinit var adapter:ComicPagerAdapter2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comic2)
        url = intent.getStringExtra(INTENT_COMIC_URL)
        adapter = ComicPagerAdapter2(mData,supportFragmentManager)
        comicPagers2.adapter =adapter
        comicPagers2.offscreenPageLimit = 2
    }

    override fun onResume() {
        super.onResume()
        async {
            val data = ComicSource().obtain(url)
            mData = data
            uiThread {
                adapter.refreshData(mData)
            }
        }
    }

    class ComicPagerAdapter2(var data:ArrayList<Comic>,fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager){
        override fun getItem(position: Int): Fragment? = newInstance(data[position].comicUrl)

        override fun getCount(): Int = data.size

        fun refreshData(newData : ArrayList<Comic>){
            data = newData
            notifyDataSetChanged()
        }
        fun newInstance(url:String) = ComicFragment(url)


    }
}
