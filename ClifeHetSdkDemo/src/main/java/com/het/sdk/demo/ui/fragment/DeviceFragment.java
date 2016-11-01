package com.het.sdk.demo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.het.open.lib.api.HetDeviceListApi;
import com.het.open.lib.api.HetSdk;
import com.het.open.lib.callback.IDeviceList;
import com.het.open.lib.model.DeviceModel;
import com.het.open.lib.utils.GsonUtil;
import com.het.sdk.demo.R;
import com.het.sdk.demo.adapter.AdapterDeviceList;
import com.het.sdk.demo.ui.activity.DeviceManagerActivity;
import com.het.sdk.demo.utils.Constants;
import com.het.sdk.demo.utils.HandlerUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 授权fragment
 * Created by xuchao on 2016/6/30.
 */

public class DeviceFragment extends BaseFragment {


    private View rootView;
    private TextView tvwTip;
    private List<DeviceModel> deviceModels = new ArrayList<>();
    private final int INIT_DATA = 0x01;
    private final int UPDATE_ADAPTER = 0x02;
    private AdapterDeviceList mAdapter;
    private ListView mListView;
    private boolean firstFlag=false;

    private HandlerUtil.MessageListener mMessageListener = new HandlerUtil.MessageListener() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case INIT_DATA:
                    initData();
                    break;
                case UPDATE_ADAPTER:
                    if (mListView!=null){
                        mListView.setVisibility(View.VISIBLE);
                    }
                    if (tvwTip!=null){
                        tvwTip.setVisibility(View.GONE);
                    }
                    if (mAdapter != null) {
                        mAdapter.notifyDataSetChanged();
                    }
                    break;
                default:
                    break;
            }

        }
    };

    private HandlerUtil.StaticHandler mStableHandler = new HandlerUtil.StaticHandler(
            mMessageListener);


    private void initData() {
        if (!HetSdk.getInstance().isAuthLogin()) {
            showTipText("授权登录后查看绑定设备信息");
            return;
        }
        HetDeviceListApi.getInstance().getBindList(new IDeviceList() {


            @Override
            public void onSuccess(String s) {
                Type type = new TypeToken<List<DeviceModel>>() {
                }.getType();
                List<DeviceModel> models = GsonUtil.getGsonInstance().fromJson(s, type);
                if (models != null && models.size() > 0) {
                    deviceModels.clear();
                    deviceModels.addAll(models);
                    sendMsg(UPDATE_ADAPTER);
                } else {
                    showTipText("未绑定任何设备");
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                showToast(msg);
            }
        });
    }

    private void showTipText(String text){
        if (mListView!=null){
            mListView.setVisibility(View.GONE);
        }
        tvwTip.setText(text);
        tvwTip.setVisibility(View.VISIBLE);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_device, container,
                    false);
            initView(rootView);
        }

        return rootView;
    }

    private void initView(View rootView) {
        mListView = (ListView) rootView.findViewById(R.id.device_list);
        tvwTip=(TextView)rootView.findViewById(R.id.tvw_tip);
        tvwTip.setVisibility(View.GONE);
        mAdapter = new AdapterDeviceList(deviceModels, getActivity());
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), DeviceManagerActivity.class);
                DeviceModel deviceModel = deviceModels.get(position);
                intent.putExtra(Constants.DEVICE_MODEL, deviceModel);
                startActivity(intent);
            }
        });
        if (!firstFlag){
            sendMsg(INIT_DATA);
            firstFlag=true;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        sendMsg(INIT_DATA);
    }

//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        // TODO Auto-generated method stub
//        super.onHiddenChanged(hidden);
//        if (hidden) {
//
//        } else {
//            if (firstFlag){
//                sendMsg(INIT_DATA);
//            }
//
//        }
//    }

    private void sendMsg(int value) {
        if (mStableHandler != null) {
            mStableHandler.obtainMessage(value).sendToTarget();
        }

    }
}
