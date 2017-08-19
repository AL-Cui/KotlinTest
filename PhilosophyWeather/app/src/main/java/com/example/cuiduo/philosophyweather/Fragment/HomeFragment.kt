package com.example.cuiduo.philosophyweather.Fragment

import android.content.Intent
import android.location.LocationListener
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.cuiduo.philosophyweather.AirQuilty
import com.example.cuiduo.philosophyweather.AirQuiltyRequestInfo
import com.example.cuiduo.philosophyweather.CitySelectActivity
import com.example.cuiduo.philosophyweather.Model.GetSharpAirInfo
import com.example.cuiduo.philosophyweather.R
import com.google.gson.Gson
import kotlinx.android.synthetic.main.homefragment.*
import okhttp3.*
import org.jetbrains.anko.async
import org.json.JSONObject
import java.io.File
import java.io.IOException

/**
 * Created by cuiduo on 2017/6/15.
 */
class HomeFragment : Fragment(){
    companion object {
        val WEATHER_URL = "http://www.smart-blink.com:8060/B-Link/V3/servlet/getWeather"
        val JSON = MediaType.parse("application/json; charset=utf-8")
    }

    var cityName: String = "深圳"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.homefragment, container, false)
        Log.d("FGGFHH", "hi")
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            load()
        }
    }

    private fun initView() {

        eqpmt_address.text = "深圳"
        homeRefresh.setOnRefreshListener {
            load()
        }
    }

    private fun load() {
        async {

            //            getWeather(cityName)
            for (i in 1..2) {
//                getWeather2()
                getOkHttp()
            }


        }
    }

    private fun getWeather(cityName: String) {

        val mOkHttpClient: OkHttpClient = OkHttpClient()

//        var params  = FormBody.Builder()
//                .add("palcename", "")
//                .build()

        var map = mutableMapOf<String, String>()
        map.put("", "")
        val json = "{" + "\"palcename\":\"深圳\"" + "}"

        var requestBody = RequestBody.create(JSON, json)
//        var requestBody2 = FormBody.Builder()
//                .add("palcename", cityName)
//                .build()
        val request = Request.Builder()
                .url(WEATHER_URL)
                .post(requestBody)
                .build()
        Log.d("hdh", "0")
        val call = mOkHttpClient.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                Toast.makeText(context, "ghfih", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call?, response: Response?) {
//
//                Log.d("GUGUGO",response!!.body().string())
                var jsObject: JSONObject = JSONObject(response?.body()?.string())
                Log.d("ACCER", jsObject.toString())
                val code = Integer.valueOf(jsObject.getString("code"))
                if (code == 2) {
                    val airQuilty = AirQuilty(jsObject.getString("pm25"), jsObject.getString("qlty"), jsObject.getString("sport"),
                            jsObject.getString("wind_dir"), jsObject.getString("drsg"),
                            jsObject.getString("comf"), jsObject.getString("tmp"))
                    eqpmt_tempurature.text = airQuilty.temporature
                    eqpmt_address.text = airQuilty.dress
                    eqpmt_wind.text = airQuilty.wind
                    tv_cmf_advice.text = airQuilty.comfort
                    tv_sport_advice.text = airQuilty.sport
                    squer.text = airQuilty.pm25
                    if (airQuilty.quilty.equals("优") || airQuilty.quilty.equals("良")) {
                        eqpmt_out_img.setImageResource(R.drawable.zhizhen2)
                    } else if (airQuilty.quilty.equals("轻度污染")) {
                        eqpmt_out_img.setImageResource(R.drawable.zhizhen3)
                    } else {
                        eqpmt_out_img.setImageResource(R.drawable.zhizhen1)

                    }
                }
            }

        })
    }

    private fun getWeather2() {
        Log.d(javaClass.simpleName, "这里")
        val bodyPost = AirQuiltyRequestInfo("")
        bodyPost.palcename = "深圳"
//        bodyPost.palcename = "深圳"
        val gson: Gson = Gson()
        var stringBody: String = gson.toJson(bodyPost)
        Log.d(javaClass.simpleName, stringBody)

        GetSharpAirInfo().getAirInfo(stringBody)
//        GetSharpAirInfo.getAirInfo(stringBody)

    }

    var directory: File = File("fhjh.tmp")
    val cache: Cache = Cache(directory, 10 * 1024 * 1024)
    private fun getOkHttp() {
        val mOkHttpClient = OkHttpClient.Builder()
                .cache(cache)
                .build()
        val requestBuilder = Request.Builder().url("http://shcloud-rd.sharp.cn/SharpCloudWeb/queen2/wechatAir?deviceId=F0FE6B146BDC&access_token=Erqxy6tq5z29H")
        requestBuilder.method("GET", null)
        val request = requestBuilder.build()
        val call = mOkHttpClient.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {

                if (null != response.cacheResponse()) {
                    val str = response.cacheResponse()!!.toString()
                    Log.e("结果cache", "cache---" + response.cacheResponse()!!)
                    Log.e("结果network", "network---" + response.networkResponse()!!)
                } else {
                    response.body()!!.string()
                    val str = response.networkResponse()!!.toString()
                    Log.d("结果", "network---" + str)
                }
//                runOnUiThread(Runnable { Toast.makeText(getApplicationContext(), "请求成功", Toast.LENGTH_SHORT).show() })
            }
        })
    }
}