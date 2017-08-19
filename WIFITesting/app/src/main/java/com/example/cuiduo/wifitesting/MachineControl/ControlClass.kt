package com.example.cuiduo.wifitesting.MachineControl

import com.example.cuiduo.wifitesting.UtilClass.Util
import okhttp3.*
import org.dom4j.DocumentHelper
import java.io.IOException
import java.util.*

/**
 * Created by cuiduo on 2017/8/3.
 */
//创建单例
object ControlClass {

    var mOkHttpClient = OkHttpClient.Builder()
            .cookieJar(object : CookieJar {
                private val cookieStore = HashMap<HttpUrl, List<Cookie>>()

                override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
                    cookieStore.put(url, cookies)
                    //TODO 看postman上的cookie地址！！！！！
                    cookieStore.put(HttpUrl.parse("https://shcloud-rd.sharp.cn/hems/upload/api/monitoring"), cookies)
                    for (cookie in cookies) {
                        println("cookie Name:" + cookie.name())
                        println("cookie Path:" + cookie.path())
                    }
                }

                override fun loadForRequest(url: HttpUrl): List<Cookie> {
                    //TODO 看postman上的cookie地址！！！！！
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
                    //TODO 解析第一次返回的xml
                    val xx = parserXmlId(resultString)

                    //TODO 给第二次请求需要的参数
                    CreateSecondRequest(macId, boxId, pass)
                    //                    Log.d("结果", "network---" + str);
                }

            }
        })

    }

    private fun CreateSecondRequest(macId: String, boxId: String, pass: String) {

        val MEDIA_XML_APPLICATIOON = MediaType.parse("application/xml;gzip;keep-alive")
        val requestString = setXmlInfo2(macId, boxId, pass)
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
                    CreateSecondRequest(macId, boxId, pass)
                    //                    Log.d("结果", "network---" + str);
                }

            }
        })

    }

    fun setXmlInfo(idString: String, boxId: String, passString: String): String {
        val writeDoc = DocumentHelper.createDocument()
        val root = writeDoc.addElement(Util.CMONITORING)
        val cmd = root.addElement(Util.CMD)
        cmd.text = Util.CHECKCTRL
        val info = root.addElement(Util.INFO)
        val monitor = info.addElement(Util.MONITOR)
        val dtver = monitor.addElement(Util.DTVER)
        dtver.text = Util.DTVER_VALUE
        val status = monitor.addElement(Util.STATUS)
        status.text = Util.STATUS_VALUE3
        val type = monitor.addElement(Util.TYPE)
        type.text = Util.TYPE_VALUE
        val id = monitor.addElement(Util.ID)
        id.text = idString
        val pass = monitor.addElement(Util.PASS)
        pass.text = passString
        val pv_addr = monitor.addElement(Util.PV_ADDR)
        pv_addr.text = Util.PV_ADDR_VALUE
        val data = root.addElement(Util.DATA)
        val checkctrl = data.addElement(Util.CHECKCTRL)

        checkctrl.setAttributeValue("version", "1.0")
        checkctrl.setAttributeValue("boxid", boxId)

        val boxconf = checkctrl.addElement(Util.BOXCONF)
        boxconf.setAttributeValue("check_boxid_deleted", "true")


        return writeDoc.asXML()
    }

    //TODO 解析第一次返回的XML
    fun parserXmlId(XMLString: String): String {

        var boxId = ""

        try {
            val readDoc = DocumentHelper.parseText(XMLString)
            val rootNode = readDoc.rootElement//MONITORING

            if (Util.MONITORING == rootNode.name) {
                val data = rootNode.element(Util.DATA)
                val extension = data.element(Util.EXTENSION)

                boxId = extension.elementTextTrim(Util.BOX_ID)
                println("boxId==" + boxId)

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return boxId
    }

    private fun setXmlInfo2(macId: String, boxId: String, passString: String): String {
        val writeDoc = DocumentHelper.createDocument()
        val root = writeDoc.addElement(Util.CMONITORING)
        val cmd = root.addElement(Util.CMD)
        cmd.text = Util.CONTROL
        val info = root.addElement(Util.INFO)
        val monitor = info.addElement(Util.MONITOR)
        val dtver = monitor.addElement(Util.DTVER)
        dtver.text = Util.DTVER_VALUE
        val status = monitor.addElement(Util.STATUS)
        status.text = Util.STATUS_VALUE2
        val type = monitor.addElement(Util.TYPE)
        type.text = Util.TYPE_VALUE
        val id = monitor.addElement(Util.ID)
        id.text = macId
        val pass = monitor.addElement(Util.PASS)
        pass.text = passString
        val pv_addr = monitor.addElement(Util.PV_ADDR)
        pv_addr.text = Util.PV_ADDR_VALUE
        val data = root.addElement(Util.DATA)
        val control = data.addElement(Util.CONTROL)

        control.setAttributeValue("version", "1.0")
        control.setAttributeValue("boxid", boxId)

        val conf = control.addElement(Util.CONF)
        val set_time = conf.addElement(Util.SET_TIME)
        set_time.text = Util.SET_TIME_VALUE
        val center_addr = conf.addElement(Util.CENTER_ADDR)
        center_addr.text = Util.CENTER_ADDR_VALUE

        val devctrl = control.addElement(Util.DEVCTRL)
        val echo1 = devctrl.addElement(Util.ECHO)

        //TODO 参数设置
        echo1.setAttributeValue("id", "1")
        echo1.setAttributeValue("node", macId)
        echo1.text = "01350105ff017101f100"

        val echo2 = devctrl.addElement(Util.ECHO)
        echo1.setAttributeValue("id", "2")
        echo1.setAttributeValue("node", macId)
        echo1.text = "01350100D55E790000000000000000000000000000000000040501008000000000000000000000000000000000000000000000000000000000001495818180010101000101010101020202091501808000000100010100000000000004050100800000000000000000000000000D9581808001010100010100000000000000000000000000000000000000000000000000000000000000000000000000000000000045000000053130344650434837300000000000000000000000000000000000000000000001010402040C010203030C111111010120282808D2"



        return writeDoc.asXML()
    }
}
