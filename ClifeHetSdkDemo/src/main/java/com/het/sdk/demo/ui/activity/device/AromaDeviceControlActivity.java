package com.het.sdk.demo.ui.activity.device;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.het.open.lib.api.HetDeviceSubmitApi;
import com.het.open.lib.api.HetDeviceUpdateApi;
import com.het.open.lib.callback.IDeviceUpdateView;
import com.het.open.lib.callback.ISubmitUpdateView;
import com.het.open.lib.callback.IUdpModelParser;
import com.het.open.lib.model.DeviceModel;
import com.het.open.lib.utils.GsonUtil;
import com.het.open.lib.utils.LogUtils;
import com.het.sdk.demo.ui.activity.BaseActivity;
import com.het.sdk.demo.R;
import com.het.sdk.demo.model.aroma.AromaConfigModel;
import com.het.sdk.demo.model.aroma.AromaInPacket;
import com.het.sdk.demo.model.aroma.AromaOutPacket;
import com.het.sdk.demo.model.aroma.AromaRunModel;
import com.het.sdk.demo.utils.Constants;
import com.het.sdk.demo.utils.HandlerUtil;

/**
 * 香薰机控制页面
 */
public class AromaDeviceControlActivity extends BaseActivity implements IUdpModelParser {


    private String deviceId;
    private DeviceModel deviceModel;
    private final String TAG = "香薰机控制页面";
    private EditText edtUpdateConfig;
    private EditText edtUpdateRun;
    private Button btnSendConfigData;  //下发数据按钮
    private final int SHOW_UPDATE_CONFIG = 0x01;
    private final int SHOW_UPDATE_RUN = 0x02;
    private AromaConfigModel curAromaConfigModel;   //当前设定数据

    private HandlerUtil.MessageListener mMessageListener = new HandlerUtil.MessageListener() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case SHOW_UPDATE_CONFIG:
                    String configMsg = (String) msg.obj;
                    edtUpdateConfig.setText("");
                    edtUpdateConfig.setText(configMsg);
                    break;
                case SHOW_UPDATE_RUN:
                    String runMsg = (String) msg.obj;
                    edtUpdateRun.setText("");
                    edtUpdateRun.setText(runMsg);
                    break;
                default:
                    break;
            }

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_control);
        initView();
        deviceModel = (DeviceModel) getIntent().getSerializableExtra(Constants.DEVICE_MODEL);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (deviceModel != null) {
            initData();
        }

    }

    /**
     * 初始并启动更新数据
     */
    private void initUpdateData() {
        HetDeviceUpdateApi.getInstance().init(deviceModel, this, new IDeviceUpdateView() {
            @Override
            public void updateSuccess(int type, String json) {
                switch (type) {
                    case 1:
                         Gson gson=new Gson();
                         curAromaConfigModel= gson.fromJson(json,AromaConfigModel.class);
                         sendMsg(SHOW_UPDATE_CONFIG, "控制数据" + json);
                        break;
                    case 2:
                        sendMsg(SHOW_UPDATE_RUN, "运行数据" + json);
                        break;


                }

            }

            @Override
            public void updateError(int code, String msg, int id) {
                showToast(code + msg);
                LogUtils.e(TAG, code + msg);
            }
        });
        HetDeviceUpdateApi.getInstance().start();
    }

    /**
     * 初始提交数据
     */
    private void initSubmitData() {
        HetDeviceSubmitApi.getInstance().init(deviceModel, this, new ISubmitUpdateView() {
            @Override
            public void submitFailure(int code, String msg, String json) {
                showToast(code + msg);
            }

            @Override
            public void submitSuccess(String s) {
                showToast(s);
            }
        });
        HetDeviceSubmitApi.getInstance().start();
    }

    private void initData() {
        initUpdateData();
        initSubmitData();
    }


    @Override
    protected void onResume() {
        super.onResume();
        //mUpdateProxy.resume();
        // mSubmitProxy.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();
        HetDeviceUpdateApi.getInstance().stop();
        HetDeviceSubmitApi.getInstance().stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HetDeviceUpdateApi.getInstance().destory();
        HetDeviceSubmitApi.getInstance().destory();
    }

    private void initView() {
        getSupportActionBar().setTitle("设备控制");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        edtUpdateConfig = (EditText) findViewById(R.id.edt_update_config);
        edtUpdateConfig.setKeyListener(null);
        edtUpdateRun = (EditText) findViewById(R.id.edt_update_run);
        edtUpdateRun.setKeyListener(null);
        btnSendConfigData = (Button) findViewById(R.id.btn_send_config_data);
        btnSendConfigData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (curAromaConfigModel != null) {
                    curAromaConfigModel.setColor(2);
                    Gson gson = new Gson();
                    String json = gson.toJson(curAromaConfigModel);
                    try {
                        HetDeviceSubmitApi.getInstance().submit(json);
                    } catch (Exception e) {
                        showToast(e.toString());
                    }
                }
            }
        });


    }


    private HandlerUtil.StaticHandler mStableHandler = new HandlerUtil.StaticHandler(
            mMessageListener);

    private void sendMsg(int value, String msg) {
        if (mStableHandler != null) {
            mStableHandler.obtainMessage(value, msg).sendToTarget();
        }

    }


    @Override
    public byte[] paserJson2Byte(String json) {

        AromaConfigModel conf = GsonUtil.getGsonInstance().fromJson(json, AromaConfigModel.class);
        byte[] bytes = AromaOutPacket.toConfigBytes(conf);
        return bytes;
    }

    @Override
    public String paserByte2Json(int cmd, byte[] b) {
        String msg = "";
        switch (cmd) {
            case 1:
                AromaConfigModel aromaConfigModel = AromaInPacket.toConfigModel(b);
                curAromaConfigModel = aromaConfigModel;
                msg = aromaConfigModel.toString();
                //sendMsg(SHOW_UPDATE_CONFIG, "控制数据" + msg);

                break;
            case 2:
                AromaRunModel aromaRunModel = AromaInPacket.toRunModel(b);
                msg = aromaRunModel.toString();

                //sendMsg(SHOW_UPDATE_RUN, "运行数据" + msg);
                break;


        }
        return msg;

    }
}
