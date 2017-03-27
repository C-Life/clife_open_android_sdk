package com.het.sdk.demo.ui.activity.device;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.het.open.lib.api.HetDeviceWifiControlApi;
import com.het.open.lib.callback.IHetCallback;
import com.het.open.lib.model.DeviceModel;
import com.het.open.lib.utils.LogUtils;
import com.het.open.lib.utils.StringUtils;
import com.het.sdk.demo.R;
import com.het.sdk.demo.ui.activity.BaseActivity;
import com.het.sdk.demo.utils.Constants;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * wifi设备控制页面
 */
public class DeviceWifiControlActivity extends BaseActivity implements View.OnClickListener {


    private String deviceId;
    private DeviceModel deviceModel;
    private final String TAG = "设备控制页面";
    private Button btnSendConfigData;  //下发数据按钮
    private Button btnGetConfigData;  //下发数据按钮
    private Button btnGetRunData;  //下发数据按钮
    private Button btnGetErrorData;  //下发数据按钮
    private String configData;   //当前控制数据




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_wifi_control);
        initView();
        deviceModel = (DeviceModel) getIntent().getSerializableExtra(Constants.DEVICE_MODEL);

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

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void initView() {
        getSupportActionBar().setTitle("设备控制");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnSendConfigData = (Button) findViewById(R.id.btn_send_config_data);
        btnSendConfigData.setOnClickListener(this);
        btnGetConfigData = (Button) findViewById(R.id.btn_get_config_data);
        btnGetConfigData .setOnClickListener(this);
        btnGetRunData = (Button) findViewById(R.id.btn_get_run_data);
        btnGetRunData.setOnClickListener(this);
        btnGetErrorData = (Button) findViewById(R.id.btn_get_error_data);
        btnGetErrorData.setOnClickListener(this);

    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_get_config_data:
                getConfigData();
                break;
            case R.id.btn_get_error_data:
                getErrorData();
                break;
            case R.id.btn_get_run_data:
                getRunData();
                break;
            case R.id.btn_send_config_data:
                sendConfigData();
                break;
        }
    }

    /**
     * 获取配置数据
     */
    private void getConfigData(){
        HetDeviceWifiControlApi.getInstance().getConfigFromDevice(new IHetCallback() {
            @Override
            public void onSuccess(int code, String msg) {
                if (code==0){
                    if (!StringUtils.isNull(msg)){
                        configData=msg;
                        showToast(msg);

                    }

                }
            }

            @Override
            public void onFailed(int code, String msg) {
                showToast(msg);
            }
        },deviceModel.getDeviceId());
    }

    /**
     * 下发数据
     */
    private void sendConfigData(){
        if (!StringUtils.isNull(configData)){
            HetDeviceWifiControlApi.getInstance().setDataToDevice(new IHetCallback() {
                @Override
                public void onSuccess(int code, String msg) {
                    if (code==0){
                        showToast(msg+"");
                    }
                }

                @Override
                public void onFailed(int code, String msg) {
                    showToast(msg);
                }
            },deviceModel.getDeviceId(),configData);
        }

    }

    /**
     * 获取运行数据
     */
    private void getRunData(){
        HetDeviceWifiControlApi.getInstance().getRunFromDevice(new IHetCallback() {
            @Override
            public void onSuccess(int code, String msg) {
                if (code==0){
                    showToast(msg+"");
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                showToast(msg);
            }
        },deviceModel.getDeviceId());
    }

    /**
     * 获取异常数据
     */
    private void getErrorData(){
        HetDeviceWifiControlApi.getInstance().getErrorDataFromDevice(new IHetCallback() {
            @Override
            public void onSuccess(int code, String msg) {
                if (code==0){
                    showToast(msg+"");
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                showToast(msg);
            }
        },deviceModel.getDeviceId());
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
}
