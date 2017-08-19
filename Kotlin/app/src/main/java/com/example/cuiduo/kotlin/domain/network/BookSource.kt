package com.example.cuiduo.kotlin.domain.network

import com.example.cuiduo.kotlin.domain.model.Cover
import com.example.cuiduo.kotlin.getHtml
import com.example.cuiduo.kotlin.log
import org.jsoup.Jsoup
import java.util.*

/**
 * @author wupanjie
 */
class BookSource() : Source<ArrayList<Cover>> {
    override fun obtain(url: String): ArrayList<Cover> {
        val list = ArrayList<Cover>()
        log(url)
        val html = getHtml(url)
        log(html)
        val doc = Jsoup.parse(html)
        // var doc = Jsoup.connect(url).get()

        val elements = doc.select("ul.chinaMangaContentList").select("li")

        for (element in elements) {
            val coverUrl = if (element.select("img").attr("src").contains("http")) {
                element.select("img").attr("src")
            } else {
                "http://ishuhui.net" + element.select("img").attr("src")
            }

            val title = element.select("p").text()
            val link = "http://ishuhui.net" + element.select("div.chinaMangaContentImg").select("a").attr("href")

            val cover = Cover(coverUrl, title, link)
            list.add(cover)
        }

        return list
    }

}