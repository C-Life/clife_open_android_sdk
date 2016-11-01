package com.het.sdk.demo.ui.activity.scene;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.het.open.lib.api.HetSceneApi;
import com.het.open.lib.callback.IHetCallback;
import com.het.open.lib.model.scene.UserSceneModel;
import com.het.open.lib.utils.GsonUtil;
import com.het.sdk.demo.R;
import com.het.sdk.demo.adapter.scene.UserSceneListAdapter;
import com.het.sdk.demo.ui.activity.BaseActivity;
import com.het.sdk.demo.utils.Constants;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2016, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * <p>描述：用户场景列表界面</p>
 * 名称:  授权api<br>
 * 作者: 80010814<br>
 * 版本: 1.0<br>
 * 日期: 2016/3/10. 16:42<br>
 **/
public class SceneUserListActivity extends BaseActivity {

    private ListView mListView;
    private UserSceneListAdapter mUserSceneListAdapter;
    private List<UserSceneModel> userSceneModels=new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_list);
        initView();
        initData();

    }


    private void initView() {


        getSupportActionBar().setTitle("用户场景");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mListView = (ListView) findViewById(R.id.listView_scene);
        mUserSceneListAdapter= new UserSceneListAdapter(this, userSceneModels);
        mListView.setAdapter( mUserSceneListAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserSceneModel sceneModel = userSceneModels.get(position);
                Intent intent=new Intent(SceneUserListActivity.this,UserSceneDetailActivity.class);
                intent.putExtra(Constants.USER_SCENE_MODEL,sceneModel);
                startActivity(intent);
            }
        });

    }

    private void initData() {
        HetSceneApi.getInstance().getUserSceneList(new IHetCallback() {
            @Override
            public void onSuccess(int code, String s) {
                Type type = new TypeToken<List<UserSceneModel>>() {
                }.getType();
                List<UserSceneModel> scenes = GsonUtil.getGsonInstance().fromJson(s, type);
                if (scenes != null && scenes.size() > 0) {
                    userSceneModels.clear();
                    userSceneModels.addAll(scenes);
                    if (mUserSceneListAdapter != null) {
                        mUserSceneListAdapter.notifyDataSetChanged();
                    }
                } else {
                    showToast("没有场景");
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                showToast(code + msg);
            }
        },null,null,"false",null);

    }


}
