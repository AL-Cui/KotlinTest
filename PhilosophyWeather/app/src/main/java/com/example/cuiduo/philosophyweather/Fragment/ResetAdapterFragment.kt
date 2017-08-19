package com.example.cuiduo.philosophyweather.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cuiduo.philosophyweather.Adapter.SamplePagerAdapter
import com.example.cuiduo.philosophyweather.R
import kotlinx.android.synthetic.main.fragment_sample_reset_viewpager.*
import java.util.*

/**
 * Created by cuiduo on 2017/7/11.
 */
class ResetAdapterFragment : Fragment() {

    lateinit var adapter: SamplePagerAdapter
    val mRandom = Random()
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_sample_reset_viewpager, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        adapter = SamplePagerAdapter(5)
        viewPager.adapter = adapter
        indicator.setViewPager(viewPager)
        button.setOnClickListener {
            viewPager.adapter = SamplePagerAdapter(1 + mRandom.nextInt(5))
            indicator.setViewPager(viewPager)
        }
    }
}