package com.dispatch.system.module.home.ui;


import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.dispatch.system.R;
import com.dispatch.system.base.BaseActivity;
import com.dispatch.system.common.ExpressHelper;
import com.dispatch.system.constants.IntentConstants;
import com.dispatch.system.entity.BaseBean;
import com.dispatch.system.entity.DeliveryDetailBean;
import com.dispatch.system.entity.ExceptionTemplateBean;
import com.dispatch.system.http.ApiClient;
import com.dispatch.system.http.MyObserver;
import com.dispatch.system.module.home.adapter.ScanExceptionAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;

/**
 * 扫描异常件结果页面
 * <p>
 *
 * @author chenjunxu
 * @date 2020/7/11
 */
public class ScanErrorResultActivity extends BaseActivity {

    @BindView(R.id.tvExpressName)
    TextView tvExpressName;
    @BindView(R.id.tvExpressNum)
    TextView tvExpressNum;
    @BindView(R.id.rvData)
    RecyclerView rvData;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvAddress)
    TextView tvAddress;
    private String expressNum;
    private ScanExceptionAdapter adapter;
    private List<ExceptionTemplateBean.DataBean.ExceptionTemplatesBean> dataList = new ArrayList<>();

    private ExceptionTemplateBean.DataBean.ExceptionTemplatesBean selectBean;

    @Override
    protected int bindLayout() {
        return R.layout.activity_scan_error_result;
    }

    @Override
    protected void initView() {
        super.initView();
        expressNum = getIntent().getStringExtra("expressNum");
        String expressName = ExpressHelper.getExpressName(expressNum);
        tvExpressNum.setText(expressNum);
        tvExpressName.setText(expressName);
        if ("无法识别快递公司".equals(expressName)) {
            tvExpressName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ExpressDialogActivity.moveForResult(ScanErrorResultActivity.this, 111);
                }
            });
        }

        rvData.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ScanExceptionAdapter(this, dataList, bean -> {
            selectBean = bean;
        });
        rvData.setAdapter(adapter);

        getException();
        getDetail();
    }

    private void getException() {
        showLoading();
        ApiClient.getInstance().getException(new MyObserver<ExceptionTemplateBean>() {
            @Override
            protected void getDisposable(Disposable d) {

            }

            @Override
            protected void onSuccess(ExceptionTemplateBean exceptionTemplateBean) {
                dataList.clear();
                dataList.addAll(exceptionTemplateBean.getData().getExceptionTemplates());
                if (!dataList.isEmpty()) {
                    dataList.get(0).setCheck(true);
                    selectBean = dataList.get(0);
                }
                adapter.notifyDataSetChanged();
                hideLoading();
            }

            @Override
            protected void onError(String msg) {
                hideLoading();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111 && resultCode == RESULT_OK && data != null) {
            String business = data.getStringExtra(IntentConstants.EXPRESS_NAME);
            tvExpressName.setText(business);
        }
    }

    private void getDetail() {
        showLoading();
        ApiClient.getInstance().deliveryDetail(expressNum, null, new MyObserver<DeliveryDetailBean>() {
            @Override
            protected void getDisposable(Disposable d) {

            }

            @Override
            protected void onSuccess(DeliveryDetailBean baseBean) {
                hideLoading();
                DeliveryDetailBean.DataBean.DeliveryRecordBean record = baseBean.getData().getDeliveryRecord();
                String phone = record.getDeliverymanMobileNumber();
                tvName.setText(String.format("%s %s", record.getAddressee(), phone));
                tvAddress.setText(record.getBuildingName() + record.getBuildingCode());
            }

            @Override
            protected void onError(String msg) {
                hideLoading();
            }
        });
    }

    private void confirmErrorGoods() {
        if (selectBean == null) {
            return;
        }
        showLoading();
        ApiClient.getInstance().addDeliveryException(expressNum, selectBean.getCode(), new MyObserver<BaseBean>() {
            @Override
            protected void getDisposable(Disposable d) {

            }

            @Override
            protected void onSuccess(BaseBean exceptionTemplateBean) {
                hideLoading();
                ToastUtils.showLong("添加异常成功！");
                finish();
            }

            @Override
            protected void onError(String msg) {
                hideLoading();
            }
        });
    }

    public static void move(Context context, String expressNum) {
        Intent intent = new Intent(context, ScanErrorResultActivity.class);
        intent.putExtra("expressNum", expressNum);
        context.startActivity(intent);
    }

    @OnClick({R.id.tvCancel, R.id.tvConfirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvCancel:
                finish();
                break;
            case R.id.tvConfirm:
                // 确定异常订单
                confirmErrorGoods();
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
        // 部分机型退出动画会有影响，导致页面闪一下
        overridePendingTransition(0, 0);
    }
}