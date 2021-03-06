package com.example.cuiduo.wifitesting.MachineControl

import com.example.cuiduo.wifitesting.UtilClass.Util
import okhttp3.*
import org.dom4j.DocumentHelper
import java.io.IOException
import java.util.*

/**
 * Created by cuiduo on 2017/8/2.
 */

class Binding {

    fun boxIDCreateFirstRequest(macId: String): Array<String> {
        var resultList : Array<String> = Array(2, {""})
        var boxID = ""
        val MEDIA_XML_APPLICATIOON = MediaType.parse("application/xml;gzip;keep-alive")

        val request = Request.Builder()
                .url("https://shcloud-rd.sharp.cn/hems/upload/api/monitoring")
                .post(RequestBody.create(MEDIA_XML_APPLICATIOON, setXmlInfo1(macId)))
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
                    val boxId = parserXmlId(resultString)
                    resultList[0] = boxId
                    println("结果1==" + resultString)
                    resultList[1] = boxIdCreateSecondRequest(boxId, macId)
                    //                    Log.d("结果", "network---" + str);
                }

            }
        })
        return resultList
    }


    companion object {
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
    }

    fun boxIdCreateSecondRequest(boxId: String, macId: String): String {
        var passString :String =""
        val MEDIA_XML_APPLICATIOON = MediaType.parse("application/xml;charset=utf-8")
        val postString = setXmlInfo2(macId, boxId)
        println("第二次发送的参数==" + postString)
        val request = Request.Builder()
                .url("https://shcloud-rd.sharp.cn/hems/upload/api/monitoring")
                .post(RequestBody.create(MEDIA_XML_APPLICATIOON, postString))
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
                    //                    response.body().string();
                    val str = response.networkResponse().toString()
                    val resultString = response.body().string()
                    println("错误代码==" + str)
                    println("结果2==" + resultString)

                    val pass = parserXmlpass(resultString)
                    passString =pass
                    passCreateThirdRequest(pass, macId,boxId)

                    //                    Log.d("结果", "network---" + str);
                }
            }
        })
        return passString
    }

    private fun passCreateThirdRequest(pass: String, macId: String, boxId: String) {
        val MEDIA_XML_APPLICATIOON = MediaType.parse("application/xml;charset=utf-8")
        val postString = setXmlInfo3(macId, pass)
        println("第3次发送的参数==" + postString)
        val request = Request.Builder()
                .url("https://shcloud-rd.sharp.cn/hems/upload/api/monitoring")
                .post(RequestBody.create(MEDIA_XML_APPLICATIOON, postString))
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
                    //                    response.body().string();
                    val str = response.networkResponse().toString()
                    val resultString = response.body().string()
                    println("错误代码==" + str)
                    println("结果3==" + resultString)
                    MachineLogin.CreateRequest(macId,boxId,pass)
                }
            }
        })


    }

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

    /**
     * 根据返回的xml解析出pass
     */
    fun parserXmlpass(XMLString: String): String {

        var pass = ""

        try {
            val readDoc = DocumentHelper.parseText(XMLString)
            val rootNode = readDoc.rootElement//MONITORING

            if (Util.MONITORING.equals(rootNode.name)) {
                val data = rootNode.element(Util.DATA)
                val put = data.element(Util.PUT)

                pass = put.elementTextTrim(Util.PASS)

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return pass
    }

    fun setXmlInfo1(idString: String): String {
        val writeDoc = DocumentHelper.createDocument()
        val root = writeDoc.addElement(Util.MONITORING)
        val cmd = root.addElement(Util.CMD)
        cmd.text = Util.AUTH
        val info = root.addElement(Util.INFO)
        val monitor = info.addElement(Util.MONITOR)
        val dtver = monitor.addElement(Util.DTVER)
        dtver.text = Util.DTVER_VALUE
        val status = monitor.addElement(Util.STATUS)
        status.text = Util.STATUS_VALUE
        val type = monitor.addElement(Util.TYPE)
        type.text = Util.TYPE_VALUE
        val id = monitor.addElement(Util.ID)
        id.text = idString
        val pass = monitor.addElement(Util.PASS)
        pass.text = "        "
        val pv_addr = monitor.addElement(Util.PV_ADDR)
        pv_addr.text = Util.PV_ADDR_VALUE
        val data = root.addElement(Util.DATA)
        val memboption = data.addElement(Util.MEMBOPTION)

        memboption.setAttributeValue("force_create_id", "true")
        memboption.setAttributeValue("app_secret", "orCPmn4UHx5bptZm1mmEUQ3XC1q%2BI%2B3gRdCVX4YfTEY%3D")
        return writeDoc.asXML()
    }


    fun setXmlInfo2(idString: String, boxId: String): String {
        val writeDoc = DocumentHelper.createDocument()
        val root = writeDoc.addElement(Util.MONITORING)
        val cmd = root.addElement(Util.CMD)
        cmd.text = Util.MEMB
        val info = root.addElement(Util.INFO)
        val monitor = info.addElement(Util.MONITOR)
        val dtver = monitor.addElement(Util.DTVER)
        dtver.text = Util.DTVER_VALUE
        val status = monitor.addElement(Util.STATUS)
        status.text = Util.STATUS_VALUE
        val type = monitor.addElement(Util.TYPE)
        type.text = Util.TYPE_VALUE
        val id = monitor.addElement(Util.ID)
        id.text = idString
        val pass = monitor.addElement(Util.PASS)
        pass.text = "        "
        val pv_addr = monitor.addElement(Util.PV_ADDR)
        pv_addr.text = Util.PV_ADDR_VALUE
        val data = root.addElement(Util.DATA)
        val member = data.addElement(Util.MEMBER)
        member.text = Util.ONE
        val extension = data.addElement(Util.EXTENSION)
        val box_id = extension.addElement(Util.BOX_ID)

        box_id.text = boxId.toString()
        return writeDoc.asXML()
    }


    /**
     * 根据第二次返回的pass，第三次发送：带pass的xml
     */
    fun setXmlInfo3(idString: String, passString: String): String {
        val writeDoc = DocumentHelper.createDocument()
        val root = writeDoc.addElement(Util.MONITORING)
        val cmd = root.addElement(Util.CMD)
        cmd.text = Util.PUT
        val info = root.addElement(Util.INFO)
        val monitor = info.addElement(Util.MONITOR)
        val dtver = monitor.addElement(Util.DTVER)
        dtver.text = Util.DTVER_VALUE
        val status = monitor.addElement(Util.STATUS)
        status.text = Util.STATUS_VALUE
        val type = monitor.addElement(Util.TYPE)
        type.text = Util.TYPE_VALUE
        val id = monitor.addElement(Util.ID)
        id.text = idString
        val pass = monitor.addElement(Util.PASS)
        pass.text = passString
        val pv_addr = monitor.addElement(Util.PV_ADDR)
        pv_addr.text = Util.PV_ADDR_VALUE
        val data = root.addElement(Util.DATA)
        val put = data.addElement(Util.PUT)
        val set_time = put.addElement(Util.SET_TIME)
        set_time.text = Util.SET_TIME_VALUE
        val next_common = put.addElement(Util.NEXT_COMM)
        next_common.text = Util.NEXT_COMM_VALUE
        val center_addr = put.addElement(Util.CENTER_ADDR)
        center_addr.text = Util.CENTER_ADDR_VALUE

        return writeDoc.asXML()
    }

}
