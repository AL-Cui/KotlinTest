package com.example.cuiduo.philosophyweather.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cuiduo.philosophyweather.Adapter.SamplePagerAdapter
import com.example.cuiduo.philosophyweather.R
import kotlinx.android.synthetic.main.fragment_sample_dynamic_adapter.*

/**
 * Created by cuiduo on 2017/7/11.
 */
class DynamicAdapterFragment : Fragment(), View.OnClickListener {
    val mAdapter : SamplePagerAdapter by lazy { object :SamplePagerAdapter(1){
        override fun getItemPosition(any: Any?): Int {
            return POSITION_NONE
        }
    } }
    override fun onClick(v: View?) {
        when(v){
            add -> mAdapter.addItem()
            remove -> mAdapter.removeItem()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_sample_dynamic_adapter,container,false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        add.setOnClickListener(this)
        remove.setOnClickListener(this)
        viewPager.adapter = mAdapter
        indicator.setViewPager(viewPager)
        mAdapter.registerDataSetObserver(indicator.dataSetObserver)
    }
}