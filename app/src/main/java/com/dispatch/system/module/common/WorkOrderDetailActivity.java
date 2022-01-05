package com.dispatch.system.module.common;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.dispatch.system.R;
import com.dispatch.system.base.BaseActivity;
import com.dispatch.system.entity.PickUpDetailBean;
import com.dispatch.system.http.ApiClient;
import com.dispatch.system.http.MyObserver;
import com.dispatch.system.module.pick.PickInputReceiverMessageActivity;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;

/**
 * 工单详情
 */
public class WorkOrderDetailActivity extends BaseActivity {
    @BindView(R.id.clUserInfo)
    ConstraintLayout clUserInfo;
    @BindView(R.id.ivStatusCard)
    ImageView ivStatusCard;
    @BindView(R.id.tvStatus)
    TextView tvStatus;
    @BindView(R.id.tvPriceTip)
    TextView tvPriceTip;
    @BindView(R.id.tvPrice)
    TextView tvPrice;
    @BindView(R.id.viewDividerPrice)
    View viewDividerPrice;
    @BindView(R.id.tvCouponTip)
    TextView tvCouponTip;
    @BindView(R.id.tvCoupon)
    TextView tvCoupon;
    @BindView(R.id.viewDividerCoupon)
    View viewDividerCoupon;
    @BindView(R.id.tvTotalTip)
    TextView tvTotalTip;
    @BindView(R.id.tvTotal)
    TextView tvTotal;
    @BindView(R.id.viewDividerTotal)
    View viewDividerTotal;
    @BindView(R.id.tvExpressTip)
    TextView tvExpressTip;
    @BindView(R.id.tvExpressNum)
    TextView tvExpressNum;
    @BindView(R.id.viewDividerExpressNum)
    View viewDividerExpressNum;
    @BindView(R.id.tvConfirm)
    TextView tvConfirm;
    @BindView(R.id.ivBack)
    View ivBack;
    private String code;
    private String trackingNumber;

    private PickUpDetailBean.DataBean.ReceivingRecordBean bean;

    @Override
    protected int bindLayout() {
        return R.layout.activity_work_order_detail;
    }

    @Override
    protected void initView() {
        super.initView();
        code = getIntent().getStringExtra("code");
        trackingNumber = getIntent().getStringExtra("trackingNumber");
        ivBack.setVisibility(View.VISIBLE);
        requestData();
    }

