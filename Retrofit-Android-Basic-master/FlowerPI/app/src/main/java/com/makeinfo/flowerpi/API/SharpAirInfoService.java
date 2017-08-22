package com.makeinfo.flowerpi.API;

import com.makeinfo.flowerpi.bean.AirInfo;
import com.makeinfo.flowerpi.bean.BodyPost;
import com.makeinfo.flowerpi.bean.WechatAirInfo;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by cuiduo on 2017/5/5.
 */

public interface SharpAirInfoService {

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("device/aircondition/get?access_token=YmlndWl5dWFuLXNoYXJwLWFpcnB1cmlmaWVy")
    Call<AirInfo> getAirInfo(
            @Body RequestBody jsonBean);

    @GET("wechatAQI?openId=18682011617&deviceId=F0FE6B146BDC&access_token=Erqxy6tq5z29H")
    Call<WechatAirInfo> getAir();
}
