package com.het.sdk.demo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.het.open.lib.api.HetAuthApi;
import com.het.open.lib.api.HetSdk;
import com.het.open.lib.api.HetUserApi;
import com.het.open.lib.callback.AuthCallback;
import com.het.open.lib.callback.IHetCallback;
import com.het.sdk.demo.R;


/**
 * 授权fragment
 * Created by xuchao on 2016/6/30.
 */

public class AuthFragment extends BaseFragment implements View.OnClickListener {



    private View rootView;
    private Button mBtnAuth;//授权登录
    private Button mBtnGetUserMess; //获取用户信息


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_auth, container,
                    false);
            initView(rootView);
        }

        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        mBtnAuth = (Button) rootView.findViewById(R.id.btn_auth);
        mBtnAuth.setOnClickListener(this);
        mBtnGetUserMess = (Button) rootView.findViewById(R.id.btn_get_user_mess);
        mBtnGetUserMess.setOnClickListener(this);
    }



    private void auth(){
        HetAuthApi.getInstance().authorize("远大",new AuthCallback() {
            @Override
            public void onSuccess(int code, String msg) {
                showToast(msg);
            }

            @Override
            public void onFailed(int code, String msg) {
                showToast(msg);


            }
        });

    }



    /**
     * 获取用户信息
     */
    private void getUserMess(){
       if (HetSdk.getInstance().isAuthLogin()){
            HetUserApi.getInstance().getUserMess(new IHetCallback() {
                @Override
                public void onSuccess(int code, String msg) {
                    if (code==0){
                        showToast("用户信息" +
                                msg);
                    }
                }

                @Override
                public void onFailed(int code, String msg) {
                    showToast(code+msg);

                }
            });
        }else {
            showToast("登录后获取用户信息");
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_auth:
               auth();
                break;

            case R.id.btn_get_user_mess:
                getUserMess();
                break;
        }
    }
}
