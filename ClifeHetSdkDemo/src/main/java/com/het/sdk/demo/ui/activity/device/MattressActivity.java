package com.het.sdk.demo.ui.activity.device;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.gson.reflect.TypeToken;
import com.het.open.lib.api.HetSleepBleControlApi;
import com.het.open.lib.api.HetSleepLaceReportApi;
import com.het.open.lib.callback.IHetCallback;
import com.het.open.lib.model.DeviceModel;
import com.het.open.lib.utils.GsonUtil;
import com.het.open.lib.utils.LogUtils;
import com.het.open.lib.utils.StringUtils;
import com.het.open.lib.utils.TimeUtils;
import com.het.sdk.demo.R;
import com.het.sdk.demo.model.mattress.MattressModel;
import com.het.sdk.demo.model.mattress.SleepDataModel;
import com.het.sdk.demo.ui.activity.BaseActivity;
import com.het.sdk.demo.utils.Constants;
import com.het.sdk.demo.utils.HandlerUtil;
import java.util.List;


/**
 * 蓝牙设备控制页面（睡眠带子）
 */

public class MattressActivity extends BaseActivity implements View.OnClickListener {


    private String deviceId;
    private DeviceModel deviceModel;
    private final String TAG = "带子设备控制页面";
    private EditText editReal;
    private EditText editHistroy;
    private Button btnSync;
    private Button btnHistroy;
    private Button btnReal;
    private Button btnDayReport;
    private Button btnTimeReport;
    private final int SHOW_REAL_DATA = 0x01;
    private final int SHOW_HISTROY_DATA = 0x02;


    private HandlerUtil.MessageListener mMessageListener = new HandlerUtil.MessageListener() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case SHOW_REAL_DATA:
                    byte[] datas = (byte[]) msg.obj;
                    dealRealData(datas);
                    break;
                case SHOW_HISTROY_DATA:

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
        setContentView(R.layout.activity_lace_device_control);
        initView();
        initData();
    }

    private void initData() {
        deviceModel = (DeviceModel) getIntent().getSerializableExtra(Constants.DEVICE_MODEL);
        getSupportActionBar().setTitle(deviceModel.getDeviceName() + "控制");
    }

    private void initView() {

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnSync = (Button) findViewById(R.id.btn_sync_data);
        btnSync.setOnClickListener(this);
        btnHistroy = (Button) findViewById(R.id.btn_get_histroy_data);
        btnHistroy.setOnClickListener(this);
        btnReal = (Button) findViewById(R.id.btn_real_data);
        btnReal.setOnClickListener(this);
        editReal = (EditText) findViewById(R.id.edt_real);
        editHistroy = (EditText) findViewById(R.id.edt_histroy);
        btnDayReport=(Button)findViewById(R.id.btn_getSummaryDayData);
        btnDayReport.setOnClickListener(this);
        btnTimeReport=(Button)findViewById(R.id.btn_getDayDataList);
        btnTimeReport.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_getSummaryDayData:
//                HetSleepLaceReportApi.getInstance().getSummaryDayData(new IHetCallback() {
//                    @Override
//                    public void onSuccess(int code, String msg) {
//                        if (code==0){
//                            if (!StringUtils.isNull(msg)){
//                                showToast(msg);
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailed(int code, String msg) {
//                        showToast(code+msg);
//                    }
//                },deviceModel.getDeviceId(),"2017-01-07",480);
                //  HetSleepLaceReportApi.getInstance().
                //  HetSleepLaceReportApi.getInstance().
                break;
            case R.id.btn_getDayDataList:
                HetSleepLaceReportApi.getInstance().getDayDataList(new IHetCallback() {
                    @Override
                    public void onSuccess(int code, String msg) {
                        if (code==0){
                            if (!StringUtils.isNull(msg)){
                                showToast(msg);
                            }
                        }
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        showToast(code+msg);
                    }
                },deviceModel.getDeviceId(),"2017-01-06","2017-01-07");

                break;
            case R.id.btn_sync_data:
                showDialog("同步数据");
                HetSleepBleControlApi.getInstance().syncData( new IHetCallback() {
                    @Override
                    public void onSuccess(int i, String s) {
                        hideDialog();
                        showToast("同步数据成功");
                    }
                    @Override
                    public void onFailed(int i, String s) {
                        hideDialog();
                        showToast("同步失败" + s);
                    }


                },deviceModel);


                break;
            case R.id.btn_get_histroy_data:
                //获取一周内数据
                showDialog("获取历史数据");
                String startTime = TimeUtils.getBeforeDate(8);
                String endTime = TimeUtils.getYesterday();
                HetSleepBleControlApi.getInstance().getHistroyData( new IHetCallback() {

                    @Override
                    public void onSuccess(int i, String s) {
                        hideDialog();
                        if (!StringUtils.isNull(s)){
                            List<SleepDataModel> listdata = GsonUtil.getGsonInstance()
                                    .fromJson(s, new TypeToken<List<SleepDataModel>>() {
                                    }.getType());
                            boolean haveData = false;//是否有数据
                            if (listdata != null && listdata.size() > 0) {
//                                for (int i = 0; i < listdata.size(); i++) {
//                                    SleepDataModel sleepDataModel = listdata.get(i);
//                                    if (sleepDataModel != null && sleepDataModel.getSleepStatusList() != null && sleepDataModel.getSleepStatusList().size() > 0) {
//                                        setDataView(sleepDataModel);
//                                        PromptUtil.showToast(mSelfActivity, getResources().getString(R.string.data_update_success));
//                                        BleDeviceHelper.setBleCacheData(mDeviceModel.getDeviceId(), sleepDataModel);
//                                        haveData = true;
//                                        break;
//                                    }
//                                }
                            }

                        }else {
                            showToast("没有历史数据，请先同步数据");
                        }

                    }

                    @Override
                    public void onFailed(int i, String s) {
                        hideDialog();
                        LogUtils.e(TAG, s);
                    }


                },deviceModel, startTime, endTime);

                break;
            case R.id.btn_real_data:
                showDialog("获取实时数据");
                HetSleepBleControlApi.getInstance().getRealData(new IHetCallback() {
                    @Override
                    public void onSuccess(int i, String s) {
                        hideDialog();
                        sendMsg(SHOW_REAL_DATA, s.getBytes());
                    }

                    @Override
                    public void onFailed(int i, String s) {
                        hideDialog();
                        showToast(s);
                    }


                },deviceModel);
                break;

        }
    }



    /**
     * 处理实时数据
     *
     * @param t
     */
    private void dealRealData(byte[] t) {
        if (t == null || t.length < 5) {
            return;
        }
        MattressModel mattressModel = new MattressModel();
        mattressModel.setHeartBeat(t[0]);
        mattressModel.setBreathe(t[1]);
        mattressModel.setSnore((byte) (t[2] & 0x01));
        mattressModel.setIsBed((byte) ((t[2] & 0x02) >> 1));
        mattressModel.setTurnOver(t[3]);
        mattressModel.setPower(t[4]);
        if (editReal != null) {
            editReal.setText("睡眠检测器实时数据:" + mattressModel.toString());
        }
    }

    private void sendMsg(int value, byte[] datas) {
        if (mStableHandler != null) {
            mStableHandler.obtainMessage(value, datas).sendToTarget();
        }

    }


}
