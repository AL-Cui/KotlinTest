package com.example.cuiduo.philosophyweather.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cuiduo.philosophyweather.Adapter.SamplePagerAdapter
import com.example.cuiduo.philosophyweather.R
import kotlinx.android.synthetic.main.fragment_sample_default.*

/**
 * Created by cuiduo on 2017/7/8.
 */
class DefaultFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_sample_default,container,false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        viewpager.adapter = SamplePagerAdapter()
        indicator.setViewPager(viewpager)
        viewpager.currentItem = 2
    }
}