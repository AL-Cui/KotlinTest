package com.example.cuiduo.kotlin.domain.network

import android.util.Log
import com.example.cuiduo.kotlin.domain.model.Cover
import com.example.cuiduo.kotlin.getHtml
import org.jsoup.Jsoup
import java.util.*

/**
 * @author wupanjie
 */
class CoverSource() : Source<ArrayList<Cover>> {
    override fun obtain(url: String): ArrayList<Cover> {
        val list = ArrayList<Cover>()

        val html = getHtml(url)
        val doc = Jsoup.parse(html)
        Log.d("XJBT",doc.toString())
        val elements = doc.select("ul.mangeListBox").select("li")
        for (element in elements) {
            val coverUrl = element.select("img").attr("src")
            val title = element.select("h1").text() + "\n" + element.select("h2").text()
            val link = "http://ishuhui.net" + element.select("div.magesPhoto").select("a").attr("href")
            val cover = Cover(coverUrl, title, link)
            list.add(cover)
        }

        return list
    }

}