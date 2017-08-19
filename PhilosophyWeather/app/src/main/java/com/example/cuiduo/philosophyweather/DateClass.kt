package com.example.cuiduo.philosophyweather

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by cuiduo on 2017/6/16.
 */
data class AirQuilty(var pm25: String,
                     var quilty: String,
                     var sport: String,
                     var wind: String,
                     var dress: String,
                     var comfort: String,
                     var temporature: String)

data class AirQuiltyRequestInfo(
        var palcename: String
)

data class AirInfo(
        @SerializedName("code")
        @Expose
        var code: String,

        @SerializedName("info")
        @Expose
        var info: String,

        @SerializedName("pm25")
        @Expose
        var pm25: String,

        @SerializedName("qlty")
        @Expose
        var qlty:String,

        @SerializedName("drsg")
        @Expose
        var drsg:String,

        @SerializedName("comf")
        @Expose
        var comf:String,

        @SerializedName("sport")
        @Expose
        var sport:String,

        @SerializedName("tmp")
        @Expose
        var tmp:String,

        @SerializedName("wind_dir")
        @Expose
        var wind_dir:String
)

data class SampleInfo( var title:String,var fragmentName:String)
