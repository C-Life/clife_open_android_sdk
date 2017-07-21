package com.het.sdk.demo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.het.open.lib.api.HetHttpApi;
import com.het.open.lib.callback.IHetCallback;
import com.het.open.lib.utils.LogUtils;
import com.het.open.lib.utils.StringUtils;
import com.het.sdk.demo.R;
import com.het.sdk.demo.ui.activity.scene.SceneTypeActivity;
import com.het.sdk.demo.ui.activity.share.ScanMainActivity;

import java.util.TreeMap;

/**
 * 通用fragment
 * Created by xuchao on 2016/6/30.
 */

public class CommonFragment extends BaseFragment implements View.OnClickListener {


    private View rootView;
    private Button btnScene;
    private Button btnScan;
    private Button btnHttpGet;
    private Button btnHttpPost;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_common, container,
                    false);

            initView(rootView);
        }

        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {

        btnScene = (Button) rootView.findViewById(R.id.btn_scene);
        btnScene.setOnClickListener(this);
        btnScan = (Button) rootView.findViewById(R.id.btn_scan);
        btnScan.setOnClickListener(this);
        btnHttpGet = (Button) rootView.findViewById(R.id.btn_http_get);
        btnHttpGet.setOnClickListener(this);
        btnHttpPost = (Button) rootView.findViewById(R.id.btn_http_post);
        btnHttpPost.setOnClickListener(this);
    }

//    View.OnClickListener onClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            switch (v.getId()) {
//
//
//            }
//        }
//
//
//    };

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
            case R.id.btn_http_get:
                httpGet();
                break;
            case R.id.btn_http_post:

                break;
        }
    }


    public void httpGet(){
        String url="v1/app/customization/broadair/analysis/cityAirCleanSort";
        //startDate	是	string	日期（yyyy-MM-dd）
        //rankMode	否	Integer	排名方式（1-计重，2-计数，默认值计数）
        //spaceType	否	Integer	空间类型排序（1-室外，2-室内，默认值室外）
        //orderType	否	Integer	排序方式（1-升序，2-降序，默认值升序）
        TreeMap<String, String> params = new TreeMap<String, String>();
        params.put("startDate", "2017-07-20");
        params.put("rankMode", "1");
        params.put("spaceType", "1");
        params.put("orderType", "1");
        HetHttpApi.getInstance().hetGet(url, params,new IHetCallback() {
            @Override
            public void onSuccess(int code, String msg) {
                LogUtils.d("通用请求",code+msg);
                if (!StringUtils.isNull(msg)){
                    showToast(msg);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                LogUtils.d("通用请求",code+msg);
                if (!StringUtils.isNull(msg)){
                    showToast(msg);
                }
            }
        });
    }
}
