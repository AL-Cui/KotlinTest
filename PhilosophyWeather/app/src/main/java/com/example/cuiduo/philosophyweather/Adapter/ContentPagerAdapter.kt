package com.example.cuiduo.philosophyweather.Adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.ViewGroup

/**
 * Created by cuiduo on 2017/6/15.
 */
class ContentPagerAdapter (val fragments: List<Fragment>,val nameList:List<String>, val fragmentManager: FragmentManager)
    : FragmentPagerAdapter(fragmentManager){
    override fun getCount(): Int = fragments.size

    override fun getItem(position: Int): Fragment? {
        return fragments[position]
    }

    override fun getPageTitle(position: Int): CharSequence? = nameList[position]

    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
        super.destroyItem(container, position, `object`)
    }

}