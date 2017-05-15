package com.het.sdk.demo;

import android.app.Application;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.het.open.lib.api.HetSdk;
import com.het.open.lib.model.ConfigModel;
import com.het.open.lib.utils.GlobalAddr;
import com.het.sdk.demo.utils.Constants;

public class AppApplication extends Application {




    @Override
    public void onCreate() {
        super.onCreate();
        initHetSdk();
        initFresco();

    }


    /**
     * 初始化和而泰sdk
     */
    private void initHetSdk(){
       // ConfigMode
        ConfigModel configModel=new ConfigModel();
        configModel.setOpenWifiContorl(true);//是否开启wifi控制
        configModel.setOpenBleControl(false);//是否开启蓝牙控制
        configModel.setLog(true); //是否开启log信息
        configModel.setOpenPost(false);//是否为开放平台设备
        configModel.setHost(GlobalAddr.TYPE_PRODUCE_HOST); //环境设置
        HetSdk.getInstance().init(getApplicationContext(), Constants.APP_ID, Constants.APP_SECRET,configModel);

    }


    private void initFresco() {
        Fresco.initialize(getApplicationContext());
    }


}
