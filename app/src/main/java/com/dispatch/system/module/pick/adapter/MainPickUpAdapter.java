package com.dispatch.system.module.pick.adapter;


import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.dispatch.system.R;
import com.dispatch.system.base.BaseRvAdapter;
import com.dispatch.system.common.ExpressHelper;
import com.dispatch.system.common.PhoneHelper;
import com.dispatch.system.entity.ReceivingRecordsBean;
import com.dispatch.system.module.common.PickUpDetailActivity;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的揽件适配器
 * <p>
 *
 * @author chenjunxu
 * @date 2020/7/19
 */
public class MainPickUpAdapter extends BaseRvAdapter<MainPickUpAdapter.VH> {

    private List<ReceivingRecordsBean> dataList;

    public MainPickUpAdapter(Context context, List<ReceivingRecordsBean> dataList) {
        super(context);
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = getView(R.layout.item_mine_pick_up_task, parent);
        view.setOnClickListener(v -> {

        });
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.updateUI(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class VH extends RecyclerView.ViewHolder {

        @BindView(R.id.ivPoster)
        ImageView ivPoster;
        @BindView(R.id.tvPosterName)
        TextView tvPosterName;
        @BindView(R.id.tvPosterPhone)
        TextView tvPosterPhone;
        @BindView(R.id.ivReceiver)
        ImageView ivReceiver;
        @BindView(R.id.tvReceiverName)
        TextView tvReceiverName;
        @BindView(R.id.tvReceiverPhone)
        TextView tvReceiverPhone;
        @BindView(R.id.tvAddress)
        TextView tvAddress;
        @BindView(R.id.viewDivider)
        View viewDivider;
        @BindView(R.id.tvTime)
        TextView tvTime;
        @BindView(R.id.tvExpress)
        TextView tvExpress;
        @BindView(R.id.tvPay)
        TextView tvPay;
        @BindView(R.id.tvMark)
        TextView tvMark;
        @BindView(R.id.tvAddTime)
        TextView tvAddTime;
        @BindView(R.id.tvOrderTime)
        TextView tvOrderTime;
        @BindView(R.id.tvMinePickUpExpress)
        TextView tvMinePickUpExpress;
        @BindView(R.id.tvMinePickUpPay)
        TextView tvMinePickUpPay;
        @BindView(R.id.tvMinePickUpTime)
        TextView tvMinePickUpTime;
        @BindView(R.id.groupMinePickUp)
        Group groupMinePickUp;
        //        @BindView(R.id.groupCommon)
//        Group groupCommon;
        @BindView(R.id.clRoot)
        ViewGroup clRoot;

        public VH(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void updateUI(ReceivingRecordsBean bean) {

            tvReceiverPhone.setOnClickListener(null);
            tvPosterPhone.setOnClickListener(null);
            tvTime.setVisibility(View.GONE);
            if (Objects.equals(bean.getType(), "EMPLOYEE_SELF")) {
                // 自揽件
//                groupCommon.setVisibility(View.GONE);
                setVisibleCommonView(false);
                groupMinePickUp.setVisibility(View.VISIBLE);
                tvMinePickUpExpress.setText(String.format("%s %s", bean.getBusiness(), bean.getTrackingNumber()));
                tvMinePickUpExpress.setOnLongClickListener(v -> {
                    ClipboardManager clipboardManager = (ClipboardManager) tvMinePickUpExpress.getContext().getSystemService(
                            Context.CLIPBOARD_SERVICE);
                    String copy = bean.getTrackingNumber();
                    clipboardManager.setPrimaryClip(ClipData.newPlainText(null, copy));
                    Toast.makeText(tvMinePickUpExpress.getContext().getApplicationContext(),  "复制快递单号成功！", Toast.LENGTH_SHORT).show();
                    return false;
                });
                tvMinePickUpPay.setText(String.format("已收款 %s元", bean.getBudget()));
                tvMinePickUpTime.setText("入库时间 " + ExpressHelper.getTime(bean.getCreateTime()));
                tvMark.setTextColor(Color.WHITE);
                tvMark.setText("自揽件");
                tvMark.setBackgroundColor(0xFF007BFF);
            } else {
                groupMinePickUp.setVisibility(View.GONE);
//                groupCommon.setVisibility(View.VISIBLE);
                setVisibleCommonView(true);
                tvPosterName.setText(bean.getSender());
                tvPosterPhone.setText(bean.getSenderMobileNumber());
                tvReceiverName.setText(bean.getAddressee());
                tvReceiverPhone.setText(bean.getAddresseeMobileNumber());
                tvReceiverPhone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PhoneHelper.callPhone((FragmentActivity) tvReceiverPhone.getContext(), bean.getAddresseeMobileNumber());
                    }
                });
                tvPosterPhone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PhoneHelper.callPhone((FragmentActivity) tvPosterPhone.getContext(), bean.getSenderMobileNumber());
                    }
                });
                tvAddress.setText(bean.getSenderAreaName() + bean.getSenderAddress());
                tvAddTime.setText("入库时间：" + ExpressHelper.getTime(bean.getCreateTime()));
                tvOrderTime.setText("揽件日期：" + ExpressHelper.getDate(bean.getReceivedDate()));

