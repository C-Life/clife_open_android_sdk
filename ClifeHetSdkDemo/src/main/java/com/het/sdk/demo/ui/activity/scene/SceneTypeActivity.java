package com.het.sdk.demo.ui.activity.scene;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.het.open.lib.api.HetSdk;
import com.het.sdk.demo.ui.activity.BaseActivity;
import com.het.sdk.demo.R;


/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2016, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * <p>描述：场景列表界面</p>
 * 名称:  授权api<br>
 * 作者: 80010814<br>
 * 版本: 1.0<br>
 * 日期: 2016/3/10. 16:42<br>
 **/
public class SceneTypeActivity extends BaseActivity {

    private Button btnSystem;
    private Button btnUser;
    private Button btnAddScene;
    private Context mContext;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_type);
        this.mContext=this;
        initView();
    }


    private void initView() {
        getSupportActionBar().setTitle("场景类别");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnSystem = (Button) findViewById(R.id.btn_system_scene);
        btnSystem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ;
                Intent intentSystemScene = new Intent(SceneTypeActivity.this, SceneSystemListActivity.class);
                startActivity(intentSystemScene);
            }
        });
        btnUser = (Button) findViewById(R.id.btn_user_scene);
        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (HetSdk.getInstance().isAuthLogin()) {
                    ;
                    Intent intentUserScene = new Intent(SceneTypeActivity.this, SceneUserListActivity.class);
                    startActivity(intentUserScene);

                } else {
                    showToast("登录后获取用户场景");
                }
            }
        });
        btnAddScene=(Button) findViewById(R.id.btn_add_scene);
        btnAddScene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (HetSdk.getInstance().isAuthLogin()) {
                    Intent intentDiy = new Intent(SceneTypeActivity.this, SceneDiyActivity.class);
                    startActivity(intentDiy);


                } else {
                    showToast("登录后自定义场景");
                }
            }
        });

    }



}
