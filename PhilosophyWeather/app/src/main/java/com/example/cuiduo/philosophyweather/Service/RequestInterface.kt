package com.example.cuiduo.philosophyweather.Service

import com.example.cuiduo.philosophyweather.AirInfo
import com.example.cuiduo.philosophyweather.AirQuiltyRequestInfo
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * Created by cuiduo on 2017/6/19.
 */
interface RequestInterface {

    @Headers("Content-type:application/json;charset=UTF-8")
    @POST("servlet/getWeather")
    abstract fun getAirInfo(
            @Body jsonBean: RequestBody): Call<AirInfo>
}