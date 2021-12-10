package com.dispatch.system.module.home.ui;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.dispatch.system.R;
import com.dispatch.system.base.BaseActivity;
import com.dispatch.system.entity.BaseBean;
import com.dispatch.system.http.ApiClient;
import com.dispatch.system.http.MyObserver;
import com.dispatch.system.module.pick.PickScanCheckResultActivity;
import com.dispatch.system.module.pick.PickScanSubmitResultActivity;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;
import io.reactivex.disposables.Disposable;

import static cn.bingoogolapple.qrcode.core.BarcodeType.ONE_DIMENSION;

/**
 * 揽件扫描
 * <p>
 *
 * @author chenjunxu
 * @date 2020/7/5
 */
public class PickUpScanActivity extends BaseActivity implements QRCodeView.Delegate {

    @BindView(R.id.etInputExpressNum)
    EditText etInputExpressNum;
    @BindView(R.id.mZXingView)
    ZXingView mZXingView;
    @BindView(R.id.tvSwitchStatus)
    TextView tvSwitchStatus;
    @BindView(R.id.tvTitle)
    TextView tvTitle;

    private PICK_UP_TYPE type = PICK_UP_TYPE.TYPE_SUBMIT;
    private boolean isOpen = false;
    @Override
    protected int bindLayout() {
        return R.layout.activity_pick_up_scan;
    }

    @Override
    protected void initView() {
        super.initView();
        mZXingView.setDelegate(this);
        mZXingView.setType(ONE_DIMENSION, null);
        toggleType();
    }

    @OnClick({R.id.ivBack, R.id.tvConfirm, R.id.tvSwitchStatus, R.id.ivHandler})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.tvConfirm:
                String expressNum = etInputExpressNum.getText().toString();
                if (TextUtils.isEmpty(expressNum)) {
                    ToastUtils.showLong("请输入快递单号");
                    return;
                }
                confirm(expressNum);
                break;
            case R.id.tvSwitchStatus:
                toggleType();
                break;
            case R.id.ivHandler:
                if(isOpen){
                    mZXingView.closeFlashlight();
                    isOpen = false;
                }else{
                    mZXingView.openFlashlight();
                    isOpen = true;
                }
                break;
        }
    }

    private void confirm(String expressNum) {
        if (type == PICK_UP_TYPE.TYPE_CHECK) {
            // 核销揽件
            isRequest = true;
            showLoading();
            ApiClient.getInstance().verifyReceiveRecord(expressNum,
                    new MyObserver<BaseBean>() {
                        @Override
                        protected void getDisposable(Disposable d) {
                        }

                        @Override
                        protected void onSuccess(BaseBean bean) {
                            hideLoading();
                            isRequest = false;
                            PickScanCheckResultActivity.move(PickUpScanActivity.this, expressNum);
                        }

                        @Override
                        protected void onError(String msg) {
                            hideLoading();
                            if (mZXingView != null && !isFinishing()) {
                                mZXingView.startSpot();
                            }
                            isRequest = false;
                        }
                    });
        } else {
            isRequest = false;
            // 添加自揽件
            PickScanSubmitResultActivity.move(this, expressNum);
        }
    }

    private void toggleType() {
        if (type == PICK_UP_TYPE.TYPE_CHECK) {
            // 切换到提交
            type = PICK_UP_TYPE.TYPE_SUBMIT;
            tvTitle.setText("自揽件提交");
            tvSwitchStatus.setText("切换揽件核销");
            tvSwitchStatus.setOnClickListener(v -> {
                ToastUtils.showLong("切换到“揽件核销”");
                toggleType();
            });
        } else {
            type = PICK_UP_TYPE.TYPE_CHECK;
            tvTitle.setText("揽件核销");
            tvSwitchStatus.setText("切换自揽件提交");
            tvSwitchStatus.setOnClickListener(v -> {
                ToastUtils.showLong("切换到“自揽件提交”");
                toggleType();
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mZXingView.startCamera(); // 打开后置摄像头开始预览，但是并未开始识别
        mZXingView.startSpotAndShowRect(); // 显示扫描框，并开始识别
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
//        mZXingView.startSpot(); // 开始识别
        ToastUtils.showLong("扫描成功");
        if (isRequest) {
            return;
        }
        confirm(result);
    }

    private boolean isRequest = false;

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
    }

    @Override
    protected void onPause() {
        super.onPause();
        mZXingView.closeFlashlight();
        isOpen = false;
        mZXingView.stopCamera(); // 关闭摄像头预览，并且隐藏扫描框
    }

    @Override
    protected void onStop() {
        super.onStop();
        mZXingView.stopCamera(); // 关闭摄像头预览，并且隐藏扫描框
    }

    @Override
    protected void onDestroy() {
        mZXingView.closeFlashlight();
        isOpen = false;
        mZXingView.onDestroy(); // 销毁二维码扫描控件
        super.onDestroy();
    }

    private enum PICK_UP_TYPE {
        TYPE_CHECK,
        TYPE_SUBMIT
    }
}
