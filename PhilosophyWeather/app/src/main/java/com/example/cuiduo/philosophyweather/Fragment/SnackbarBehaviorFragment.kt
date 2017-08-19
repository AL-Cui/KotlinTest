package com.example.cuiduo.philosophyweather.Fragment

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cuiduo.philosophyweather.Adapter.SamplePagerAdapter
import com.example.cuiduo.philosophyweather.R
import kotlinx.android.synthetic.main.fragment_sample_snacbar_viewpager.*

/**
 * Created by cuiduo on 2017/7/12.
 */
class SnackbarBehaviorFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_sample_snacbar_viewpager, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        viewPager1.adapter = SamplePagerAdapter()
        indicator.setViewPager(viewPager1)
        val mSnackbar: Snackbar = Snackbar.make(view!!.findViewById(R.id.coordinator_layout), "SnackBar", Snackbar.LENGTH_SHORT)
        button.setOnClickListener {
            if (!mSnackbar.isShown) {
                mSnackbar.show()
            } else {
                mSnackbar.dismiss()
            }
        }
    }
}