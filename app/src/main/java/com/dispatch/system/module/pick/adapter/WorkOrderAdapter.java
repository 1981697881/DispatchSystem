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
import com.dispatch.system.entity.WorkOrderBean;
import com.dispatch.system.module.common.PickUpDetailActivity;
import com.dispatch.system.module.common.WorkOrderDetailActivity;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的工单 适配器
 */
public class WorkOrderAdapter extends BaseRvAdapter<WorkOrderAdapter.VH> {
    private List<WorkOrderBean.DataBean.workOrderBean> dataList;

    public WorkOrderAdapter(Context context, List<WorkOrderBean.DataBean.workOrderBean> dataList) {
        super(context);
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public WorkOrderAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = getView(R.layout.item_work_order_task, parent);
        view.setOnClickListener(v -> {

        });
        return new WorkOrderAdapter.VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkOrderAdapter.VH holder, int position) {
        holder.updateUI(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class VH extends RecyclerView.ViewHolder {

        @BindView(R.id.ivPoster)
        ImageView ivPoster;
        @BindView(R.id.tvIsUrgent)
        TextView tvIsUrgent;
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

        public void updateUI(WorkOrderBean.DataBean.workOrderBean bean) {
            tvReceiverPhone.setOnClickListener(null);
            tvPosterPhone.setOnClickListener(null);
            tvTime.setVisibility(View.GONE);
            setVisibleCommonView(false);
            groupMinePickUp.setVisibility(View.VISIBLE);
            tvMinePickUpExpress.setText(bean.getRemark());
            tvReceiverPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PhoneHelper.callPhone((FragmentActivity) tvReceiverPhone.getContext(), bean.getCustomercontact());
                }
            });
            tvMinePickUpPay.setText("联系人：" + bean.getCustomername());
            tvMinePickUpTime.setText("联系电话：" + bean.getCustomercontact());
            tvIsUrgent.setText("是否紧急：" + (bean.getIsurgent().equals("1")?"加急":"否"));
            tvMark.setTextColor(Color.WHITE);
            if(bean.getStatus().equals("1")){
                tvMark.setText("已完成");
                tvMark.setBackgroundColor(0xFF007BFF);
            }else{
                tvMark.setText("待处理");
            }
            clRoot.setOnClickListener(v -> {
                WorkOrderDetailActivity.move((Activity) clRoot.getContext(), bean);
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
