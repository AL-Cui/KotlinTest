package com.example.cuiduo.wifitesting.MachineControl

import com.example.cuiduo.wifitesting.UtilClass.Util
import okhttp3.*
import org.dom4j.DocumentHelper
import java.io.IOException
import java.util.*

/**
 * Created by cuiduo on 2017/8/10.
 */

object UpdateStatus {


    var mOkHttpClient = OkHttpClient.Builder()
            .cookieJar(object : CookieJar {
                private val cookieStore = HashMap<HttpUrl, List<Cookie>>()

                override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
                    cookieStore.put(url, cookies)
                    cookieStore.put(HttpUrl.parse("https://shcloud-rd.sharp.cn/hems/upload/api/monitoring"), cookies)
                    for (cookie in cookies) {
                        println("cookie Name:" + cookie.name())
                        println("cookie Path:" + cookie.path())
                    }
                }

                override fun loadForRequest(url: HttpUrl): List<Cookie> {
                    val cookies = cookieStore[HttpUrl.parse("https://shcloud-rd.sharp.cn/hems/upload/api/monitoring")]
                    if (cookies == null) {
                        println("没加载到cookie")
                    }
                    return cookies ?: ArrayList<Cookie>()
                }
            })
            .build()

//    @JvmStatic fun main(args: Array<String>) {
//        // CreateRequest(macId,boxId,pass);
//        CreateRequest("FF-FF-FF-FF-CH-N1", "https://slchms-test.xicp.netclpf/key/k2DjVcJso905xRDxoRhLmNnQr3iPgVHKycejiz2hZSc", "nt6VM3an")
//
//    }

    fun CreateRequest(macId: String, boxId: String, pass: String) {

        val MEDIA_XML_APPLICATIOON = MediaType.parse("application/xml;gzip;keep-alive")
        val requestString = setXmlInfo(macId, boxId, pass)
        println("发送的xml：" + requestString)
        val request = Request.Builder()
                .url("https://shcloud-rd.sharp.cn/hems/control/api/cmonitoring")
                .post(RequestBody.create(MEDIA_XML_APPLICATIOON, requestString))
                .build()
        val call = mOkHttpClient.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {

                if (null != response.cacheResponse()) {
                    val str = response.cacheResponse().toString()
                    println("结果cache" + response.cacheResponse())
                    //                    Log.e("结果cache", "cache---" + response.cacheResponse());
                    //                    Log.e("结果network", "network---" + response.networkResponse());
                } else {
                    val resultString = response.body().string()
                    val str = response.networkResponse().toString()
                    println("错误代码：" + str)
                    println("结果1==" + resultString)

                    //                    Log.d("结果", "network---" + str);
                }

            }
        })

    }


    fun setXmlInfo(idString: String, boxId: String, passString: String): String {
        val writeDoc = DocumentHelper.createDocument()
        val root = writeDoc.addElement(Util.CMONITORING)
        val cmd = root.addElement(Util.CMD)
        cmd.setText(Util.CHECKCTRL)
        val info = root.addElement(Util.INFO)
        val monitor = info.addElement(Util.MONITOR)
        val dtver = monitor.addElement(Util.DTVER)
        dtver.setText(Util.DTVER_VALUE)
        val status = monitor.addElement(Util.STATUS)
        status.setText(Util.STATUS_VALUE3)
        val type = monitor.addElement(Util.TYPE)
        type.setText(Util.TYPE_VALUE)
        val id = monitor.addElement(Util.ID)
        id.setText(idString)
        val pass = monitor.addElement(Util.PASS)
        pass.setText(passString)
        val pv_addr = monitor.addElement(Util.PV_ADDR)
        pv_addr.setText(Util.PV_ADDR_VALUE)
        val data = root.addElement(Util.DATA)
        val checkctrl = data.addElement(Util.CHECKCTRL)

        checkctrl.setAttributeValue("version", "1.0")
        checkctrl.setAttributeValue("boxid", boxId)

        val devctrl = checkctrl.addElement(Util.DEVCTRL)
        val echo = devctrl.addElement(Util.ECHO)
        echo.setAttributeValue("node", idString)
        echo.setText("0135010ef0017301f10141")

        val boxconf = checkctrl.addElement(Util.BOXCONF)
        boxconf.setAttributeValue("check_boxid_deleted", "true")


        return writeDoc.asXML()
    }


}