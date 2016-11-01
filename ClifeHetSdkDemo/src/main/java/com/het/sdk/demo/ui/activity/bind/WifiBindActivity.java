package com.het.sdk.demo.ui.activity.bind;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.het.open.lib.model.DeviceModel;
import com.het.open.lib.utils.StringUtils;
import com.het.sdk.demo.R;
import com.het.sdk.demo.model.WifiInfoModel;
import com.het.sdk.demo.ui.activity.BaseActivity;
import com.het.sdk.demo.utils.Constants;


/**
 * 设置wifi信息页面
 */
public class WifiBindActivity extends BaseActivity implements View.OnClickListener {

    private String ssid;
    private EditText editTextPass;
    private TextView tvwWifiName;
    private final int GET_IP_DATA = 0x01;
    private Button btnBind;
    private Context mContext;
    private WifiInfoModel mWifiInfoModel;
    private DeviceModel deviceModel;
    private Button mBtnBind;
    private ImageView mImvDeviceType;
    private LinearLayout mRlTwo;
    private TextView mTvWifiName;
    private RelativeLayout mRelName;
    private EditText mEdtWifiPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_wifi);
        deviceModel = (DeviceModel) getIntent().getSerializableExtra(Constants.DEVICE_MODEL);
        this.mContext = this;
        initView();

    }

    private void initView() {

        getSupportActionBar().setTitle("设置WIFI密码");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tvwWifiName = (TextView) findViewById(R.id.tv_wifi_name);
        tvwWifiName.setOnClickListener(this);
        btnBind = (Button) findViewById(R.id.btn_bind);
        btnBind.setOnClickListener(this);
        mBtnBind = (Button) findViewById(R.id.btn_bind);
        mBtnBind.setOnClickListener(this);
        mImvDeviceType = (ImageView) findViewById(R.id.imv_device_type);
        mImvDeviceType.setOnClickListener(this);
        mRlTwo = (LinearLayout) findViewById(R.id.rl_two);
        mRlTwo.setOnClickListener(this);
        mTvWifiName = (TextView) findViewById(R.id.tv_wifi_name);
        mTvWifiName.setOnClickListener(this);
        mRelName = (RelativeLayout) findViewById(R.id.rel_name);
        mRelName.setOnClickListener(this);
        mEdtWifiPassword = (EditText) findViewById(R.id.edt_wifi_password);
        mEdtWifiPassword .setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        initWifiData();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    private void initWifiData() {
        ssid = getSSid(mContext);
        mWifiInfoModel = new WifiInfoModel();
        if (tvwWifiName != null) {
            tvwWifiName.setText(ssid);
            mWifiInfoModel.setSsid(ssid);
        }


    }


    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.rel_name:
                toWifiManage();
                break;
            case R.id.btn_bind:
                toNextStep();
                break;
            default:
                break;

        }
    }

    private void toWifiManage() {
        Intent intent = new Intent();
        intent.setAction("android.net.wifi.PICK_WIFI_NETWORK");
        intent.putExtra("extra_prefs_show_button_bar", true);
        intent.putExtra("wifi_enable_next_on_connect", true);
        startActivity(intent);
    }

    private void toNextStep() {
        String pass = mEdtWifiPassword.getText().toString();
        mWifiInfoModel.setPassword(pass);
        if (StringUtils.isNull(pass)) {
            showToast("请输入WIFI密码");
        } else {
            if (deviceModel != null) {
                Intent to = new Intent(this,
                        SmartLinkConfigActivity.class);
                to.putExtra(Constants.WIFI_IFFO_MODEL, mWifiInfoModel);
                to.putExtra(Constants.DEVICE_MODEL, deviceModel);
                startActivity(to);
            }

        }
    }


    public static String getSSid(Context ctx) {
        WifiManager mWifiManager = (WifiManager) ctx
                .getSystemService(Context.WIFI_SERVICE);
        // 用来获取当前已连接上的wifi的信息
        if (mWifiManager == null) {
            return "";
        }
        WifiInfo mWifiInfo = mWifiManager.getConnectionInfo();
        if (mWifiInfo == null) {
            return "";
        }
        if (mWifiInfo.getSSID() == null) {
            return "";
        }

        String ssid = mWifiInfo.getSSID();
        // int currentapiVersion=android.os.Build.VERSION.SDK_INT;
        // 16之后的版本 取ssid时会自动带“”. 汉方模块要去掉
        if (Build.VERSION.SDK_INT > 16) {
            ssid = ssid.substring(1, ssid.length() - 1);
        }
        return ssid;
    }


    private void submit() {
        // validate
        String password = mEdtWifiPassword.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "请输入WIFI密码", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something


    }
}