                if ("NOT_RECEIVED".equals(bean.getState())) {
                    // 状态：未揽件
                    // UI上显示“待上门取件”
                    tvMark.setText("待上门取件");
                    tvMark.setTextColor(Color.WHITE);
                    tvMark.setBackgroundColor(0xFFDE1111);
                    tvPay.setVisibility(View.GONE);
                    tvTime.setVisibility(View.VISIBLE);
                    tvTime.setText("预约时间：" + ExpressHelper.formatTime(bean.getReceivingStartTime()) + "~" + ExpressHelper.formatTime(bean.getReceivingEndTime()));
                    tvExpress.setVisibility(View.GONE);
                } else if ("RECEIVED".equals(bean.getState())) {
                    // 状态：已揽件
                    if ("PAID".equals(bean.getPayState())) {
                        // 已支付
                        if ("VERIFIED".equals(bean.getVerifyState())) {
                            // UI上显示“已寄出”
                            tvMark.setText("已寄出");
                            tvMark.setTextColor(Color.WHITE);
                            tvMark.setBackgroundColor(0xFF999999);
                            tvPay.setVisibility(View.GONE);
                            tvTime.setVisibility(View.GONE);
                            tvExpress.setVisibility(View.VISIBLE);
                            tvExpress.setText(bean.getBusiness() + " " + bean.getTrackingNumber());
                        } else {
                            // UI上显示“用户已支付”
                            tvMark.setText("用户已支付");
                            tvMark.setTextColor(0xFFDE1111);
                            tvMark.setBackgroundColor(0xFFFFE7E7);
                            tvPay.setVisibility(View.GONE);
                            tvTime.setVisibility(View.GONE);
                            tvExpress.setVisibility(View.VISIBLE);
                            tvExpress.setText(bean.getBusiness() + " " + bean.getTrackingNumber());
                        }
                    } else {
                        // 等待支付
                        // UI上显示“等待支付”
                        tvMark.setText("等待支付");
                        tvMark.setTextColor(Color.WHITE);
                        tvMark.setBackgroundColor(0xFFFF9F1E);
                        tvPay.setVisibility(View.VISIBLE);
                        tvPay.setText("等待用户支付 " + bean.getAmount() + "元");
                        tvTime.setVisibility(View.GONE);
                        tvExpress.setVisibility(View.VISIBLE);
                        tvExpress.setText(bean.getBusiness() + " " + bean.getTrackingNumber());
                    }
                } else {
                    if ("CANCELLED".equals(bean.getState())) {
                        // 状态：已取消
                        // UI上显示什么？？
                        tvMark.setText("已取消");
                    } else {
                        // 状态：揽件异常
                        // UI上显示什么？？
                        tvMark.setText("异常");
                    }

                    // UI上显示“已寄出”
                    tvMark.setTextColor(Color.WHITE);
                    tvMark.setBackgroundColor(0xFF999999);
                    tvPay.setVisibility(View.GONE);
                    tvTime.setVisibility(View.GONE);
                    tvExpress.setVisibility(View.VISIBLE);
                    tvExpress.setText(bean.getBusiness() + " " + bean.getTrackingNumber());
                }
            }

            if (tvExpress.getVisibility() == View.VISIBLE) {
                tvExpress.setOnLongClickListener(v -> {
                    ClipboardManager clipboardManager = (ClipboardManager) tvExpress.getContext().getSystemService(
                            Context.CLIPBOARD_SERVICE);
                    String copy = bean.getTrackingNumber();
                    clipboardManager.setPrimaryClip(ClipData.newPlainText(null, copy));
                    Toast.makeText(tvExpress.getContext().getApplicationContext(),  "复制快递单号成功！", Toast.LENGTH_SHORT).show();
                    return false;
                });
            }

            clRoot.setOnClickListener(v -> {
                PickUpDetailActivity.move((Activity) clRoot.getContext(), bean.getCode(), bean.getTrackingNumber());
            });
        }


        private void setVisibleCommonView(boolean visible) {
            tvPay.setVisibility(visible ? View.VISIBLE : View.GONE);
            tvExpress.setVisibility(visible ? View.VISIBLE : View.GONE);
            tvTime.setVisibility(visible ? View.VISIBLE : View.GONE);
            viewDivider.setVisibility(visible ? View.VISIBLE : View.GONE);
            tvAddress.setVisibility(visible ? View.VISIBLE : View.GONE);
            tvAddTime.setVisibility(visible ? View.VISIBLE : View.GONE);
            tvOrderTime.setVisibility(visible ? View.VISIBLE : View.GONE);
            tvReceiverName.setVisibility(visible ? View.VISIBLE : View.GONE);
            ivReceiver.setVisibility(visible ? View.VISIBLE : View.GONE);
            tvReceiverPhone.setVisibility(visible ? View.VISIBLE : View.GONE);
            ivPoster.setVisibility(visible ? View.VISIBLE : View.GONE);
            tvPosterName.setVisibility(visible ? View.VISIBLE : View.GONE);
            tvPosterPhone.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    }
}
