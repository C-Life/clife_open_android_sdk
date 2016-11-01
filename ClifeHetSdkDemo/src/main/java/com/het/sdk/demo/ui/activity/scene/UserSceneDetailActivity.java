package com.het.sdk.demo.ui.activity.scene;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.het.open.lib.api.HetSceneApi;
import com.het.open.lib.callback.IHetCallback;
import com.het.open.lib.model.scene.UserSceneModel;
import com.het.sdk.demo.ui.activity.BaseActivity;
import com.het.sdk.demo.R;
import com.het.sdk.demo.utils.Constants;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2016, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * <p>描述：用户场景详情界面</p>
 * 名称:  授权api<br>
 * 作者: 80010814<br>
 * 版本: 1.0<br>
 * 日期: 2016/3/10. 16:42<br>
 **/
public class UserSceneDetailActivity extends BaseActivity implements View.OnClickListener{

    private Button btnDeviceList;
    private Button btnConditionList;
    private Button btnActionList;
    private Button btnAdd;
    private Button btnRefersh;
    private Button btndelect;
    private Button btnStart;
    private Button btnStop;
    private Button btnDelectDevice;
    private Context mContext;
    private UserSceneModel curUserSceneModel;
    private final String TAG="用户场景接口列表";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail_scene);
        this.mContext=this;
        curUserSceneModel= (UserSceneModel) getIntent().getSerializableExtra(Constants.USER_SCENE_MODEL);
        initView();


    }


    private void initView() {
        getSupportActionBar().setTitle("用户场景接口列表");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnDeviceList = (Button) findViewById(R.id.btn_user_scene_device_list);
        btnDeviceList.setOnClickListener(this);
        btnConditionList = (Button) findViewById(R.id.btn_user_scene_condition_list);
        btnConditionList.setOnClickListener(this);
        btnActionList=(Button) findViewById(R.id.btn_user_scene_action_list);
        btnActionList.setOnClickListener(this);
        btnAdd=(Button)findViewById(R.id.btn_user_scene_add);
        btnAdd.setOnClickListener(this);
        btnRefersh=(Button)findViewById(R.id.btn_user_scene_refersh);
        btnRefersh.setOnClickListener(this);
        btndelect=(Button)findViewById(R.id.btn_user_scene_delete);
        btndelect.setOnClickListener(this);
        btnStart=(Button)findViewById(R.id.btn_user_scene_start);
        btnStart.setOnClickListener(this);
        btnStop=(Button)findViewById(R.id.btn_user_scene_stop);
        btnStop.setOnClickListener(this);
        btnDelectDevice=(Button)findViewById(R.id.btn_user_scene_device_delete);
        btnDelectDevice.setOnClickListener(this);



    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_user_scene_device_list:
                getDeviceList();
                break;
            case R.id.btn_user_scene_condition_list:
                getConditionList();
                break;
            case R.id.btn_user_scene_action_list:
                getActionList();
                break;
            case R.id.btn_user_scene_add:
                add();
                break;
            case R.id.btn_user_scene_refersh:
                refersh();
                break;
            case R.id.btn_user_scene_delete:
                delete();
                break;
            case R.id.btn_user_scene_start:
                start();
                break;
            case R.id.btn_user_scene_stop:
                stop();
                break;
            case R.id.btn_user_scene_device_delete:
               deviceDelect();
                break;

        }
    }

    private void deviceDelect(){

    }

    private void getActionList() {
    }

    private void delete() {

        HetSceneApi.getInstance().deleteUserScene(new IHetCallback() {
            @Override
            public void onSuccess(int code, String msg) {
                showToast(code+msg);
            }

            @Override
            public void onFailed(int code, String msg) {
                showToast(code+msg);

            }
        },curUserSceneModel.getUserSceneId(),-1);
    }

    private void add() {
        HetSceneApi.getInstance().startUserScene(new IHetCallback() {
            @Override
            public void onSuccess(int code, String msg) {
                showToast(code+msg);
            }

            @Override
            public void onFailed(int code, String msg) {
                showToast(code+msg);

            }
        },curUserSceneModel.getSceneId(),-1);

    }

    private void start() {
        HetSceneApi.getInstance().startUserScene(new IHetCallback() {
            @Override
            public void onSuccess(int code, String msg) {
                showToast(code+msg);
            }

            @Override
            public void onFailed(int code, String msg) {
                showToast(code+msg);

            }
        },curUserSceneModel.getUserSceneId(),-1);



    }

    private void stop() {
        HetSceneApi.getInstance().startUserScene(new IHetCallback() {
            @Override
            public void onSuccess(int code, String msg) {
                showToast(code+msg);
            }

            @Override
            public void onFailed(int code, String msg) {
                showToast(code+msg);

            }
        },curUserSceneModel.getUserSceneId(),-1);
    }

    private void refersh() {

        HetSceneApi.getInstance().startUserScene(new IHetCallback() {
            @Override
            public void onSuccess(int code, String msg) {
                showToast(code+msg);
            }

            @Override
            public void onFailed(int code, String msg) {
                showToast(code+msg);

            }
        },curUserSceneModel.getSceneId(),-1);
    }

    private void getConditionList() {
    }

    private void getDeviceList() {
    }


}
