package com.het.sdk.demo.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import com.het.open.lib.api.HetSdk;
import com.het.sdk.demo.R;
import com.het.sdk.demo.ui.fragment.AuthFragment;
import com.het.sdk.demo.ui.fragment.BindFragment;
import com.het.sdk.demo.ui.fragment.CommonFragment;
import com.het.sdk.demo.ui.fragment.DeviceFragment;


/**
 * 和而泰开放平台主界面
 */
public class MainActivity extends BaseActivity {

    private RadioButton rbAuth;
    private RadioButton rbBind;
    private RadioButton rbDevice;
    private RadioButton rbCommon;
    private AuthFragment authFragment;
    private BindFragment bindFragment;
    private DeviceFragment deviceFragment;
    private CommonFragment commonFragment;
    private Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragment();
        initView();


    }


    private void initFragment() {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (authFragment == null) {
            authFragment = new AuthFragment();
        }
        ft.add(R.id.fragment_container, authFragment, "AuthFragment");
        ft.addToBackStack("AuthFragment");
        ft.commit();
        mFragment = authFragment;
    }


    private void initView() {
        rbAuth = (RadioButton) findViewById(R.id.rb_auth);
        rbAuth.setOnCheckedChangeListener(onCheckedChangeListener);
        rbCommon = (RadioButton) findViewById(R.id.rb_common);
        rbCommon.setOnCheckedChangeListener(onCheckedChangeListener);
        rbBind = (RadioButton) findViewById(R.id.rb_bind);
        rbBind.setOnCheckedChangeListener(onCheckedChangeListener);
        rbDevice = (RadioButton) findViewById(R.id.rb_device);
        rbDevice.setOnCheckedChangeListener(onCheckedChangeListener);


    }


    CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            // TODO Auto-generated method stub
            switch (buttonView.getId()) {
                case R.id.rb_auth:
                    if (isChecked) {
                        rbAuth.setChecked(true);
                        rbBind.setChecked(false);
                        rbCommon.setChecked(false);
                        rbDevice.setChecked(false);

                        if (authFragment == null) {
                            authFragment = new AuthFragment();
                        }
                        switchContent(authFragment);
                    }

                    break;
                case R.id.rb_bind:
                    if (isChecked) {
                        rbAuth.setChecked(false);
                        rbBind.setChecked(true);
                        rbCommon.setChecked(false);
                        rbDevice.setChecked(false);

                        if (bindFragment == null) {
                            bindFragment = new BindFragment();
                        }
                        switchContent(bindFragment);
                    }

                    break;
                case R.id.rb_device:
                    if (isChecked) {
                        rbAuth.setChecked(false);
                        rbBind.setChecked(false);
                        rbCommon.setChecked(false);
                        rbDevice.setChecked(true);
                        if (deviceFragment == null) {
                            deviceFragment = new DeviceFragment();
                        }
                        switchContent(deviceFragment);
                    }

                    break;
                case R.id.rb_common:
                    if (isChecked) {
                        rbAuth.setChecked(false);
                        rbBind.setChecked(false);
                        rbCommon.setChecked(true);
                        rbDevice.setChecked(false);
                        if (commonFragment == null) {
                            commonFragment = new CommonFragment();
                        }
                        switchContent(commonFragment);
                    }

                    break;

                default:
                    break;
            }

        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //释放sdk资源
        HetSdk.getInstance().destroy();
    }

    /**
     * 修改显示的fragment 不会重新加载
     **/
    public void switchContent(Fragment to) {
        if (mFragment != to) {
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction();
            if (!to.isAdded()) { // 先判断是否被add过
                transaction.hide(mFragment).add(R.id.fragment_container, to)
                        .commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(mFragment).show(to).commit(); // 隐藏当前的fragment，显示下一个

            }
            mFragment = to;
        }
    }


}
