package com.het.sdk.demo.ui.activity.scene;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.gson.reflect.TypeToken;
import com.het.open.lib.api.HetSceneApi;
import com.het.open.lib.callback.IHetCallback;
import com.het.open.lib.model.scene.UserSceneModel;
import com.het.open.lib.model.scene.custom.AddOrUpdateBatchModel;
import com.het.open.lib.model.scene.custom.OptionOneModel;
import com.het.open.lib.model.scene.custom.SceneDeviceModel;
import com.het.open.lib.model.scene.custom.SceneDiyModel;
import com.het.open.lib.model.scene.custom.SceneIDModel;
import com.het.open.lib.model.scene.custom.UserCustomSceneModel;
import com.het.open.lib.utils.GsonUtil;
import com.het.open.lib.utils.StringUtils;
import com.het.sdk.demo.ui.activity.BaseActivity;
import com.het.sdk.demo.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2016, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * <p>描述：自定义场景界面</p>
 * 名称:  授权api<br>
 * 作者: 80010814<br>
 * 版本: 1.0<br>
 * 日期: 2016/3/10. 16:42<br>
 **/
public class SceneDiyActivity extends BaseActivity implements View.OnClickListener{

    private Button btnAddScene;
    private Button btnGetMainMess;
    private Button btnGetConditionList;
    private Button btnAddContion;
    private Button btnGetInDevice;
    private Button btnUserSceneDetail;
    private Button btnUserSceneSubDelete;
    private Button btnSetConditionRelation;
    private Context mContext;
    private  SceneIDModel sceneIDModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_diy);
        this.mContext=this;
        initView();


    }


    private void initView() {
        getSupportActionBar().setTitle("自定义场景");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnAddScene = (Button) findViewById(R.id.btn_add_or_make_scene);
        btnAddScene.setOnClickListener(this);
        btnGetMainMess=(Button)findViewById(R.id.btn_get_scene_main);
        btnGetMainMess.setOnClickListener(this);
        btnGetConditionList=(Button)findViewById(R.id.btn_get_condition_list);
        btnGetConditionList.setOnClickListener(this);
        btnAddContion=(Button)findViewById(R.id.btn_timer_condition_set);
        btnAddContion.setOnClickListener(this);
        btnGetInDevice=(Button)findViewById(R.id.btn_get_in_device);
        btnGetInDevice.setOnClickListener(this);
        btnUserSceneDetail=(Button)findViewById(R.id.btn_user_scene_detail);
        btnUserSceneDetail.setOnClickListener(this);
        btnUserSceneSubDelete=(Button)findViewById(R.id.btn_user_sub_scene_delete);
        btnUserSceneSubDelete.setOnClickListener(this);
        btnSetConditionRelation=(Button)findViewById(R.id.btn_user_set_condition_relationl);
        btnSetConditionRelation.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add_or_make_scene:
                addScene();
                break;
            case R.id.btn_get_scene_main:
                getSceneMain();
                break;
            case R.id.btn_get_condition_list:
                getTwoConditionList();
                break;
            case R.id.btn_timer_condition_set:
                addContionSet();
                break;
            case R.id.btn_get_in_device:
                getInDevice();
                break;
            case  R.id.btn_user_scene_detail:
                userSceneDetail();
                break;
            case  R.id.btn_user_sub_scene_delete:
               userSceneSubDelete();
                break;
            case  R.id.btn_user_set_condition_relationl:
                setConditionRelation();
                break;

        }
    }

    /**
     *  用户条件关系设置
     */
    private void setConditionRelation() {
        if (sceneIDModel!=null){
            String sceneId=sceneIDModel.getUserSceneId();
            HetSceneApi.getInstance().setConditionRelation(new IHetCallback() {
                @Override
                public void onSuccess(int code, String msg) {
                    showToast(code+msg);
                }

                @Override
                public void onFailed(int code, String msg) {
                    showToast(code+msg);
                }
            },sceneId,null,"&&",-1);
        }else {
            showToast("请先添加自定义场景");
        }

    }


    /**
     *  用户子场景删除
     */
    private void userSceneSubDelete() {
        if (sceneIDModel!=null){
            String sceneId=sceneIDModel.getUserSceneId();
            HetSceneApi.getInstance().deleteUserSubScene(new IHetCallback() {
                @Override
                public void onSuccess(int code, String msg) {
                    showToast(code+msg);
                }

                @Override
                public void onFailed(int code, String msg) {

                }
            },sceneId,null,-1);
        }else {
            showToast("请先添加自定义场景");
        }

    }

    /**
     * 用户自定义场景详情
     */
    private void userSceneDetail() {
        if (sceneIDModel!=null){
            String sceneId=sceneIDModel.getUserSceneId();
            HetSceneApi.getInstance().getUserCustomSceneDetail(new IHetCallback() {
                @Override
                public void onSuccess(int code, String msg) {
                    if (!StringUtils.isNull(msg)){
                        Type type = new TypeToken<List<UserCustomSceneModel>>() {
                        }.getType();
                        List<UserCustomSceneModel> models = GsonUtil.getGsonInstance().fromJson(msg, type);
                    }
                }

                @Override
                public void onFailed(int code, String msg) {

                }
            },sceneId,null,-1);
        }else {
            showToast("请先添加自定义场景");
        }

    }

    /**
     * 获取用户输入设备（有控制功能）列表
     */
    private void getInDevice() {
        HetSceneApi.getInstance().getInDeviceList(new IHetCallback() {
            @Override
            public void onSuccess(int code, String msg) {
                if (!StringUtils.isNull(msg)){
                    Type type = new TypeToken<List<SceneDeviceModel>>() {
                    }.getType();
                    List<SceneDeviceModel> models = GsonUtil.getGsonInstance().fromJson(msg, type);
                }else {
                    showToast("没有输入绑定设备 ");
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                showToast(code+msg);
            }
        },-1);
    }

    /**
     * 条件实例批量新增/修改
     */
    private void addContionSet() {
        if (sceneIDModel!=null){
            AddOrUpdateBatchModel model=new AddOrUpdateBatchModel();
            model.setUserSceneId(sceneIDModel.getUserSceneId());//用户场景标识
            model.setSubSceneIndex(0+"");//子场景序号，从0开始
            model.setTimePoint("12:00");//时间点（小时数和分钟数，中间以半角冒号符分隔，形如 12:00）
            model.setRepetition(4+"");	//重复日（0~6表示星期日至星期六；升序，半角逗号分隔，形如1,2,3,）
            model.setConditionInstanceType("4");//条件实例类型，传4
            model.setConditionTypeId("1");//条件类型标识（1为设备查询值，3为定时条件，4为立即启动）
            List<AddOrUpdateBatchModel> dataList=new ArrayList<>();
            dataList.add(model);
            HetSceneApi.getInstance().addOrUpdateBatch(new IHetCallback() {
                @Override
                public void onSuccess(int code, String msg) {
                    showToast(code+msg);
                }

                @Override
                public void onFailed(int code, String msg) {
                    showToast(code+msg);
                }
            },dataList,-1);
        }else {
            showToast("请输入条件选项id" +
                    "");
        }


    }

    /**
     * 条件选项列表
     */
    private void getConditionList() {
        HetSceneApi.getInstance().getOptionList(new IHetCallback() {
            @Override
            public void onSuccess(int code, String msg) {
                if (!StringUtils.isNull(msg)){
                    Type type = new TypeToken<List<OptionOneModel>>() {
                    }.getType();
                    List<OptionOneModel> models = GsonUtil.getGsonInstance().fromJson(msg, type);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                showToast(code+msg);
            }
        },-1);
    }

    /**
     * 条件选项列表(二级)
     */
    private void getTwoConditionList() {
        String deviceId="";
        String conditionOptionId="";
        if (!StringUtils.isNull(conditionOptionId)){
            HetSceneApi.getInstance().getOptionListTwo(new IHetCallback() {
                @Override
                public void onSuccess(int code, String msg) {
                    if (!StringUtils.isNull(msg)){
                        Type type = new TypeToken<List<OptionOneModel>>() {
                        }.getType();
                        List<OptionOneModel> models = GsonUtil.getGsonInstance().fromJson(msg, type);
                    }
                }

                @Override
                public void onFailed(int code, String msg) {
                    showToast(code+msg);
                }
            },conditionOptionId,deviceId,-1);
        }else {
            showToast("请输入条件选项id" +
                    "");
        }

    }

    /**
     * 获取用户场景主信息
     */
    private void getSceneMain() {
        if (sceneIDModel!=null){
            String sceneId=sceneIDModel.getUserSceneId();
            HetSceneApi.getInstance().getSceneMainInfo(new IHetCallback() {
                @Override
                public void onSuccess(int code, String msg) {
                    if (!StringUtils.isNull(msg)){
                        Type type = new TypeToken<UserSceneModel>() {
                        }.getType();
                        UserSceneModel userSceneModel = GsonUtil.getGsonInstance().fromJson(msg, type);
                        showToast("获取用户场景主信息成功");
                    }

                }

                @Override
                public void onFailed(int code, String msg) {
                    showToast(code+msg);
                }
            },sceneId,-1);
        }else {
            showToast("请先添加自定义场景");
        }

    }

    /**
     * 自定义场景
     */
    private void addScene() {
        SceneDiyModel sceneDiyModel=new SceneDiyModel();
        sceneDiyModel.setUserSceneName("我的场景");
        HetSceneApi.getInstance().addOrUpdateCustomScene(new IHetCallback() {
            @Override
            public void onSuccess(int code, String msg) {
                Type type = new TypeToken<SceneIDModel>() {
                }.getType();
                sceneIDModel = GsonUtil.getGsonInstance().fromJson(msg, type);
                showToast("创建场景成功");
            }

            @Override
            public void onFailed(int code, String msg) {
                showToast(code+msg);
            }
        },sceneDiyModel);

    }



}
