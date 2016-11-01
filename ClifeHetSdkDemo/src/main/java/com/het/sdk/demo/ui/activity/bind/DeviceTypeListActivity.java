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
import com.het.open.lib.model.DeviceTypeModel;
import com.het.open.lib.utils.GsonUtil;
import com.het.sdk.demo.R;
import com.het.sdk.demo.adapter.AdapterDeviceTypeList;
import com.het.sdk.demo.ui.activity.BaseActivity;
import com.het.sdk.demo.utils.Constants;
import com.het.sdk.demo.utils.HandlerUtil;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 *设备大类列表
 */
public class DeviceTypeListActivity extends BaseActivity {


    private List<DeviceTypeModel> deviceModels=new ArrayList<>();
    private final int INIT_DATA = 0x01;
    private final int UPDATE_ADAPTER = 0x02;
    private AdapterDeviceTypeList mAdapter;
    private ListView mListView;



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
        HetDeviceListApi.getInstance().getTypeList(new IDeviceList() {

            @Override
            public void onSuccess(String s) {
                Type type = new TypeToken<List<DeviceTypeModel>>() {
                }.getType();
                List<DeviceTypeModel> models = GsonUtil.getGsonInstance().fromJson(s, type);
                if (models != null && models.size() > 0) {
                    deviceModels.clear();
                    deviceModels.addAll(models);
                    sendMsg(UPDATE_ADAPTER);
                } else {
                    showToast("没有设备");
                }
            }

            @Override
            public void onFailed(int code, String msg) {

            }
        });
    }



    private void initView() {
        getSupportActionBar().setTitle("设备大类列表");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mListView=(ListView)findViewById(R.id.device_list);
        mAdapter=new AdapterDeviceTypeList(deviceModels,this);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DeviceTypeModel deviceModel=deviceModels.get(position);
                Intent intent=new Intent(DeviceTypeListActivity.this,DeviceSubTypeListActivity.class);
                intent.putExtra(Constants.DEVICE_TYPE_MODEL,deviceModel);
                startActivity(intent);

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
