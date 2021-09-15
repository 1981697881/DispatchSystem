package com.dispatch.system.module;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Handler;
import android.text.TextUtils;

import com.blankj.utilcode.util.SPUtils;
import com.dispatch.system.R;
import com.dispatch.system.base.BaseActivity;
import com.dispatch.system.constants.SPConstants;
import com.dispatch.system.module.login.LoginActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;

/**
 * 欢迎页面
 *
 * @author chenxuxu
 * @date 2020/8/1
 */
public class WelcomeActivity extends BaseActivity {

    @Override
    protected int bindLayout() {
        return R.layout.activity_welcome;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initView() {
        super.initView();
        new RxPermissions(this).request(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(grant -> {
            if (grant) {
                init();
            } else {
                finish();
            }
        });
    }

    /**
     * 初始化
     */
    private void init() {

        // 一秒后跳转页面
        handler.postDelayed(() -> {
            // 判断是否已经登录
//                String sessionId = (String) SPUtils.get(BaseApplication.getApp(), SPConstants.SESSION_ID, "");

            String sessionId = SPUtils.getInstance().getString(SPConstants.SESSION_ID, null);
            if (!TextUtils.isEmpty(sessionId)) {
                // 跳转到主页面
                MainActivity.move(WelcomeActivity.this);
            } else {
                LoginActivity.move(WelcomeActivity.this);
            }
            WelcomeActivity.this.finish();
        }, 1200);
    }


    private Handler handler = new Handler();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

}
