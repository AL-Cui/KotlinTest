package com.makeinfo.flowerpi;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.jakewharton.rxbinding.view.RxView;
import com.makeinfo.flowerpi.bean.BodyPost;
import com.makeinfo.flowerpi.databinding.ActivityMainBinding;
import com.makeinfo.flowerpi.model.GetAirInfo;
import com.makeinfo.flowerpi.model.GetSharpAirInfo;
import com.makeinfo.flowerpi.model.GitHubModel;
import com.makeinfo.flowerpi.vm.MainViewModel;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainViewModel viewModel;
    //    private GetSharpAirInfo model;
    private GitHubModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setModel(viewModel = new MainViewModel());
//        model = new GetSharpAirInfo(viewModel);
        model = new GitHubModel(viewModel);
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.setPb(true);
//                model.getAir();
//                model.getUser(binding.username.getText().toString());
                model.getUser(binding.username.getText().toString());
//                BodyPost bodyPost = new BodyPost();
//                bodyPost.setControlKey("1_service_get_air_info");
//                bodyPost.setDeviceId("F0FE6B146BDC");
//                bodyPost.setDeviceKind("FPCH70");
//                bodyPost.setSessionId("0");
//                bodyPost.setUserOpenId("18682011617");
//                Gson gson = new Gson();
////                HashMap<String,String> paramsMap = new HashMap<String, String>();
////                paramsMap.put("controlKey","1_service_get_air_info");
////                paramsMap.put("deviceId","F0FE6B146BDC");
////                paramsMap.put("userOpenId","18682011617");
////                paramsMap.put("deviceKind","FPCH70");
////                paramsMap.put("sessionId","0");
//                String bodyString = gson.toJson(bodyPost);
//                model.getAirInfo(bodyString);
                //   model.getAirInfo(new BodyPost("1_service_get_air_info","F0FE6B146BDC","18682011617","FPCH70","0"));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
