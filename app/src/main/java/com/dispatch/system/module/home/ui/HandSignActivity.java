package com.dispatch.system.module.home.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.dispatch.system.R;
import com.dispatch.system.base.BaseActivity;
import com.dispatch.system.entity.BaseBean;
import com.dispatch.system.entity.UploadSignBean;
import com.dispatch.system.http.ApiClient;
import com.dispatch.system.http.MyObserver;
import com.dispatch.system.module.home.widget.SignView;
import com.dispatch.system.utils.ViewSaveImageUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 手写签名页面
 * <p>
 *
 * @author chenjunxu
 * @date 2020/7/11
 */
public class HandSignActivity extends BaseActivity {

    @BindView(R.id.signView)
    SignView signView;
    private RxPermissions rxPermissions;

    @Override
    protected int bindLayout() {
        return R.layout.activity_hand_sign;
    }

    @Override
    protected void initView() {
        super.initView();
        trackingNumbers = getIntent().getStringArrayListExtra("trackingNumbers");
        rxPermissions = new RxPermissions(this);
    }

    @OnClick({R.id.tvCancel, R.id.tvClear, R.id.tvConfirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvCancel:
                finish();
                break;
            case R.id.tvClear:
                signView.clear();
                break;
            case R.id.tvConfirm:
                if (!signView.isHasSign()) {
                    ToastUtils.showLong("签名不能为空");
                    return;
                }
               /* batchDelivery("123");*/
                rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        .subscribe(grant -> {
                            upload();
                        });
                break;
        }
    }

    private void upload() {
        String path = ViewSaveImageUtils.viewSaveToImage(signView);
        if (TextUtils.isEmpty(path)) {
            ToastUtils.showLong("保存失败");
            return;
        }
        showLoading();
        RequestBody body = RequestBody.create(MediaType.parse("image/jpeg"), new File(path));
        ApiClient.getInstance().uploadAvatar(body, new MyObserver<UploadSignBean>() {
            @Override
            protected void getDisposable(Disposable d) {
            }

            @Override
            protected void onSuccess(UploadSignBean pickUpDetailBean) {
                batchDelivery(pickUpDetailBean.getData().getCode());
            }

            @Override
            protected void onError(String msg) {
                hideLoading();
            }
        });
    }

    private void batchDelivery(String code) {
        ApiClient.getInstance().batchDeliverySign(trackingNumbers, code, new MyObserver<BaseBean>() {
            @Override
            protected void getDisposable(Disposable d) {

            }

            @Override
            protected void onSuccess(BaseBean listBean) {
                hideLoading();
                ToastUtils.showLong("签收成功！");
                setResult(RESULT_OK);
                finish();
            }

            @Override
            protected void onError(String msg) {
                hideLoading();
            }
        });
    }

    private ArrayList<String> trackingNumbers;

    public static void move(Activity context, ArrayList<String> trackingNumbers) {
        Intent intent = new Intent(context, HandSignActivity.class);
        intent.putStringArrayListExtra("trackingNumbers", trackingNumbers);
        context.startActivityForResult(intent, 0);
    }
}
