package com.example.cuiduo.philosophyweather.Model

import android.util.Log
import com.example.cuiduo.philosophyweather.AirInfo
import com.example.cuiduo.philosophyweather.AirQuiltyRequestInfo
import com.example.cuiduo.philosophyweather.Net.SharpGenerator
import com.example.cuiduo.philosophyweather.Service.RequestInterface
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by cuiduo on 2017/6/19.
 */
class GetSharpAirInfo {
    fun getAirInfo(bodyString: String) {
        Log.d(javaClass.simpleName,"这里了2")
        var body = RequestBody.create(
                okhttp3.MediaType.parse("application/json;charset=UTF-8"), bodyString)
        Log.d(javaClass.simpleName,"这里了3")
        val call:Call<AirInfo> = SharpGenerator().createService(RequestInterface::class.java).getAirInfo(body)
        Log.d(javaClass.simpleName,"这里了4")

        call.enqueue(object :Callback<AirInfo>{
            override fun onFailure(call: Call<AirInfo>?, t: Throwable?) {
                Log.d(javaClass.simpleName,"这里了5")
            }

            override fun onResponse(call: Call<AirInfo>?, response: Response<AirInfo>?) {
                Log.d(javaClass.simpleName,"这里了5")
                if (response !=null){
                    val model:AirInfo = response.body()!!
                    Log.d(javaClass.simpleName,model.code)
                }

            }
        })


    }
}
