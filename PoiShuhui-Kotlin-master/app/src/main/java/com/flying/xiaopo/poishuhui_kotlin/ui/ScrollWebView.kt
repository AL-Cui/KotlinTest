package com.flying.xiaopo.poishuhui_kotlin.ui

import android.annotation.TargetApi
import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView

/**
 * TODO to know if scroll to bottom , and do better
 * @author wupanjie
 */
class ScrollWebView : WebView {
    private val TAG = ScrollWebView::class.java.simpleName

    var scrollListener: ((x: Int, y: Int) -> Unit)? = null
    var topListener: ((isScrollToTop: Boolean) -> Unit)? = null
    var bottomListener: ((isScrollToBottom: Boolean) -> Unit)? = null

    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    }

    @TargetApi(21)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)

        //        log("l->$l,t->$t,oldl->$oldl,oldt->$oldt")
        //        val webHeight = contentHeight * scaleY
        //        val nowHeight = height + scrollY

        //        log("webHeight->$webHeight,nowHeight->$nowHeight")

        //        if (Math.abs(webHeight - nowHeight) < 1) {
        //            //scroll to bottom
        //            bottomListener?.invoke(true)
        //            topListener?.invoke(false)
        //        } else if (scrollY == 0) {
        //            //scroll to top
        //            topListener?.invoke(true)
        //            bottomListener?.invoke(false)
        //        } else {
        //            scrollListener?.invoke(l, t)
        //            topListener?.invoke(false)
        //            bottomListener?.invoke(false)
        //        }
    }
}
