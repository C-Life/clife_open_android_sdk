package com.het.sdk.demo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.het.sdk.demo.R;
import com.het.sdk.demo.ui.activity.scene.SceneTypeActivity;
import com.het.sdk.demo.ui.activity.share.ScanMainActivity;

/**
 * 通用fragment
 * Created by xuchao on 2016/6/30.
 */

public class CommonFragment extends BaseFragment {


    private View rootView;
    private Button btnScene;
    private Button btnScan;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_common, container,
                    false);

            initView(rootView);
        }

        return rootView;
    }

    private void initView(View rootView) {

        btnScene=(Button)rootView.findViewById(R.id.btn_scene);
        btnScene.setOnClickListener(onClickListener);
        btnScan=(Button)rootView.findViewById(R.id.btn_scan);
        btnScan.setOnClickListener(onClickListener);

    }

    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_scene:
                    Intent intentScene = new Intent(getActivity(), SceneTypeActivity.class);
                    startActivity(intentScene);
                    break;
                case R.id.btn_scan:
                    Intent intentScan = new Intent(getActivity(), ScanMainActivity.class);
                    startActivity(intentScan);
                    break;

            }
        }


    };
}
