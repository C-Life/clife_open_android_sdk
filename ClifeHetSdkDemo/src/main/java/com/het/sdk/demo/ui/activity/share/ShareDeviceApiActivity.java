package com.het.sdk.demo.ui.activity.share;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.reflect.TypeToken;
import com.het.open.lib.api.HetDeviceShareApi;
import com.het.open.lib.api.HetMessageApi;
import com.het.open.lib.callback.IHetCallback;
import com.het.open.lib.model.DeviceModel;
import com.het.open.lib.model.share.AuthDeviceModel;
import com.het.open.lib.model.share.DeviceAuthUserModel;
import com.het.open.lib.utils.GsonUtil;
import com.het.open.lib.utils.StringUtils;
import com.het.sdk.demo.R;
import com.het.sdk.demo.ui.activity.BaseActivity;
import com.het.sdk.demo.utils.Constants;

import java.lang.reflect.Type;
import java.util.List;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2016, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * <p>描述：分享api接口界面</p>
 * 名称:  授权api<br>
 * 作者: 80010814<br>
 * 版本: 1.0<br>
 * 日期: 2016/3/10. 16:42<br>
 **/
public class ShareDeviceApiActivity extends BaseActivity implements View.OnClickListener{

    private Button btnInvert;
    private Button btnAgree;
    private Button btnDel;
    private Button btnGetDeviceAuthUser;
    private Button btnGetDeviceNotAuthUser;
    private Button btnGetAuthDevice;
    private Button btnMessage;
    private Context mContext;
    private DeviceModel deviceModel;//设备model
    private String deviceId;  //设备id
    private EditText edtPhone;
    private EditText edtDelPhone;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_device);
        this.mContext=this;
        initView();
        initData();

    }

    /**
     * 获取要分享设备的devicemodel
     */
    private void initData() {
        deviceModel = (com.het.open.lib.model.DeviceModel) getIntent().getSerializableExtra(Constants.DEVICE_MODEL);
        deviceId=deviceModel.getDeviceId();
    }


    private void initView() {
        getSupportActionBar().setTitle("设备分享");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnInvert = (Button) findViewById(R.id.btn_device_auth_invite);
        btnInvert.setOnClickListener(this);
        btnAgree= (Button) findViewById(R.id.btn_device_auth_agree);
        btnAgree.setOnClickListener(this);
        btnDel= (Button) findViewById(R.id.btn_device_auth_del);
        btnDel.setOnClickListener(this);
        btnMessage=(Button) findViewById(R.id.btn_device_auth_message);
        btnMessage.setOnClickListener(this);
        btnGetDeviceAuthUser= (Button) findViewById(R.id.btn_device_auth_getDeviceAuthUser);
        btnGetDeviceAuthUser.setOnClickListener(this);
        btnGetDeviceNotAuthUser= (Button) findViewById(R.id.btn_device_auth_getDeviceNotAuthUser);
        btnGetDeviceNotAuthUser.setOnClickListener(this);
        btnGetAuthDevice= (Button) findViewById(R.id.btn_device_auth_getAuthDevice);
        btnGetAuthDevice.setOnClickListener(this);
        edtPhone=(EditText)findViewById(R.id.et_input_phone);
        edtDelPhone=(EditText)findViewById(R.id.et_del_phone);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_device_auth_invite:
                invite();
                break;
            case R.id.btn_device_auth_agree:
                agree();
                break;
            case R.id.btn_device_auth_del:
                del();
                break;
            case R.id.btn_device_auth_getDeviceAuthUser:
                getDeviceAuthUser();
                break;
            case R.id.btn_device_auth_getDeviceNotAuthUser:
                getDeviceNotAuthUser();
                break;
            case R.id.btn_device_auth_getAuthDevice:
                getAuthDevice();
                break;
            case R.id.btn_device_auth_message:
                getMessage();
                break;


        }
    }

    /**
     * 获取分享授权消息
     */
    private void getMessage() {
        HetMessageApi.getInstance().getListByPage(new IHetCallback() {
            @Override
            public void onSuccess(int code, String msg) {
                if (!StringUtils.isNull(msg)){
                  // MessgeModel model = GsonUtil.getGsonInstance().fromJson(msg, MessgeModel.class);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                showToast(code+msg);
            }
        });

    }

    /**
     *获取未授权给好友的设备列表
     */
    private void getAuthDevice() {
        HetDeviceShareApi.getInstance().getAuthDevice(new IHetCallback() {
            @Override
            public void onSuccess(int code, String msg) {
                if (!StringUtils.isNull(msg)){
                    Type type = new TypeToken<List<AuthDeviceModel>>() {
                    }.getType();
                    List<AuthDeviceModel> models = GsonUtil.getGsonInstance().fromJson(msg, type);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                showToast(code+msg);
            }
        });
    }








    /**
     * 获取设备未授权的用户
     */
    private void getDeviceNotAuthUser() {

        HetDeviceShareApi.getInstance().getDeviceNotAuthUser(new IHetCallback() {
            @Override
            public void onSuccess(int code, String msg) {
                if (!StringUtils.isNull(msg)){
                    Type type = new TypeToken<List<DeviceAuthUserModel>>() {
                    }.getType();
                    List<DeviceAuthUserModel> models = GsonUtil.getGsonInstance().fromJson(msg, type);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                showToast(code+msg);
            }
        },deviceId);
    }

    /**
     * 获取设备授权的用户
     */
    private void getDeviceAuthUser() {

        HetDeviceShareApi.getInstance().getDeviceAuthUser(new IHetCallback() {
            @Override
            public void onSuccess(int code, String msg) {
                if (!StringUtils.isNull(msg)){
                    Type type = new TypeToken<List<DeviceAuthUserModel>>() {
                    }.getType();
                    List<DeviceAuthUserModel> models = GsonUtil.getGsonInstance().fromJson(msg, type);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                showToast(code+msg);
            }
        },deviceId);
    }

    /**
     * 设备授权删除
     */
    private void del() {
        final String phone = edtPhone.getText().toString().trim();
        if (StringUtils.isNull(phone)) {
            showToast("用户帐号不能为空");

        }else {
            HetDeviceShareApi.getInstance().del(new IHetCallback() {
                @Override
                public void onSuccess(int code, String msg) {
                          showToast("已删除给"+ phone+"分享信息");
                }

                @Override
                public void onFailed(int code, String msg) {
                          showToast(code+msg);
                }
            },deviceId,phone);
        }


    }

    /**
     * 设备授权同意
     */
    private void agree() {

        HetDeviceShareApi.getInstance().agree(new IHetCallback() {
            @Override
            public void onSuccess(int code, String msg) {
                   showToast("已同意设备授权");
            }

            @Override
            public void onFailed(int code, String msg) {
                showToast(code+msg);
            }
        },deviceId);


    }

    /**
     * 设备授权邀请
     */
    private void invite() {
        String phone = edtPhone.getText().toString().trim();
        if (StringUtils.isNull(phone)) {
            showToast("用户帐号不能为空");

        }else {

            HetDeviceShareApi.getInstance().invert(new IHetCallback() {
                @Override
                public void onSuccess(int code, String msg) {
                    showToast("已成功发出邀请");
                }

                @Override
                public void onFailed(int code, String msg) {
                    showToast(code+msg);
                }
            },deviceId,phone);
        }


    }


}
