package com.het.sdk.demo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.het.open.lib.api.HetDeviceManagerApi;
import com.het.open.lib.api.HetProtocolApi;
import com.het.open.lib.callback.IHetCallback;
import com.het.open.lib.model.DeviceModel;
import com.het.open.lib.utils.StringUtils;
import com.het.sdk.demo.R;
import com.het.sdk.demo.ui.activity.device.DeviceControlActivity;
import com.het.sdk.demo.ui.activity.device.HumidifierDeviceControlActivity;
import com.het.sdk.demo.ui.activity.device.MattressActivity;
import com.het.sdk.demo.ui.activity.share.ShareMainActivity;
import com.het.sdk.demo.ui.activity.upgrade.UpgradeActivity;
import com.het.sdk.demo.utils.Constants;

/**
 * 设备管理界面
 */
public class DeviceManagerActivity extends BaseActivity implements View.OnClickListener {

    private final String TAG = "设备管理界面";
    private DeviceModel deviceModel;//设备model
    private Button mBtnDeviceControl;  //设备控制
    private Button mBtnDeviceShare;//设备分享
    private Button mBtnDeviceRomUpload;//固件升级
    private Button mBtnDeviceUnbind; //解绑设备
    private Button mBtnDeviceChangeMessage; //修改设备信息
    private Button mBtnGetHistroy; //获取历史数据
    private Button mBtnGetProtocol;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_manager);
        deviceModel = (DeviceModel) getIntent().getSerializableExtra(Constants.DEVICE_MODEL);
        initView();
    }


    private void initView() {
        getSupportActionBar().setTitle("设备管理");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mBtnDeviceControl = (Button) findViewById(R.id.btn_device_control);
        mBtnDeviceControl.setOnClickListener(this);
        mBtnDeviceShare = (Button) findViewById(R.id.btn_device_share);
        mBtnDeviceShare.setOnClickListener(this);
        mBtnDeviceRomUpload = (Button) findViewById(R.id.btn_device_rom_upload);
        mBtnDeviceRomUpload.setOnClickListener(this);
        mBtnDeviceUnbind = (Button) findViewById(R.id.btn_device_unbind);
        mBtnDeviceUnbind.setOnClickListener(this);
        mBtnDeviceChangeMessage = (Button) findViewById(R.id.btn_device_change_message);
        mBtnDeviceChangeMessage.setOnClickListener(this);
        mBtnGetHistroy = (Button) findViewById(R.id.btn_get_histroy);
        mBtnGetHistroy.setOnClickListener(this);
        mBtnGetProtocol = (Button) findViewById(R.id.btn_get_protocol);
        mBtnGetProtocol.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_device_control:
                int type = deviceModel.getModuleType();
                if (type == 2) {
                    Intent intent = new Intent(DeviceManagerActivity.this, MattressActivity.class);
                    intent.putExtra(Constants.DEVICE_MODEL, deviceModel);
                    startActivity(intent);
                } else {
                    if (Integer.parseInt(deviceModel.getDeviceTypeId()) == 5) {
                        Intent intent = new Intent(DeviceManagerActivity.this, HumidifierDeviceControlActivity.class);
                        intent.putExtra(Constants.DEVICE_MODEL, deviceModel);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(DeviceManagerActivity.this, DeviceControlActivity.class);
                        intent.putExtra(Constants.DEVICE_MODEL, deviceModel);
                        startActivity(intent);
                    }

                }
                break;
            case R.id.btn_device_share:
                Intent intent = new Intent(DeviceManagerActivity.this, ShareMainActivity.class);
                intent.putExtra(Constants.DEVICE_MODEL, deviceModel);
                startActivity(intent);
                break;
            case R.id.btn_device_rom_upload:
                // showToast("该功能暂未开放");
                Intent intentUp = new Intent(DeviceManagerActivity.this, UpgradeActivity.class);
                intentUp.putExtra(Constants.DEVICE_MODEL, deviceModel);
                startActivity(intentUp);
                break;
            case R.id.btn_device_unbind:
                unBindDevice(deviceModel);
                break;
            case R.id.btn_device_change_message:
                changeDeviceMessage();
                break;
            case R.id.btn_get_histroy:
                Intent intentData = new Intent(DeviceManagerActivity.this, DateHistroyActivity.class);
                intentData.putExtra(Constants.DEVICE_MODEL, deviceModel);
                startActivity(intentData);
                break;
            case R.id.btn_get_protocol:
                getProtocol();
                break;
        }
    }

    /**
     * 获取设备协议
     */
    private void getProtocol() {
        HetProtocolApi.getInstance().getProtocol(new IHetCallback() {
                    @Override
                    public void onSuccess(int code, String msg) {
                        if (code == 0) {
                            if (!StringUtils.isNull(msg)) {
                                //ProtocolManager.getInstance().loadFromJsonDeal(HetSdk.getInstance().getApplication(), msg);
                                showToast("获取协议成功");
                            }

                        }


                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        showToast("获取协议失败"+code+msg);
                    }
                }, deviceModel.getProductId(), 0);
    }

    /**
     * 修改设备信息
     */
    private void changeDeviceMessage() {
        String deviceName = "我的设备";
        HetDeviceManagerApi.getInstance().update(deviceModel, new IHetCallback() {
            @Override
            public void onSuccess(int code, String msg) {
                showToast(msg);
            }

            @Override
            public void onFailed(int code, String msg) {
                showToast(code + msg);
            }
        }, deviceName);
    }

    /**
     * 解绑设备
     *
     * @param deviceModel
     */
    private void unBindDevice(final DeviceModel deviceModel) {
        HetDeviceManagerApi.getInstance().unBind(deviceModel, new IHetCallback() {
            @Override
            public void onSuccess(int code, String msg) {
                if (code == 0) {
                    showToast("成功解绑该设备");
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                showToast(code + msg);
            }
        });

    }


}
