package com.het.sdk.demo.ui.activity.bind;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.reflect.TypeToken;
import com.het.open.lib.api.HetBleBindApi;
import com.het.open.lib.callback.IBleBind;
import com.het.open.lib.model.DeviceModel;
import com.het.open.lib.utils.GsonUtil;
import com.het.open.lib.utils.LogUtils;
import com.het.sdk.demo.R;
import com.het.sdk.demo.adapter.AdapterBleDeviceList;
import com.het.sdk.demo.ui.activity.BaseActivity;
import com.het.sdk.demo.utils.Constants;
import com.het.sdk.demo.utils.HandlerUtil;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * 蓝牙扫描设备界面
 */
public class BleBindActivity extends BaseActivity implements
        View.OnClickListener {
    private static final String TAG = "蓝牙扫描设备界面";
    private ImageView bind_circle_iv;
    private Animation animation;
    private RelativeLayout search_rl;
    private TextView point;
    private TextView tvwTip;
    private final int UPDATE_ADAPTER= 0x01;
    private final int CONNECT_DEVICE=0x02;
    private final int STOP_ANIM=0x03;
    private List<DeviceModel> deviceModels=new ArrayList<>();
    private RelativeLayout relStopBind;
    private ShimmerFrameLayout container;
    private DeviceModel deviceModel;
    private ListView listViewDevice;
    private AdapterBleDeviceList adapterDeviceList;
    private Context mContext;

    private HandlerUtil.MessageListener mMessageListener = new HandlerUtil.MessageListener() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {

                case UPDATE_ADAPTER:
                    if (search_rl!=null){
                        search_rl.setVisibility(View.GONE);
                    }
                    listViewDevice.setVisibility(View.VISIBLE);

                    if (adapterDeviceList != null) {
                        adapterDeviceList.notifyDataSetChanged();
                    }
                    break;
                case CONNECT_DEVICE:

                    break;
                case STOP_ANIM:
                    stopAnim();
                    break;
                default:
                    break;
            }

        }
    };

    private HandlerUtil.StaticHandler mStableHandler = new HandlerUtil.StaticHandler(
            mMessageListener);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_ble_bind);
        this.mContext=this;
        initView();
        initData();


    }

    private void initData() {
        deviceModel= (DeviceModel) getIntent().getSerializableExtra(Constants.DEVICE_MODEL);
        getSupportActionBar().setTitle(deviceModel.getDeviceName()+"绑定");
        int ret=HetBleBindApi.getInstance().init();
        if (ret==0){
            HetBleBindApi.getInstance().startBind(deviceModel, new IBleBind() {


                @Override
                public void onScanDevices(String devices, String msg) {
                       LogUtils.d(TAG,devices);
                    Type type = new TypeToken<List<DeviceModel>>() {
                    }.getType();
                    List<DeviceModel> models = GsonUtil.getGsonInstance().fromJson(devices, type);
                    if (models != null && models.size() > 0) {
                        deviceModels.clear();
                        deviceModels.addAll(models);
                        sendMsg(UPDATE_ADAPTER);
                    } else {
                        showToast("未绑定任何设备");
                    }
                }

                @Override
                public void onFailed(int errId, String msg) {
                    LogUtils.e(TAG,errId+msg);
                    showStatus(errId+msg);
                    sendMsg(STOP_ANIM);
                }

                @Override
                public void onSuccess(DeviceModel deviceModel) {
                    String msg="成功绑定设备["+deviceModel.getMacAddress()+"]";
                    showStatus(msg);
                    sendMsg(STOP_ANIM);
                }
            });
        }else if (ret==1){
            sendMsg(STOP_ANIM);
            showToast("请打开蓝牙");
        }else if (ret==2){
            sendMsg(STOP_ANIM);
            showToast("该手机不支持ble蓝牙功能");
        }


    }

    private void goToFailedUi(String msg){

        Intent intent=new Intent(this,SmartLinkScanFailActivity.class);
        intent.putExtra(Constants.BIND_ERROR_MSG,msg);
        startActivity(intent);
        finish();
    }

    private void showStatus(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                tvwTip.setText(msg);
                container.removeView(tvwTip);
                container.addView(tvwTip);

            }
        });

    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if (bind_circle_iv!=null){
            bind_circle_iv.startAnimation(animation);
        }
    }

    /**
     * 停止动画
     */
    private void stopAnim(){
        setPoint(0);
        if (bind_circle_iv!=null){
            bind_circle_iv.clearAnimation();
        }
    }

    private void initView() {
        getSupportActionBar().setTitle("扫描绑定设备");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        container =
                (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container);
        container.startShimmerAnimation();
        tvwTip = (TextView) findViewById(R.id.tv_tips);
        relStopBind = (RelativeLayout) findViewById(R.id.rel_cancle);
        relStopBind.setOnClickListener(this);
        search_rl = (RelativeLayout) findViewById(R.id.search_rl);
        bind_circle_iv = (ImageView) findViewById(R.id.bind_circle_iv);
        point = (TextView) findViewById(R.id.point);
        search_rl.setVisibility(View.VISIBLE);
        animation= AnimationUtils.loadAnimation(this, R.anim.lock_round);
        bind_circle_iv=(ImageView)findViewById(R.id.bind_circle_iv);
        listViewDevice=(ListView)findViewById(R.id.lvw_devices);
        adapterDeviceList=new AdapterBleDeviceList(deviceModels,mContext) ;
        listViewDevice.setAdapter(adapterDeviceList);
        listViewDevice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DeviceModel deviceModel=deviceModels.get(position);
                if (deviceModel!=null){
                    if (search_rl!=null){
                        search_rl.setVisibility(View.VISIBLE);
                    }
                    listViewDevice.setVisibility(View.GONE);
                    showStatus("绑定设备中");
                    HetBleBindApi.getInstance().connect(deviceModel);

                }
            }
        });
    }


    private void setPoint(final int value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                point.setText(value + "");
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopAnim();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rel_cancle:
                finish();
                break;
        }
    }


    private void sendMsg(int value) {
        if (mStableHandler != null) {
            mStableHandler.obtainMessage(value).sendToTarget();
        }

    }




}
