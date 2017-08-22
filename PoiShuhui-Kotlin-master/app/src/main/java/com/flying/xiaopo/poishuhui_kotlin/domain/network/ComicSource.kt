package com.flying.xiaopo.poishuhui_kotlin.domain.network

import com.flying.xiaopo.poishuhui_kotlin.domain.model.Comic
import com.flying.xiaopo.poishuhui_kotlin.getHtml
import org.jsoup.Jsoup
import java.util.*

/**
 * Created by Flying SnowBean on 16-3-9.
 */
class ComicSource() : Source<ArrayList<Comic>> {
    override fun obtain(url: String): ArrayList<Comic> {
        val html = getHtml(url)
        val doc = Jsoup.parse(html)

        val elements = doc.select("div.mangaContentMainImg").select("img")
        val list = ArrayList<Comic>()

        for (element in elements) {
            var comicUrl: String
            val temp = element.attr("src")
            if (temp.contains(".png") || temp.contains(".jpg") || temp.contains(".JPEG")) {
                comicUrl = temp
            } else if (!"".equals(element.attr("data-original"))) {
                comicUrl = element.attr("data-original")
            } else {
                continue
            }

            val comic = Comic(comicUrl)
            list.add(comic)
        }
        return list
    }
}