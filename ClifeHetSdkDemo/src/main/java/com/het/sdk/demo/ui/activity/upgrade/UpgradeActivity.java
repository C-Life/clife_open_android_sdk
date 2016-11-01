package com.het.sdk.demo.ui.activity.upgrade;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.het.open.lib.api.HetDeviceRomUpgradeApi;
import com.het.open.lib.callback.IHetCallback;
import com.het.open.lib.model.DeviceModel;
import com.het.open.lib.model.romUdgrade.DeviceVersionUpgradeModel;
import com.het.open.lib.utils.GsonUtil;
import com.het.open.lib.utils.StringUtils;
import com.het.sdk.demo.R;
import com.het.sdk.demo.ui.activity.BaseActivity;
import com.het.sdk.demo.utils.Constants;


/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2016, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * <p>描述：固件升级主界面</p>
 * 名称:  授权api<br>
 * 作者: 80010814<br>
 * 版本: 1.0<br>
 * 日期: 2016/3/10. 16:42<br>
 **/
public class UpgradeActivity extends BaseActivity implements View.OnClickListener {

    private final String TAG="第三方蓝牙设备";
    private Button btnUpgradeCheck;  //设备控制
    private Button btnUpgradeAgree;  //设备分享
    private Button btnUpgradeProgess;  //固件升级
    private Button btnUpgradeFinish;  //解绑设备
    private DeviceModel deviceModel;//设备model
    private  DeviceVersionUpgradeModel deviceVersionUpgradeModel;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ugrade);
        deviceModel=(DeviceModel)getIntent().getSerializableExtra(Constants.DEVICE_MODEL) ;
        initView();



    }




    private void initView() {
        getSupportActionBar().setTitle("固件更新");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnUpgradeCheck = (Button) findViewById(R.id.btn_upgrade_check);
        btnUpgradeCheck .setOnClickListener(this);
        btnUpgradeAgree = (Button) findViewById(R.id.btn_upgrade_agree);
        btnUpgradeAgree.setOnClickListener(this);
        btnUpgradeProgess= (Button) findViewById(R.id.btn_upgrade_progess);
        btnUpgradeProgess.setOnClickListener(this);
        btnUpgradeFinish=(Button) findViewById(R.id.btn_upgrade_finish);
        btnUpgradeFinish.setOnClickListener(this);
        btnUpgradeAgree.setEnabled(false);
        btnUpgradeProgess.setEnabled(false);
        btnUpgradeFinish.setEnabled(false);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_upgrade_check:
                check();
                break;
            case R.id.btn_upgrade_agree:
                agree();
                break;
            case R.id.btn_device_rom_upload:
                 getProgess();
                break;
            case R.id.btn_upgrade_finish:
                updateFinish();
                break;
        }
    }

    /**
     * 更新是否完成
     * deviceId	是	string	设备标识
     * deviceVersionId	是	string	设备版本标示
     */
    private void updateFinish() {
        String deviceId=deviceModel.getDeviceId();
        String versionId=deviceVersionUpgradeModel.getDeviceVersionId();
        HetDeviceRomUpgradeApi.confirmSucUpgrade(new IHetCallback() {
            @Override
            public void onSuccess(int code, String msg) {

            }

            @Override
            public void onFailed(int code, String msg) {

            }
        },deviceId,versionId);
    }

    /**
     *获取更新进度
     * deviceId	是	string	设备标识
     * deviceVersionId	是	string	设备版本标示
     */
    private void getProgess() {
        String deviceId=deviceModel.getDeviceId();
        String versionId=deviceVersionUpgradeModel.getDeviceVersionId();
        HetDeviceRomUpgradeApi.getUpgradeProgress(new IHetCallback() {
            @Override
            public void onSuccess(int code, String msg) {

            }

            @Override
            public void onFailed(int code, String msg) {

            }
        },deviceId,versionId);

    }


    /**
     * 检查固件更新
     */
    private void check() {
        if (deviceModel.getModuleId()==2){
            showToast("蓝牙模块升级暂未开放");
            return;
        }
        String deviceId=deviceModel.getDeviceId();

        HetDeviceRomUpgradeApi.check(new IHetCallback() {
            @Override
            public void onSuccess(int code, String msg) {
                deviceVersionUpgradeModel= GsonUtil.getGsonInstance().fromJson(msg,DeviceVersionUpgradeModel.class);
                if (deviceVersionUpgradeModel!=null){
                    String oldVersion=deviceVersionUpgradeModel.getOldDeviceVersion();
                    String newVersion=deviceVersionUpgradeModel.getNewDeviceVersion();
                    if (!StringUtils.isNull(newVersion)){
                        showToast("固件有新版本"+newVersion);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                btnUpgradeAgree.setEnabled(true);
                                btnUpgradeProgess.setEnabled(true);
                                btnUpgradeFinish.setEnabled(true);
                            }
                        });

                    }else {
                        showToast("固件已是最新版本"+oldVersion);
                    }
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                 showToast(code+msg);
            }
        },deviceId);
    }

    /**
     * 同意版本更新
      * deviceId	是	string	设备标识
     * deviceVersionId	是	string	设备版本标示
     */
    private void agree() {
        String deviceId=deviceModel.getDeviceId();
        String versionId=deviceVersionUpgradeModel.getDeviceVersionId();
        //设备版本类型（1-PCB，2-WiFi）
        int type=1;
        HetDeviceRomUpgradeApi.confirm(new IHetCallback() {
            @Override
            public void onSuccess(int code, String msg) {
                showToast("同意版本更新");
            }

            @Override
            public void onFailed(int code, String msg) {
                showToast(code+msg);

            }
        },deviceId,type+"",versionId);
    }


}
