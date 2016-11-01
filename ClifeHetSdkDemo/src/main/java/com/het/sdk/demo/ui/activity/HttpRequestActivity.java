package com.het.sdk.demo.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.het.sdk.demo.R;

/**
 * 网络请求页面
 */
public class HttpRequestActivity extends BaseActivity {

    private Button btnRequest;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_request);
        initView();

    }

    private void initView(){
        getSupportActionBar().setTitle("HTTP请求");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnRequest = (Button) findViewById(R.id.btn_http);
        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DeviceBindApi.bind(new ICallback<String>() {
//                    @Override
//                    public void onSuccess(String s, int id) {
//
//                    }
//
//                    @Override
//                    public void onFailure(int code, String msg, int id) {
//
//                    }
//                },"8C8B83A1077E", 1202, null, -1);
            }
        });
    }






}


