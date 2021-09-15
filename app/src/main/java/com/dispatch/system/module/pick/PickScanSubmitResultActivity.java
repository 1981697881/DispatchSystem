package com.dispatch.system.module.pick;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.dispatch.system.R;
import com.dispatch.system.base.BaseActivity;
import com.dispatch.system.common.ExpressHelper;
import com.dispatch.system.entity.BaseBean;
import com.dispatch.system.http.ApiClient;
import com.dispatch.system.http.MyObserver;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;

public class PickScanSubmitResultActivity extends BaseActivity {

    @BindView(R.id.tvExpressName)
    TextView tvExpressName;
    @BindView(R.id.tvExpressNum)
    TextView tvExpressNum;
    @BindView(R.id.etMoney)
    EditText etMoney;
    private String expressNum;

    @Override
    protected int bindLayout() {
        return R.layout.activity_scan_pick_up_submit_result;
    }

    @Override
    protected void initView() {
        super.initView();
        expressNum = getIntent().getStringExtra("expressNum");
        tvExpressName.setText(ExpressHelper.getExpressName(expressNum));
        tvExpressNum.setText(expressNum);
    }

    public static void move(Context context, String expressNum) {
        Intent intent = new Intent(context, PickScanSubmitResultActivity.class);
        intent.putExtra("expressNum", expressNum);
        context.startActivity(intent);
    }

    @OnClick(R.id.tvCancel)
    public void onViewClicked() {
        finish();
    }

    @OnClick(R.id.tvSubmit)
    public void submit() {
        String money = etMoney.getText().toString();
        if (TextUtils.isEmpty(money)) {
            ToastUtils.showLong("请输入金额");
            return;
        }
        showLoading();
        ApiClient.getInstance().addSelfReceiving(expressNum, ExpressHelper.getExpressKey(expressNum),
                money, new MyObserver<BaseBean>() {
            @Override
            protected void getDisposable(Disposable d) {
            }

            @Override
            protected void onSuccess(BaseBean deliveryDetailBean) {
                hideLoading();
                ToastUtils.showLong("添加自揽件成功！");
                finish();
            }

            @Override
            protected void onError(String msg) {
                hideLoading();
            }
        });
    }
}
