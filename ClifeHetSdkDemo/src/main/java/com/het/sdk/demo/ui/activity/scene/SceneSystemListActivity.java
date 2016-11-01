package com.het.sdk.demo.ui.activity.scene;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.het.open.lib.api.HetSceneApi;
import com.het.open.lib.callback.IHetCallback;
import com.het.open.lib.model.scene.SystemSceneModel;
import com.het.open.lib.utils.GsonUtil;
import com.het.sdk.demo.adapter.scene.SystemExampleSceneAdapter;
import com.het.sdk.demo.ui.activity.BaseActivity;
import com.het.sdk.demo.R;
import com.het.sdk.demo.utils.Constants;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统场景页面
 */
public class SceneSystemListActivity extends BaseActivity {

    private ListView mListView;
    private List<SystemSceneModel> systemSceneModelList = new ArrayList<>();
    private SystemExampleSceneAdapter mSystemExampleSceneAdapter;
    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_list);
        this.mContext = this;
        initView();
        initData();

    }

    private void initData() {
        HetSceneApi.getInstance().getSceneList(new IHetCallback() {
            @Override
            public void onSuccess(int code, String s) {
                Type type = new TypeToken<List<SystemSceneModel>>() {
                }.getType();
                List<SystemSceneModel> scenes = GsonUtil.getGsonInstance().fromJson(s, type);
                if (scenes != null && scenes.size() > 0) {
                    systemSceneModelList.clear();
                    systemSceneModelList.addAll(scenes);
                    if (mSystemExampleSceneAdapter != null) {
                        mSystemExampleSceneAdapter.notifyDataSetChanged();
                    }
                } else {
                    showToast("没有场景");
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                showToast(code + msg);
            }
        },"false");

    }


    private void initView() {
        getSupportActionBar().setTitle("系统场景");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mListView = (ListView) findViewById(R.id.listView_scene);
        mSystemExampleSceneAdapter = new SystemExampleSceneAdapter(this, systemSceneModelList);
        mListView.setAdapter(mSystemExampleSceneAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SystemSceneModel sceneModel = systemSceneModelList.get(position);

                Intent intent=new Intent(SceneSystemListActivity.this,SystemSceneDetailActivity.class);
                intent.putExtra(Constants.SYSTEM_SCENE_MODEL,sceneModel);
                startActivity(intent);

            }
        });

    }


}
