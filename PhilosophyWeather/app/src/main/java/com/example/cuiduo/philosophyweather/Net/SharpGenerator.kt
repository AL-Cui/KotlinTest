package com.example.cuiduo.philosophyweather.Net

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

/**
 * Created by cuiduo on 2017/6/19.
 */
class SharpGenerator {
    companion object {
        val BASE_URL = "http://www.smart-blink.com:8060/B-Link/V3/"
        val builder:Retrofit.Builder = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
    }

    fun <S> createService(serviceClass: Class<S>): S {
        val client: OkHttpClient = OkHttpClient.Builder().build()
        val retrofit:Retrofit = builder.client(client).build()
//        val retrofit:Retrofit = builder.build()
        return retrofit.create(serviceClass)
    }


}