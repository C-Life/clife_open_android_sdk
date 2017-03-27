package com.het.sdk.demo.ui.activity.device;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.het.open.lib.api.HetDeviceSubmitApi;
import com.het.open.lib.api.HetDeviceUpdateApi;
import com.het.open.lib.api.HetProtocolApi;
import com.het.open.lib.api.HetSdk;
import com.het.open.lib.callback.IDeviceUpdateView;
import com.het.open.lib.callback.IHetCallback;
import com.het.open.lib.callback.ISubmitUpdateView;
import com.het.open.lib.model.DeviceModel;
import com.het.open.lib.utils.LogUtils;
import com.het.open.lib.utils.StringUtils;
import com.het.sdk.demo.R;
import com.het.sdk.demo.ui.activity.BaseActivity;
import com.het.sdk.demo.utils.Constants;
import com.het.sdk.demo.utils.HandlerUtil;
import com.het.wifi.common.protocol.ProtocolManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 设备控制页面
 */
public class DeviceControlActivity extends BaseActivity {


    private String deviceId;
    private DeviceModel deviceModel;
    private final String TAG = "设备控制页面";
    private EditText edtUpdateConfig;
    private EditText edtUpdateRun;
    private EditText edtUpdateError;
    private Button btnSendConfigData;  //下发数据按钮
    private final int SHOW_UPDATE_CONFIG = 0x01;  //显示配置数据
    private final int SHOW_UPDATE_RUN = 0x02;     //显示运行数据
    private final int  SHOW_UPDATE_ERROR= 0x03;     //显示运行数据
    private final int START_UPDATE_DATA = 0x04;     //开始更新数据
    private String configData;   //当前控制数据
    private Context mContext;

    private HandlerUtil.MessageListener mMessageListener = new HandlerUtil.MessageListener() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case SHOW_UPDATE_CONFIG:
                    final String configMsg = (String) msg.obj;

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            edtUpdateConfig.setText("");
                            edtUpdateConfig.setText(configMsg);
                        }
                    });

                    break;
                case SHOW_UPDATE_RUN:
                    final String runMsg = (String) msg.obj;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            edtUpdateRun.setText("");
                            edtUpdateRun.setText(runMsg);
                        }
                    });

                    break;
                case SHOW_UPDATE_ERROR:
                    String errorMsg = (String) msg.obj;
                    edtUpdateError.setText("");
                    if (StringUtils.isNull(errorMsg)){
                        errorMsg=": 没有异常数据";
                    }
                    edtUpdateError.setText(errorMsg);
                    break;
                case START_UPDATE_DATA:
                    startUpdateData();
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
        this.mContext = this;
        initView();


    }

    @Override
    protected void onStart() {
        super.onStart();

        initData();


    }

    /**
     * 初始并启动更新数据
     */
    private void initUpdateData() {
        HetDeviceUpdateApi.getInstance().init(deviceModel, new IDeviceUpdateView() {
            @Override
            public void updateSuccess(int type, String json) {
                if (StringUtils.isNull(json)||json.contains("null")){
                    return;
                }
                switch (type) {
                    case 1:
                        configData=json;
                        sendMsg(SHOW_UPDATE_CONFIG, "控制数据" + json);
                        break;
                    case 2:
                        sendMsg(SHOW_UPDATE_RUN, "运行数据" + json);
                        break;
                    case 3:
                        sendMsg(SHOW_UPDATE_ERROR, "异常数据" + json);
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

        HetDeviceSubmitApi.getInstance().init(deviceModel, iSubmitUpdateView);
        HetDeviceSubmitApi.getInstance().start();
    }

    private void initData() {
        deviceModel = (DeviceModel) getIntent().getSerializableExtra(Constants.DEVICE_MODEL);
        if (deviceModel != null) {
            int productId = deviceModel.getProductId();
            //判断是否已将协议加载到sdk中，没加载则获取协议并加载到sdk中
            String ret = ProtocolManager.getInstance().getProtocolDate(mContext, deviceModel.getProductId());
            if (ret.equals("0")) {
                HetProtocolApi.getInstance().getProtocol(new IHetCallback() {
                    @Override
                    public void onSuccess(int code, String msg) {
                        if (code == 0) {
                            if (!StringUtils.isNull(msg)) {
                                ProtocolManager.getInstance().loadFromJsonDeal(HetSdk.getInstance().getApplication(), msg);
                                sendMsg(START_UPDATE_DATA);
                            }

                        }


                    }

                    @Override
                    public void onFailed(int code, String msg) {

                    }
                }, productId, 0);
            } else {
                sendMsg(START_UPDATE_DATA);
            }

        }
    }




    /**
     * 开始请求数据
     */
    private void startUpdateData() {
        initUpdateData();
        initSubmitData();
    }


    @Override
    protected void onResume() {
        super.onResume();
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
        edtUpdateError=(EditText) findViewById(R.id.edt_update_error);
        edtUpdateError.setKeyListener(null);
        btnSendConfigData = (Button) findViewById(R.id.btn_send_config_data);
        btnSendConfigData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (configData != null) {

                    try {
                        String data=handleJson(configData);
                        HetDeviceSubmitApi.getInstance().submit(data);
                    } catch (Exception e) {
                        LogUtils.e("下发数据失败",e.toString());
                        showToast(e.toString());
                    }
                }
            }
        });


    }

    private String handleJson(String jsonStr){

       JSONObject jsonObject = null;

        try {
            jsonObject= new JSONObject(jsonStr);
            String key="light";
            if(jsonObject.has(key)){
                jsonObject.remove(key);
                jsonObject.accumulate(key, "1");
            }
//            String updateFlag="updateFlag";
//            if(jsonObject.has(updateFlag)){
//                jsonObject.remove(updateFlag);
//                jsonObject.accumulate(updateFlag,128);
//            }

        } catch (JSONException e) {
            LogUtils.e(TAG,e.toString());

        }
        return  jsonObject.toString();





    }




    private HandlerUtil.StaticHandler mStableHandler = new HandlerUtil.StaticHandler(
            mMessageListener);

    private void sendMsg(int value, String msg) {
        if (mStableHandler != null) {
            mStableHandler.obtainMessage(value, msg).sendToTarget();
        }

    }

    private void sendMsg(int value) {
        if (mStableHandler != null) {
            mStableHandler.obtainMessage(value).sendToTarget();
        }

    }

    ISubmitUpdateView iSubmitUpdateView=new ISubmitUpdateView() {
        @Override
        public void submitFailure(int code, String msg, String json) {
            showToast(code + msg);
        }

        @Override
        public void submitSuccess(String s) {
            if (!StringUtils.isNull(s)){
                showToast(s);
            }else {
                showToast("下发数据成功");
            }
        }
    };
}
