package com.makeinfo.flowerpi.model;

import com.makeinfo.flowerpi.API.SharpAirInfoService;
import com.makeinfo.flowerpi.bean.AirInfo;
import com.makeinfo.flowerpi.bean.BodyPost;
import com.makeinfo.flowerpi.bean.User;
import com.makeinfo.flowerpi.net.SharpServiceGenerator;
import com.makeinfo.flowerpi.vm.MainViewModel;

import java.io.IOException;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cuiduo on 2017/5/6.
 */

public class GetSharpAirInfo {

    private SharpAirInfoService git;
    private MainViewModel viewModel;


    public GetSharpAirInfo(MainViewModel viewModel) {
        this.viewModel = viewModel;
        this.git =  SharpServiceGenerator.createService(SharpAirInfoService.class);

    }
    public void getAirInfo (String bodyString){
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),bodyString);
        Call call = git.getAirInfo(body);
        call.enqueue(new Callback<AirInfo>() {
            @Override
            public void onResponse(Response<AirInfo> response) {
                AirInfo model = response.body();

                if (model == null) {
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
                    viewModel.setText("Errcode :" + model.getErrCode() + "\nErrmsg :" + model.getErrMsg());
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
