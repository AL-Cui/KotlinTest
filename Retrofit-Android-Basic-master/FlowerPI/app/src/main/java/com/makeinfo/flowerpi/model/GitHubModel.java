package com.makeinfo.flowerpi.model;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.makeinfo.flowerpi.API.GitHubService;
import com.makeinfo.flowerpi.bean.User;
//import com.makeinfo.flowerpi.net.ServiceGenerator;
import com.makeinfo.flowerpi.net.Servicegenerator2;
import com.makeinfo.flowerpi.vm.MainViewModel;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observer;
import rx.Scheduler;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Andy on 1/25/2016.
 */
public class GitHubModel {
    public static final String TAG = GitHubModel.class.getSimpleName();
    private GitHubService git;
    private MainViewModel viewModel;
    private Subscription subscription;

    public GitHubModel(MainViewModel viewModel) {
        this.viewModel = viewModel;
//
        this.git = Servicegenerator2.createService2(GitHubService.class);

    }

    Observer<User> observer = new Observer<User>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG,e.toString());
        }

        @Override
        public void onNext(User user) {

            viewModel.setText("Github Name :" + user.getName() +
                    "\nWebsite :" + user.getBlog() + "\nCompany Name :" + user.getCompany());
            viewModel.setPb(false);
        }
    };

    public void getUser(String username) {
        //binding.username.getText().toString()
        subscription = git
                .getUser2(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
//        Call call = git.getUser(username);
//        call.enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Response<User> response) {
//                User model = response.body();
//
//                if (model == null) {
//                    //404 or the response cannot be converted to User.
//                    ResponseBody responseBody = response.errorBody();
//                    if (responseBody != null) {
//                        try {
//                            viewModel.setText("responseBody = " + responseBody.string());
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    } else {
//                        viewModel.setText("responseBody  = null");
//                    }
//                } else {
//                    //200
//                    viewModel.setText("Github Name :" + model.getName() + "\nWebsite :" + model.getBlog() + "\nCompany Name :" + model.getCompany());
//                }
//                viewModel.setPb(false);
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//                viewModel.setText("t = " + t.getMessage());
//                viewModel.setPb(false);
//            }
//        });
    }

}
