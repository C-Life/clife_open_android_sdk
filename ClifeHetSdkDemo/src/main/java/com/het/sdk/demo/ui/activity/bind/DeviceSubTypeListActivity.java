package com.het.sdk.demo.ui.activity.bind;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.het.open.lib.api.HetDeviceListApi;
import com.het.open.lib.callback.IDeviceList;
import com.het.open.lib.model.DeviceModel;
import com.het.open.lib.model.DeviceSubModel;
import com.het.open.lib.model.DeviceTypeModel;
import com.het.open.lib.utils.GsonUtil;
import com.het.sdk.demo.R;
import com.het.sdk.demo.adapter.AdapterDeviceSubtypeList;
import com.het.sdk.demo.ui.activity.BaseActivity;
import com.het.sdk.demo.utils.Constants;
import com.het.sdk.demo.utils.HandlerUtil;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 设备小类列表
 */
public class DeviceSubTypeListActivity extends BaseActivity {


    private List<DeviceSubModel> deviceModels = new ArrayList<>();
    private final int INIT_DATA = 0x01;
    private final int UPDATE_ADAPTER = 0x02;
    private AdapterDeviceSubtypeList mAdapter;
    private ListView mListView;
    private DeviceTypeModel deviceTypeModel;

    private HandlerUtil.MessageListener mMessageListener = new HandlerUtil.MessageListener() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case INIT_DATA:
                    initData();
                    break;
                case UPDATE_ADAPTER:
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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);
        initView();


    }

    private void initData() {
        deviceTypeModel = (DeviceTypeModel) getIntent().getSerializableExtra(Constants.DEVICE_TYPE_MODEL);
        String deviceType = deviceTypeModel.getDeviceTypeId();
        HetDeviceListApi.getInstance().getSubTypeListProduct(new IDeviceList() {
            @Override
            public void onSuccess(String s) {
                Type type = new TypeToken<List<DeviceSubModel>>() {
                }.getType();
                List<DeviceSubModel> models = GsonUtil.getGsonInstance().fromJson(s, type);
                if (models != null && models.size() > 0) {
                    deviceModels.clear();
                    deviceModels.addAll(models);
                    sendMsg(UPDATE_ADAPTER);
                } else {
                    showToast("没有设备");
                }
            }

            @Override
            public void onFailed(int i, String s) {

            }
        }, deviceType);
    }


    private void initView() {
        getSupportActionBar().setTitle("设备小类列表");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mListView = (ListView) findViewById(R.id.device_list);
        mAdapter = new AdapterDeviceSubtypeList(deviceModels, this);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DeviceSubModel deviceSubModel=deviceModels.get(position);
                DeviceModel deviceModel=new DeviceModel();
                deviceModel.setDeviceTypeId(deviceSubModel.getDeviceTypeId()+"");
                deviceModel.setDeviceSubtypeId(deviceSubModel.getDeviceSubtypeId()+"");
                deviceModel.setProductId(deviceSubModel.getProductId());
                deviceModel.setModuleType(deviceSubModel.getModuleType());
                deviceModel.setModuleId(deviceSubModel.getModuleId());
                deviceModel.setDeviceName(deviceSubModel.getProductName());
                int type=deviceModel.getModuleType();
                switch (type){
                    case 1:
                        Intent intent = new Intent(DeviceSubTypeListActivity.this, WifiBindActivity.class);
                        intent.putExtra(Constants.DEVICE_MODEL, deviceModel);
                        startActivity(intent);
                        finish();
                        break;
                    case 2:
                        Intent intentBle = new Intent(DeviceSubTypeListActivity.this, BleBindActivity.class);
                        intentBle.putExtra(Constants.DEVICE_MODEL, deviceModel);
                        startActivity(intentBle);
                        finish();
                        break;
                }




            }
        });
        sendMsg(INIT_DATA);

    }


    private void sendMsg(int value) {
        if (mStableHandler != null) {
            mStableHandler.obtainMessage(value).sendToTarget();
        }

    }


}
