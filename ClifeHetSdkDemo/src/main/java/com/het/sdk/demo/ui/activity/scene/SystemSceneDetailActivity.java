package com.het.sdk.demo.ui.activity.scene;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.gson.reflect.TypeToken;
import com.het.open.lib.api.HetSceneApi;
import com.het.open.lib.callback.IHetCallback;
import com.het.open.lib.model.scene.SystemSceneModel;
import com.het.open.lib.model.scene.custom.SystemSceneDetailModel;
import com.het.open.lib.utils.GsonUtil;
import com.het.open.lib.utils.HetCodeConstants;
import com.het.sdk.demo.ui.activity.BaseActivity;
import com.het.sdk.demo.R;
import com.het.sdk.demo.utils.Constants;

import java.lang.reflect.Type;
import java.util.List;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2016, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * <p>描述：系统场景列表界面</p>
 * 名称:  授权api<br>
 * 作者: 80010814<br>
 * 版本: 1.0<br>
 * 日期: 2016/3/10. 16:42<br>
 **/
public class SystemSceneDetailActivity extends BaseActivity implements View.OnClickListener{

    private Button btnDetail;
    private Button addUserScene;
    private Context mContext;

    private SystemSceneModel curSystemSceneModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_detail_scene);
        this.mContext=this;
        curSystemSceneModel= (SystemSceneModel) getIntent().getSerializableExtra(Constants.SYSTEM_SCENE_MODEL);
        initView();


    }


    private void initView() {
        getSupportActionBar().setTitle("场景类别");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnDetail = (Button) findViewById(R.id.btn_system_scene_detail);
        btnDetail.setOnClickListener(this);
        addUserScene=(Button)findViewById(R.id.btn_add_to_user_scene);
        addUserScene.setOnClickListener(this);


    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_system_scene_detail:
                getSystemSceneDetail();
                break;
            case R.id.btn_add_to_user_scene:
                addToUserScene();
                break;
        }
    }

    private void addToUserScene(){
        HetSceneApi.getInstance().userSceneAdd(new IHetCallback() {
            @Override
            public void onSuccess(int i, String s) {
                if (i== HetCodeConstants.HTTP_RET_SUCCESS){
                    showToast("添加成功");
                }
            }

            @Override
            public void onFailed(int i, String s) {
                showToast(i+s);

            }
        },null,null,curSystemSceneModel.getSceneId(),-1);
    }

    /**
     * 获取系统场景详情
     */
    private void getSystemSceneDetail() {

        HetSceneApi.getInstance().getSystemSceneDetail(new IHetCallback() {
            @Override
            public void onSuccess(int code, String msg) {
                Type type = (new TypeToken<List<SystemSceneDetailModel>>() {
                }).getType();
                List<SystemSceneDetailModel> models=  GsonUtil.getGsonInstance().fromJson(msg, type);
                if (models!=null&&models.size()>0){
                    showToast("获取系统详情成功");
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                showToast(code+msg);

            }
        },curSystemSceneModel.getSceneId(),-1);
    }


}
