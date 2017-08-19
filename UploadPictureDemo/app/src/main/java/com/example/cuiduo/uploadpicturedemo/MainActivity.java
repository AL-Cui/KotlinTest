package com.example.cuiduo.uploadpicturedemo;

import android.icu.text.NumberFormat;
import android.os.Environment;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.tencent.rtmp.ugc.cos.TVCClient;
import com.tencent.rtmp.ugc.cos.TVCUploadInfo;
import com.tencent.rtmp.ugc.cos.TVCUploadListener;


import util.Config;
import util.SignatureUtil;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    String secretId;
    String signature;
    String videoType;
    String videoPath;
    String coverType;
    String coverPath;
    String concatText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String ss = String.valueOf(System.currentTimeMillis());
//                try {
////                   String concatText =  SignatureUtil.createSignature
////                           (Config.SECRET_ID,ss,SignatureUtil.Random(),Config.SECRET_KEY);
//                    String concatText =  SignatureUtil.createSignature
//                            (Config.SECRET_ID,"1492651557","3614948195",Config.SECRET_KEY);
//                    Log.d(TAG,"最终结果="+concatText);
//                    Log.d(TAG,"最终结果的长度="+concatText.length());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                Log.d(TAG,"djkd"+Environment.getExternalStorageDirectory());
                Environment.getExternalStorageDirectory();
                secretId = Config.SECRET_ID;
                videoType="mp4";
                Log.d(TAG,"SD卡状态="+Environment.getExternalStorageState());
                videoPath=Environment.getExternalStorageDirectory()+"/VID_20170517_104200.mp4";
                Log.d(TAG,"路径="+videoPath);
                Log.d(TAG,"视频类型="+videoType);
                Log.d(TAG,"secretId="+secretId);
                signature = "oTvumupScUjbgoFm+CckBr2YCuFzZWNyZXRJZD1BS0lEbFBzQUdBa1RxMUlkdTJrbU5tQk9QRHBGU2F2RGxVVFAmY3VycmVudFRpbWVTdGFtcD0xNDk1MTU4NzA1JmV4cGlyZVRpbWU9MTQ5NTk1ODcwNSZyYW5kb209MzYxNDk0ODE5NQ==";
                TVCClient client = new TVCClient(getApplicationContext(),secretId,signature);
                TVCUploadInfo info = new TVCUploadInfo(videoType,videoPath,"","");
                client.uploadVideo(info, new TVCUploadListener() {
                    @Override
                    public void onSucess(String fileId, String playUrl, String videoUrl) {
                        Log.d(TAG, "上传成功的视频文件ID: " + fileId);
                        Log.d(TAG, "上传视频的播放地址：" + playUrl);
                        Log.d(TAG, "上传封面的展示地址：" + videoUrl);
                    }

                    @Override
                    public void onFailed(int errCode, String errMsg) {

                        Toast.makeText(MainActivity.this,
                                "错误码：" + errCode + " 错误信息：" + errMsg,
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onProgress(long currentSize, long totalSize) {

                        double percent = (double) currentSize / (double) totalSize;
                        NumberFormat nt = NumberFormat.getPercentInstance();
                        nt.setMinimumFractionDigits(2);
                        Log.d("上传进度", "上传进度：" + nt.format(percent));
                    }
                });
            }
        });

    }
}
