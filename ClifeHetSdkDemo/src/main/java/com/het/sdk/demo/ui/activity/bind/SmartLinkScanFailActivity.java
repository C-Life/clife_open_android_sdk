package com.het.sdk.demo.ui.activity.bind;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.het.open.lib.model.DeviceModel;
import com.het.open.lib.utils.StringUtils;
import com.het.sdk.demo.R;
import com.het.sdk.demo.ui.activity.BaseActivity;
import com.het.sdk.demo.utils.Constants;


/**
 * 绑定失败界面
 */
public class SmartLinkScanFailActivity extends BaseActivity {

    private Button btnReBind;
    private TextView tvwError;   //
    private String errMsg = "";//绑定异常信息
    private DeviceModel deviceModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_failed_layout);
        initView();
        initData();

    }

    private void initData() {
        deviceModel=(DeviceModel)getIntent().getSerializableExtra(Constants.DEVICE_MODEL) ;
        errMsg = getIntent().getStringExtra(Constants.BIND_ERROR_MSG);
        if (!StringUtils.isNull(errMsg)) {
            tvwError.setText("("+errMsg+")");
            tvwError.invalidate();
        }
    }


    private void initView() {
        getSupportActionBar().setTitle("绑定失败");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tvwError = (TextView) findViewById(R.id.error_msg);
        btnReBind = (Button) findViewById(R.id.btn_re_bind);
        btnReBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(SmartLinkScanFailActivity.this, WifiBindActivity.class);
                intent.putExtra(Constants.DEVICE_MODEL, deviceModel);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });


    }




}
