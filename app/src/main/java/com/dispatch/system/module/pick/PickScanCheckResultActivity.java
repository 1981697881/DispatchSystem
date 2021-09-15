package com.dispatch.system.module.pick;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.dispatch.system.R;
import com.dispatch.system.base.BaseActivity;
import com.dispatch.system.common.ExpressHelper;

import butterknife.BindView;
import butterknife.OnClick;

public class PickScanCheckResultActivity extends BaseActivity {

    @BindView(R.id.tvExpressName)
    TextView tvExpressName;
    @BindView(R.id.tvExpressNum)
    TextView tvExpressNum;

    @Override
    protected int bindLayout() {
        return R.layout.activity_pick_up_check_result;
    }

    @Override
    protected void initView() {
        super.initView();
        String expressNum = getIntent().getStringExtra("expressNum");
        tvExpressName.setText(ExpressHelper.getExpressName(expressNum));
        tvExpressNum.setText(expressNum);
    }

    public static void move(Context context, String expressNum) {
        Intent intent = new Intent(context, PickScanCheckResultActivity.class);
        intent.putExtra("expressNum", expressNum);
        context.startActivity(intent);
    }

    @OnClick(R.id.tvCancel)
    public void onViewClicked() {
        finish();
    }
}