    private void requestData() {
        showLoading();
        ApiClient.getInstance().getPickUpDetail(code, trackingNumber, new MyObserver<PickUpDetailBean>() {
            @Override
            protected void getDisposable(Disposable d) {
            }

            @Override
            protected void onSuccess(PickUpDetailBean pickUpDetailBean) {
                hideLoading();
                bean = pickUpDetailBean.getData().getReceivingRecord();
                tvConfirm.setVisibility(View.GONE);
                if ("EMPLOYEE_SELF".equals(bean.getType())) {
                    // 自揽件
                    clUserInfo.setVisibility(View.GONE);
                    tvStatus.setText("自揽件");
                    tvPriceTip.setVisibility(View.GONE);
                    viewDividerPrice.setVisibility(View.GONE);
                    tvPrice.setVisibility(View.GONE);
                    tvCouponTip.setVisibility(View.GONE);
                    tvCoupon.setVisibility(View.GONE);
                    tvTotalTip.setVisibility(View.VISIBLE);
                    viewDividerCoupon.setVisibility(View.GONE);
                    tvTotal.setVisibility(View.VISIBLE);
                    viewDividerTotal.setVisibility(View.VISIBLE);
                    tvTotal.setText(String.valueOf(bean.getAmount()));
                    tvExpressNum.setText(bean.getTrackingNumber());
                } else {
                    // 系统派件
                    if ("NOT_RECEIVED".equals(bean.getState())) {
                        // 状态：未揽件
                        // UI上显示“待上门取件”
                        tvStatus.setText("等待上门取件");
                        tvPriceTip.setVisibility(View.GONE);
                        viewDividerPrice.setVisibility(View.GONE);
                        tvPrice.setVisibility(View.GONE);
                        tvCouponTip.setVisibility(View.GONE);
                        tvCoupon.setVisibility(View.GONE);
                        viewDividerCoupon.setVisibility(View.GONE);
                        tvTotal.setVisibility(View.GONE);
                        tvTotalTip.setVisibility(View.GONE);
                        viewDividerTotal.setVisibility(View.GONE);
                        tvExpressTip.setVisibility(View.GONE);
                        tvExpressNum.setVisibility(View.GONE);
                        tvExpressNum.setVisibility(View.GONE);
                        tvConfirm.setVisibility(View.VISIBLE);
                        tvConfirm.setOnClickListener(v -> {
                            PickInputReceiverMessageActivity.move(WorkOrderDetailActivity.this, bean.getCode());
                        });
                    } else if ("RECEIVED".equals(bean.getState())) {
                        // 状态：已揽件
                        if ("PAID".equals(bean.getPayState())) {
                            // 已支付
                            if ("VERIFIED".equals(bean.getVerifyState())) {
                                // UI上显示“已寄出”
                                // UI上显示“用户已支付”
                                tvStatus.setText("已寄出");
                                tvPriceTip.setVisibility(View.VISIBLE);
                                viewDividerPrice.setVisibility(View.VISIBLE);
                                tvPrice.setVisibility(View.VISIBLE);
                                tvPrice.setText(String.valueOf(bean.getBudget()) +  "元");
                                tvCouponTip.setVisibility(View.VISIBLE);
                                tvCoupon.setVisibility(View.VISIBLE);
                                if (TextUtils.isEmpty(bean.getDiscountAmount())) {
                                    tvCoupon.setText("暂无抵扣(未用券或低于启用金额)");
                                } else {
                                    tvCoupon.setText("-"+ bean.getDiscountAmount() + "元（" + bean.getCouponNumber() + "）");
                                }
                                viewDividerCoupon.setVisibility(View.VISIBLE);
                                tvTotal.setVisibility(View.VISIBLE);
                                tvTotal.setText(String.valueOf(bean.getAmount()) +  "元");
                                tvTotalTip.setVisibility(View.VISIBLE);
                                viewDividerTotal.setVisibility(View.VISIBLE);
                                tvExpressTip.setVisibility(View.VISIBLE);
                                tvExpressNum.setVisibility(View.VISIBLE);
                                tvExpressNum.setVisibility(View.VISIBLE);
                                tvExpressNum.setText(bean.getTrackingNumber());
                            } else {
                                // UI上显示“用户已支付”
                                tvStatus.setText("用户已支付");
                                tvPriceTip.setVisibility(View.VISIBLE);
                                viewDividerPrice.setVisibility(View.VISIBLE);
                                tvPrice.setVisibility(View.VISIBLE);
                                tvPrice.setText(String.valueOf(bean.getBudget()) +  "元");
                                tvCouponTip.setVisibility(View.VISIBLE);
                                tvCoupon.setVisibility(View.VISIBLE);
                                if (TextUtils.isEmpty(bean.getDiscountAmount())) {
                                    tvCoupon.setText("暂无抵扣(未用券或低于启用金额)");
                                } else {
                                    tvCoupon.setText("-"+ bean.getDiscountAmount() + "元（" + bean.getCouponNumber() + "）");
                                }viewDividerCoupon.setVisibility(View.VISIBLE);
                                tvTotal.setVisibility(View.VISIBLE);
                                tvTotal.setText(String.valueOf(bean.getAmount()) +  "元");
                                tvTotalTip.setVisibility(View.VISIBLE);
                                viewDividerTotal.setVisibility(View.VISIBLE);
                                tvExpressTip.setVisibility(View.VISIBLE);
                                tvExpressNum.setVisibility(View.VISIBLE);
                                tvExpressNum.setVisibility(View.VISIBLE);
                                tvExpressNum.setText(bean.getTrackingNumber());
                            }
                        } else {
                            // 等待支付
                            // UI上显示“等待支付”
                            tvStatus.setText("等待支付");
                            tvPriceTip.setVisibility(View.VISIBLE);
                            viewDividerPrice.setVisibility(View.VISIBLE);
                            tvPrice.setVisibility(View.VISIBLE);
                            tvPrice.setText(String.valueOf(bean.getBudget()) +  "元");
                            tvCouponTip.setVisibility(View.VISIBLE);
                            tvCoupon.setVisibility(View.VISIBLE);
                            if (TextUtils.isEmpty(bean.getDiscountAmount())) {
                                tvCoupon.setText("暂无抵扣(未用券或低于启用金额)");
                            } else {
                                tvCoupon.setText("-"+ bean.getDiscountAmount() + "元（" + bean.getCouponNumber() + "）");
                            }
                            viewDividerCoupon.setVisibility(View.VISIBLE);
                            tvTotal.setVisibility(View.VISIBLE);
                            tvTotal.setText(String.valueOf(bean.getAmount()) +  "元");
                            tvTotalTip.setVisibility(View.VISIBLE);
                            viewDividerTotal.setVisibility(View.VISIBLE);
                            tvExpressTip.setVisibility(View.VISIBLE);
                            tvExpressNum.setVisibility(View.VISIBLE);
                            tvExpressNum.setVisibility(View.VISIBLE);
                            tvExpressNum.setText(bean.getTrackingNumber());
                        }
                    } else {
                        if (("CANCELLED".equals(bean.getState()))) {
                            // 状态：已取消
                            // UI上显示什么？？
                            tvStatus.setText("已取消");
                        } else {
                            // 状态：揽件异常
                            // UI上显示什么？？
                            tvStatus.setText("异常");
                        }
                        tvPriceTip.setVisibility(View.VISIBLE);
                        viewDividerPrice.setVisibility(View.VISIBLE);
                        tvPrice.setVisibility(View.VISIBLE);
                        tvPrice.setText(String.valueOf(bean.getBudget()) +  "元");
                        tvCouponTip.setVisibility(View.VISIBLE);
                        tvCoupon.setVisibility(View.VISIBLE);
                        if (TextUtils.isEmpty(bean.getDiscountAmount())) {
                            tvCoupon.setText("暂无抵扣(未用券或低于启用金额)");
                        } else {
                            tvCoupon.setText("-"+ bean.getDiscountAmount() + "元（" + bean.getCouponNumber() + "）");
                        }
                        viewDividerCoupon.setVisibility(View.VISIBLE);
                        tvTotal.setVisibility(View.VISIBLE);
                        tvTotal.setText(String.valueOf(bean.getAmount()) +  "元");
                        tvTotalTip.setVisibility(View.VISIBLE);
                        viewDividerTotal.setVisibility(View.VISIBLE);
                        tvExpressTip.setVisibility(View.VISIBLE);
                        tvExpressNum.setVisibility(View.VISIBLE);
                        tvExpressNum.setVisibility(View.VISIBLE);
                        tvExpressNum.setText(bean.getTrackingNumber());
                    }
                }
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
        if (resultCode == RESULT_OK) {
            isUpdate = true;
            requestData();
        }
    }

    private boolean isUpdate;
    public static void move(Activity context, String code, String trackingNumber) {
        Intent intent = new Intent(context, WorkOrderDetailActivity.class);
        intent.putExtra("code", code);
        intent.putExtra("trackingNumber", trackingNumber);
        context.startActivityForResult(intent, 0);
    }

    @OnClick(R.id.ivBack)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void finish() {
        if (isUpdate) {
            setResult(RESULT_OK);
        }
        super.finish();
    }
}
