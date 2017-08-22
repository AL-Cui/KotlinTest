package com.makeinfo.flowerpi.model;

import com.makeinfo.flowerpi.API.GitHubService;
import com.makeinfo.flowerpi.API.SharpAirInfoService;
import com.makeinfo.flowerpi.bean.AirInfo;
import com.makeinfo.flowerpi.bean.WechatAirInfo;
import com.makeinfo.flowerpi.net.ServiceGenerator;
import com.makeinfo.flowerpi.net.SharpGenerator;
import com.makeinfo.flowerpi.vm.MainViewModel;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cuiduo on 2017/5/12.
 */

public class GetAirInfo {
    private SharpAirInfoService git;
    private MainViewModel viewModel;

    public GetAirInfo(MainViewModel viewModel) {
        this.viewModel = viewModel;
        this.git =  SharpGenerator.createService(SharpAirInfoService.class);

    }

    public void getAir() {
        //binding.username.getText().toString()
        Call call = git.getAir();
        call.enqueue(new Callback<WechatAirInfo>() {
            @Override
            public void onResponse(Response<WechatAirInfo> response) {
                WechatAirInfo air = response.body();

                if (air == null) {
                    //404 or the response cannot be converted to User.
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        try {
                            viewModel.setText("responseBody = " + responseBody.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        viewModel.setText("responseBody  = null");
                    }
                } else {
                    //200
                    viewModel.setText("风速模式=:" + air.getModels() + "\nPM= :" + air.getPm()+"\n湿度= :" + air.getHumidity()
                    + "\n室外空气质量= :"+air.getOutside() +"\n室尘= ："+air.getDust() + "\n异味= ："+air.getSmell());
                }
                viewModel.setPb(false);
            }

            @Override
            public void onFailure(Throwable t) {
                viewModel.setText("t = " + t.getMessage());
                viewModel.setPb(false);
            }
        });
    }


}
