package com.dispatch.system.module.pick;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.ToastUtils;
import com.dispatch.system.R;
import com.dispatch.system.base.BaseActivity;
import com.dispatch.system.entity.BaseBean;
import com.dispatch.system.http.ApiClient;
import com.dispatch.system.http.MyObserver;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;

/**
 * 我的寄件-确认取件
 * <p>
 *
 * @author chenjunxu
 * @date 2020/7/28
 */
public class PickInputReceiverMessageActivity extends BaseActivity {
    @BindView(R.id.etExpressNum)
    EditText etExpressNum;
    @BindView(R.id.etExpressPrice)
    EditText etExpressPrice;
    private String code;

    @Override
    protected int bindLayout() {
        return R.layout.dialog_input_receiver_message;
    }

    @Override
    protected void initView() {
        super.initView();
        code = getIntent().getStringExtra("code");
    }

    private void request() {
        String expressNum = etExpressNum.getText().toString();
        if (TextUtils.isEmpty(expressNum)) {
            ToastUtils.showLong("请输入运单号");
            return;
        }
        String expressPrice = etExpressPrice.getText().toString();
        if (TextUtils.isEmpty(expressPrice)) {
            ToastUtils.showLong("请输入寄件费用");
            return;
        }
        showLoading();
        ApiClient.getInstance().confirmReceiver(code, expressNum, expressPrice,
                new MyObserver<BaseBean>() {
                    @Override
                    protected void getDisposable(Disposable d) {
                    }

                    @Override
                    protected void onSuccess(BaseBean baseBean) {
                        hideLoading();
                        ToastUtils.showLong("确认揽件成功！");
                        setResult(RESULT_OK);
                        finish();
                    }

                    @Override
                    protected void onError(String msg) {
                        hideLoading();
                    }
                });
    }

    public static void move(Activity context, String code) {
        Intent intent = new Intent(context, PickInputReceiverMessageActivity.class);
        intent.putExtra("code", code);
        context.startActivityForResult(intent, 0);
    }

    @OnClick({R.id.ivClose, R.id.tvCancel, R.id.tvConfirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivClose:
            case R.id.tvCancel:
                finish();
                break;
            case R.id.tvConfirm:
                request();
                break;
        }
    }
}
