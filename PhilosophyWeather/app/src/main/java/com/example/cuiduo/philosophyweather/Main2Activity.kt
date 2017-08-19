package com.example.cuiduo.philosophyweather

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.example.cuiduo.philosophyweather.Adapter.ContentPagerAdapter
import com.example.cuiduo.philosophyweather.Fragment.HomeFragment
import com.example.cuiduo.philosophyweather.Fragment.RoomAirEveryHour
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.content_main.*

class Main2Activity : AppCompatActivity() {

    val nameReList:ArrayList<String> = arrayListOf("当前天气","空气质量")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        init()
    }

    private fun init(){
        val fragments = ArrayList<Fragment>()
        fragments.add(HomeFragment())
        fragments.add(RoomAirEveryHour())

        viewPager.adapter = ContentPagerAdapter(fragments,nameReList,supportFragmentManager)
        viewPager.offscreenPageLimit = 2
        tabLayout.setupWithViewPager(viewPager)
    }
}
