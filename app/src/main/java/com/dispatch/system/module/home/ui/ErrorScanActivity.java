package com.dispatch.system.module.home.ui;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.ToastUtils;
import com.dispatch.system.R;
import com.dispatch.system.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

import static cn.bingoogolapple.qrcode.core.BarcodeType.ONE_DIMENSION;

/**
 * 异常件扫描
 * <p>
 *
 * @author chenjunxu
 * @date 2020/7/5
 */
public class ErrorScanActivity extends BaseActivity implements QRCodeView.Delegate {

   /* @BindView(R.id.etInputExpressNum)
    EditText etInputExpressNum;*/
    @BindView(R.id.mZXingView)
    ZXingView mZXingView;
    private boolean isOpen = false;

    @Override
    protected int bindLayout() {
        return R.layout.activity_error_scan;
    }

    @Override
    protected void initView() {
        super.initView();
        mZXingView.setDelegate(this);
        mZXingView.setType(ONE_DIMENSION, null);
    }

    @OnClick({R.id.ivBack, /*R.id.tvConfirm,*/ R.id.ivHandler})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
           /* case R.id.tvConfirm:
                searchExpress();
                break;*/
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

    /**
     * 确认快递单号
     */
   /* private void searchExpress() {
        String expressNum = etInputExpressNum.getText().toString();
        if (TextUtils.isEmpty(expressNum)) {
            ToastUtils.showLong("请输入快递单号");
            return;
        }
        etInputExpressNum.setText(null);
        ScanErrorResultActivity.move(this, expressNum);
    }*/

    @Override
    protected void onStart() {
        super.onStart();
        mZXingView.startCamera(); // 打开后置摄像头开始预览，但是并未开始识别
    }

    @Override
    protected void onResume() {
        super.onResume();
        mZXingView.startSpotAndShowRect(); // 显示扫描框，并开始识别
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
//        mZXingView.startSpot(); // 开始识别
        /*ScanErrorResultActivity.move(this, result);
        ToastUtils.showLong(result);*/
        BuildingDetailActivity.scan(this, result);
    }

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
    protected void onDestroy() {
        mZXingView.closeFlashlight();
        isOpen = false;
        mZXingView.onDestroy(); // 销毁二维码扫描控件
        super.onDestroy();
    }
}
