package com.dispatch.system.module.common;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.dispatch.system.R;
import com.dispatch.system.base.BaseActivity;
import com.dispatch.system.entity.BaseBean;
import com.dispatch.system.entity.WorkOrderBean;
import com.dispatch.system.http.ApiClient;
import com.dispatch.system.http.MyObserver;
import com.dispatch.system.module.pick.PickInputReceiverMessageActivity;
import com.google.gson.JsonObject;

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
    @BindView(R.id.tvLeaveMessage)
    TextView tvLeaveMessage; @BindView(R.id.tvMessage)
    TextView tvMessage;
    @BindView(R.id.ivBack)
    View ivBack;
    private int id;
    private String waybillNo;
    private String remark;
    private String senderName;
    private String senderWorkNo;
    private String isUrgent;
    private String customerContact;
    private String customerName;
    private String customerAddress;
    private String status;
    private String feedback;


    @Override
    protected int bindLayout() {
        return R.layout.activity_work_order_detail;
    }

    @Override
    protected void initView() {
        super.initView();
        id = getIntent().getIntExtra("id",-1);
        waybillNo = getIntent().getStringExtra("waybillNo");
        remark = getIntent().getStringExtra("remark");
        senderName = getIntent().getStringExtra("senderName");
        senderWorkNo = getIntent().getStringExtra("senderWorkNo");
        isUrgent = getIntent().getStringExtra("isUrgent");
        customerContact = getIntent().getStringExtra("customerContact");
        customerName = getIntent().getStringExtra("customerName");
        customerContact = getIntent().getStringExtra("customerContact");
        customerAddress = getIntent().getStringExtra("customerAddress");
        status = getIntent().getStringExtra("status");
        feedback = getIntent().getStringExtra("feedback");
        ivBack.setVisibility(View.VISIBLE);

        tvConfirm.setVisibility(View.GONE);
        // 自揽件
        clUserInfo.setVisibility(View.GONE);
        if (status.equals("1")) {
            tvStatus.setText("已完成");
            tvConfirm.setVisibility(View.GONE);
            tvMessage.setText(feedback);
        } else {
            tvStatus.setText("待处理");
            tvConfirm.setVisibility(View.VISIBLE);
            tvConfirm.setOnClickListener(v -> {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("id", id);
                jsonObject.addProperty("waybillNo", waybillNo);
                jsonObject.addProperty("remark", remark);
                jsonObject.addProperty("senderName", senderName);
                jsonObject.addProperty("senderWorkNo", senderWorkNo);
                jsonObject.addProperty("isUrgent", isUrgent);
                jsonObject.addProperty("customerContact", customerContact);
                jsonObject.addProperty("customerName", customerName);
                jsonObject.addProperty("customerAddress", customerAddress);
                jsonObject.addProperty("status", "1");
                jsonObject.addProperty("feedback", tvMessage.getText().toString());
                request(jsonObject);
            });
        }
        tvPrice.setText(customerName);
        tvCoupon.setText(customerContact);
        tvTotal.setText(customerAddress);
        tvExpressNum.setText(waybillNo);
        tvLeaveMessage.setText(remark);
    }
    private void request(JsonObject jsonObject) {
        showLoading();
        ApiClient.getInstance().updateWorkOrder(jsonObject,
                new MyObserver<BaseBean>() {
                    @Override
                    protected void getDisposable(Disposable d) {
                    }
                    @Override
                    protected void onSuccess(BaseBean baseBean) {
                        hideLoading();
                        ToastUtils.showLong("确认成功！");
                        setResult(RESULT_OK);
                        finish();
                    }

                    @Override
                    protected void onError(String msg) {
                        hideLoading();
                    }
                });
    }
    private boolean isUpdate;

    public static void move(Activity context, WorkOrderBean.DataBean.workOrderBean bean) {
        Intent intent = new Intent(context, WorkOrderDetailActivity.class);
        intent.putExtra("id", bean.getId());
        intent.putExtra("waybillNo", bean.getWaybillno());
        intent.putExtra("remark", bean.getRemark());
        intent.putExtra("senderName", bean.getSendername());
        intent.putExtra("senderWorkNo", bean.getSenderworkno());
        intent.putExtra("isUrgent", bean.getIsurgent());
        intent.putExtra("customerContact", bean.getCustomercontact());
        intent.putExtra("customerName", bean.getCustomername());
        intent.putExtra("customerAddress", bean.getCustomeraddress());
        intent.putExtra("status", bean.getStatus());
        intent.putExtra("feedback", bean.getFeedback());
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
