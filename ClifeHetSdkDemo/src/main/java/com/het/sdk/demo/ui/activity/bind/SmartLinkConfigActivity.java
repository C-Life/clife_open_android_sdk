package com.het.sdk.demo.ui.activity.bind;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.het.open.lib.api.HetWifiBindApi;
import com.het.open.lib.callback.HetWifiBindState;
import com.het.open.lib.callback.IWifiBind;
import com.het.open.lib.model.DeviceModel;
import com.het.sdk.demo.R;
import com.het.sdk.demo.model.WifiInfoModel;
import com.het.sdk.demo.ui.activity.BaseActivity;
import com.het.sdk.demo.utils.Constants;


/**
 * 扫描设备界面
 */
public class SmartLinkConfigActivity extends BaseActivity implements
        View.OnClickListener {
    private static final String TAG = "扫描设备界面";
    private ImageView bind_circle_iv;
    private Animation animation;
    private RelativeLayout search_rl;
    private TextView point;
    private TextView tvwTip;
    private WifiInfoModel wifiInfoModel;
    private ShimmerFrameLayout container;
    private DeviceModel deviceModel;
    private Button mBtnCancle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_smart_bind);
        initView();
        initData();


    }

    private void initData() {

        wifiInfoModel = (WifiInfoModel) getIntent().getSerializableExtra(Constants.WIFI_IFFO_MODEL);
        deviceModel = (DeviceModel) getIntent().getSerializableExtra(Constants.DEVICE_MODEL);
        getSupportActionBar().setTitle(deviceModel.getDeviceName() + "绑定");
        HetWifiBindApi.getInstance().startBind(wifiInfoModel.getPassword(), deviceModel, new IWifiBind() {


            @Override
            public void onStatus(HetWifiBindState hetWifiBindState, String msg) {
                showStatus(msg);
            }

            @Override
            public void onFailed(int errId, String msg) {
                stopAnim();
                showStatus(msg);
                goToFailedUi(errId + msg);
            }


            @Override
            public void onSuccess(DeviceModel deviceModel) {
                String msg = "成功绑定设备[" + deviceModel.getMacAddress() + "]";
                showStatus(msg);
                stopAnim();
            }

            @Override
            public void onProgress(int type, int value) {
                setPoint(value);
            }


        });
    }

    private void goToFailedUi(String msg) {
        Intent intent = new Intent(this, SmartLinkScanFailActivity.class);
        intent.putExtra(Constants.BIND_ERROR_MSG, msg);
        intent.putExtra(Constants.DEVICE_MODEL, deviceModel);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
        if (bind_circle_iv != null) {
            bind_circle_iv.startAnimation(animation);
        }
    }

    /**
     * 停止动画
     */
    private void stopAnim() {
        setPoint(0);
        if (bind_circle_iv != null) {
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
        search_rl = (RelativeLayout) findViewById(R.id.search_rl);
        bind_circle_iv = (ImageView) findViewById(R.id.bind_circle_iv);
        point = (TextView) findViewById(R.id.point);
        search_rl.setVisibility(View.VISIBLE);
        animation = AnimationUtils.loadAnimation(this, R.anim.lock_round);
        bind_circle_iv = (ImageView) findViewById(R.id.bind_circle_iv);
        mBtnCancle = (Button) findViewById(R.id.btn_cancle);
        mBtnCancle.setOnClickListener(this);
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
        HetWifiBindApi.getInstance().cancle();
        stopAnim();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancle:
                finish();
                break;
        }
    }


}
