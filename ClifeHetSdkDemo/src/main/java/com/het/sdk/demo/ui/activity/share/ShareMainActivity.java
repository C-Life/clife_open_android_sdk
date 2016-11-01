package com.het.sdk.demo.ui.activity.share;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.het.open.lib.api.HetDeviceShareApi;
import com.het.open.lib.callback.IHetCallback;
import com.het.open.lib.model.DeviceModel;
import com.het.open.lib.utils.StringUtils;
import com.het.sdk.demo.R;
import com.het.sdk.demo.ui.activity.BaseActivity;
import com.het.sdk.demo.utils.Constants;
import com.het.sdk.demo.utils.QrUtils;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2016, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * <p>描述：分享主界面</p>
 * 名称:  授权api<br>
 * 作者: 80010814<br>
 * 版本: 1.0<br>
 * 日期: 2016/3/10. 16:42<br>
 **/
public class ShareMainActivity extends BaseActivity implements View.OnClickListener {

    private Context mContext;
    private DeviceModel deviceModel;
    private EditText etInputPhone;
    private Button btnOkInvite;
    private ImageView ivQr;
    private Button btnDeviceShareApi;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_main);
        this.mContext = this;
        deviceModel = (DeviceModel) getIntent().getSerializableExtra(Constants.DEVICE_MODEL);
        initView();


    }


    /**
     * 生成二维码
     */
    private void showQr() {
        final String deviceId = deviceModel.getDeviceId();
        // Bitmap bitmapLogo=
        final Bitmap rawBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.clife_launcher);
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap = QrUtils.createQRImage(mContext, deviceId, rawBitmap);

                if (bitmap != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!rawBitmap.isRecycled()) {
                                rawBitmap.recycle();
                            }
                            if (ivQr != null) {
                                ivQr.setImageBitmap(bitmap);
                            }
                        }
                    });
                }
            }
        }).start();

    }


    private void initView() {
        getSupportActionBar().setTitle("设备分享");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        etInputPhone = (EditText) findViewById(R.id.et_input_phone);
        btnOkInvite = (Button) findViewById(R.id.btn_ok_invite);
        btnOkInvite.setOnClickListener(this);
        ivQr = (ImageView) findViewById(R.id.iv_qr);
        btnDeviceShareApi = (Button) findViewById(R.id.btn_device_share_api);
        btnDeviceShareApi.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_device_share_api:
                Intent intent = new Intent(ShareMainActivity.this, ShareDeviceApiActivity.class);
                intent.putExtra(Constants.DEVICE_MODEL,deviceModel);
                startActivity(intent);
                break;
            case R.id.btn_ok_invite:
                String phone = etInputPhone.getText().toString().trim();
                if (StringUtils.isNull(phone)) {
                    showToast("用户帐号不能为空");

                }else {
                    invite(phone);
                }

                break;

        }
    }

    /**
     *向指定用户发送邀请
     * @param phone
     */
    private void invite(final String phone) {

        String deviceId=deviceModel.getDeviceId();
        HetDeviceShareApi.getInstance().invert(new IHetCallback() {
            @Override
            public void onSuccess(int code, String msg) {
                   showToast("已成功邀请用户"+phone);
                   showQr();
            }

            @Override
            public void onFailed(int code, String msg) {

            }
        },deviceId,phone);

        // TODO validate success, do something


    }
}
