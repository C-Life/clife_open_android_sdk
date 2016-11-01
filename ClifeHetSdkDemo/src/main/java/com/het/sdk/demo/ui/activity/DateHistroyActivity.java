package com.het.sdk.demo.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.het.open.lib.api.HetDeviceDataHistroyApi;
import com.het.open.lib.callback.IHetCallback;
import com.het.open.lib.model.DeviceModel;
import com.het.open.lib.utils.TimeUtils;
import com.het.sdk.demo.R;
import com.het.sdk.demo.utils.Constants;

/**
 * 设备管理界面
 */
public class DateHistroyActivity extends BaseActivity implements View.OnClickListener {

    private final String TAG = "设备管理界面";
    private DeviceModel deviceModel;//设备model
    private Button mDataHistroyRun;
    private Button mDataHistroyConfig;
    private Button mDataHistroyError;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_data_histroy);
        deviceModel = (DeviceModel) getIntent().getSerializableExtra(Constants.DEVICE_MODEL);
        initView();


    }


    private void initView() {
        getSupportActionBar().setTitle("设备管理");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDataHistroyRun = (Button) findViewById(R.id.data_histroy_run);
        mDataHistroyRun.setOnClickListener(this);
        mDataHistroyConfig = (Button) findViewById(R.id.data_histroy_config);
        mDataHistroyConfig.setOnClickListener(this);
        mDataHistroyError = (Button) findViewById(R.id.data_histroy_error);
        mDataHistroyError.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.data_histroy_run:
                getRunData();
                break;
            case R.id.data_histroy_config:
                getConfigData();
                break;
            case R.id.data_histroy_error:
                getErrorData();
                break;
        }
    }

    /**
     * 获取运行数据
     */
    private void getRunData(){
        String deviceId=deviceModel.getDeviceId();
        String startTime= TimeUtils.getCurUtcDateString();
        String endTime="";
        int pageRows=20;
        int pageIndex=1;
        HetDeviceDataHistroyApi.getInstance().getRunDataList(new IHetCallback() {
            @Override
            public void onSuccess(int code, String msg) {
                showToast(msg);
            }

            @Override
            public void onFailed(int code, String msg) {
                showToast(code+msg);

            }
        },deviceId,startTime,endTime,pageRows,pageIndex);

    }

    private void getConfigData(){
        String deviceId=deviceModel.getDeviceId();
        String startTime= TimeUtils.getCurUtcDateString();
        String endTime="";
        int pageRows=20;
        int pageIndex=1;
        HetDeviceDataHistroyApi.getInstance().getConfigDataList(new IHetCallback() {
            @Override
            public void onSuccess(int code, String msg) {
                showToast(msg);
            }

            @Override
            public void onFailed(int code, String msg) {
                showToast(code+msg);

            }
        },deviceId,startTime,endTime,pageRows,pageIndex);
    }

    private void getErrorData(){
        String deviceId=deviceModel.getDeviceId();
        String startTime= TimeUtils.getCurUtcDateString();
        String endTime="";
        int pageRows=20;
        int pageIndex=1;
        HetDeviceDataHistroyApi.getInstance().getErrorDataList(new IHetCallback() {
            @Override
            public void onSuccess(int code, String msg) {
                showToast(msg);
            }

            @Override
            public void onFailed(int code, String msg) {
                showToast(code+msg);

            }
        },deviceId,startTime,endTime,pageRows,pageIndex);
    }




}
