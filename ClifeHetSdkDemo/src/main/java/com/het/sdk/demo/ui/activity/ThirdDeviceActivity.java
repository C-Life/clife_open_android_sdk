package com.het.sdk.demo.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.gson.JsonObject;
import com.het.open.lib.api.HetThridBleApi;
import com.het.open.lib.callback.IHetCallback;
import com.het.open.lib.model.DeviceModel;
import com.het.open.lib.model.ThirdBLeDataModel;
import com.het.open.lib.utils.GsonUtil;
import com.het.open.lib.utils.LogUtils;
import com.het.sdk.demo.R;


/**
 * 第三方蓝牙设备
 */
public class ThirdDeviceActivity extends BaseActivity implements View.OnClickListener {

    private final String TAG="第三方蓝牙设备";
    private Button btnbind;  //设备绑定
    private Button btnUpdate;  //设备数据上传
    private Button btnDownload; //设备数据下载
    private DeviceModel deviceModel;//设备model


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_device);
        initView();
        initData();


    }


    private void initData() {
        //初始化第三方蓝牙设备信息，设备信息可以通过绑定设备大类，小类获取
//        deviceModel = new DeviceModel();
//        deviceModel.setDeviceName("智能胸罩");
//        deviceModel.setProductIcon("http://200.200.200.58:8981/group2/M01/04/5F/yMjIOlcpZRqAD9B6AAJdvLoqFvg1312466");
//        deviceModel.setProductId(1251);
//        deviceModel.setModuleType(2);
//        deviceModel.setDeviceTypeId(57 + "");
//        deviceModel.setDeviceSubtypeId(1 + "");
//        deviceModel.setMacAddress("ACCF2378C432");
        //智能体温计
//        deviceModel = new DeviceModel();
//        deviceModel.setDeviceName("智能体温计");
//        deviceModel.setProductIcon("http://200.200.200.58:8981/group1/M00/04/67/yMjIOlcxp62ASa5YAAAKNKKFRPc8688804");
//        deviceModel.setProductId(1320);
//        deviceModel.setModuleType(2);
//        deviceModel.setMacAddress("ACCF2378C431");
//超声波身高测量仪
        deviceModel = new DeviceModel();
        deviceModel.setDeviceName("超声波身高测量仪");
        deviceModel.setProductId(1380);
        deviceModel.setModuleType(2);
        deviceModel.setMacAddress("FFFFFFFFFFFF");


    }

    private void initView() {
        getSupportActionBar().setTitle("第三方设备接入");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnbind = (Button) findViewById(R.id.btn_bind_server);
        btnbind.setOnClickListener(this);
        btnUpdate = (Button) findViewById(R.id.btn_update_data);
        btnUpdate.setOnClickListener(this);
        btnDownload = (Button) findViewById(R.id.btn_download_data);
        btnDownload.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_bind_server:
                submit();
                break;
            case R.id.btn_update_data:
                //updataData();
                updataJsonData();
                break;
            case R.id.btn_download_data:
                getData();
                break;
        }
    }

    /**
     * 上传byt数组数据
     */
    private void updataData() {
        byte[] data=new byte[1000];
        HetThridBleApi.getInstance().updateData(new IHetCallback() {
            @Override
            public void onSuccess(int code, String msg) {
                if (code==0){
                    showToast("上传数据成功");
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                showToast(code + msg);
            }
        },data,deviceModel,1);
    }

    /**
     * 上传json数据
     */
    private void updataJsonData() {
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("data","test");
        HetThridBleApi.getInstance().updateData(new IHetCallback() {
            @Override
            public void onSuccess(int code, String msg) {
                if (code==0){
                    showToast("上传数据成功");
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                showToast(code + msg);
            }
        },jsonObject.toString(),deviceModel,1);
    }

    /**
     * 将设备信息提交到clife平台
     */
    private void submit() {
        HetThridBleApi.getInstance().bindToHetServer(deviceModel, new IHetCallback() {
            @Override
            public void onSuccess(int code, String msg) {
                if (code == 0) {
                    deviceModel = GsonUtil.getGsonInstance().fromJson(msg, DeviceModel.class);
                    showToast("成功绑定设备");
                }else {
                    showToast(msg);

                }
            }

            @Override
            public void onFailed(int code, String msg) {
                showToast(code + msg);
            }
        });
    }

    /**
     * 将设备信息提交到clife平台
     */
    private void getData() {
        HetThridBleApi.getInstance().getData(new IHetCallback() {
            @Override
            public void onSuccess(int code, String data) {
                if (code == 0) {
                    ThirdBLeDataModel thirdBLeDataModel=GsonUtil.getGsonInstance().fromJson(data, ThirdBLeDataModel.class);
                    LogUtils.d(TAG,data);
                }else {
                    LogUtils.e(TAG,code+data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                showToast(code + msg);
            }
        },deviceModel,0,20,1);
    }
}
