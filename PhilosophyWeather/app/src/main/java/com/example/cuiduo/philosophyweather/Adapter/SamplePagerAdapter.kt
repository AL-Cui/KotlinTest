package com.example.cuiduo.philosophyweather.Adapter

import android.graphics.Color
import android.support.v4.view.PagerAdapter
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.jetbrains.anko.textColor
import java.util.*

/**
 * Created by cuiduo on 2017/7/8.
 */
open class SamplePagerAdapter : PagerAdapter {

    companion object{
        val random:Random = Random()
        var mSize:Int = 0
    }
    constructor(){
        mSize = 5
    }
    constructor(count:Int){
        mSize = count
    }
    override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
        return view  == `object`
    }

    override fun getCount() = mSize

    override fun destroyItem(view:ViewGroup, position: Int, any: Any){
        view.removeView(any as View)
    }

    override fun instantiateItem(container: ViewGroup?, position: Int): Any {
        val textView = TextView(container!!.context)
        textView.text=(position+1).toString()
        textView.setBackgroundColor(0xff000000.toInt() or random.nextInt(0x00ffffff))
        textView.gravity = Gravity.CENTER
        textView.textColor = Color.WHITE
        textView.textSize = 48F
        container.addView(textView,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
        return textView
    }
    fun addItem(){
        mSize++
        notifyDataSetChanged()
    }
    fun removeItem(){
        mSize--
        mSize = if (mSize <0) 0 else mSize
        notifyDataSetChanged()
    }
}
