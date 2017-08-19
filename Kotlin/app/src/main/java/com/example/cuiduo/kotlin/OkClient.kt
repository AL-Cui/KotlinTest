package com.example.cuiduo.kotlin

import com.squareup.okhttp.OkHttpClient
/**
 * 单例OkHttpClient
 * Created by Flying SnowBean on 16-3-6.
 */
object OkClient{
    private val client = OkHttpClient()
    fun instance() = client
}
