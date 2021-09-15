package com.dispatch.system.module.home.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.dispatch.system.R;
import com.dispatch.system.base.BaseRvAdapter;
import com.dispatch.system.entity.BuildingTaskListBean;
import com.dispatch.system.module.home.ui.BuildingDetailActivity;
import com.dispatch.system.module.home.ui.BuildingDetailTimeOutActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 楼栋任务列表适配器
 * <p>
 *
 * @author chenjunxu
 * @date 2020/7/18
 */
public class BuildingTaskListAdapter extends BaseRvAdapter<BuildingTaskListAdapter.VH> {

    private List<BuildingTaskListBean.DataBean.HouseTasksBean> datas;
    private boolean isTimeOut;
    private boolean isMine;
    private boolean isJsd;

    public BuildingTaskListAdapter(Context context, List<BuildingTaskListBean.DataBean.HouseTasksBean> datas, boolean isTimeOut, boolean isMine, boolean isJsd) {
        super(context);
        this.datas = datas;
        this.isTimeOut = isTimeOut;
        this.isMine = isMine;
        this.isJsd = isJsd;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(getView(R.layout.item_home_building_task_list, parent));
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        BuildingTaskListBean.DataBean.HouseTasksBean bean = datas.get(position);
        holder.tvTitle.setText(bean.getHouseNumber());
        if(isJsd){
            holder.tvPostAllDeliveryCount.setVisibility(View.GONE);
            holder.tvPostHasDeliveryCount.setVisibility(View.GONE);
            holder.tvPost.setVisibility(View.GONE);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)  holder.tvReceiver.getLayoutParams();
            layoutParams.setMarginStart(40);//把75dp转换为px
            holder.tvReceiver.setLayoutParams(layoutParams);
        }
        if (isTimeOut) {
            holder.tvPostAllDeliveryCount.setText(String.format("/%d", bean.getTimeoutCount()));
            holder.tvPostHasDeliveryCount.setText(String.valueOf(bean.getTimeoutCount()));
        } else {
            holder.tvPostAllDeliveryCount.setText(String.format("/%d", bean.getDeliveryCount() + bean.getNotDeliveryCount()));
            holder.tvPostHasDeliveryCount.setText(String.valueOf(bean.getDeliveryCount()));
        }
        holder.tvPostAllReceiveCount.setText(String.format("/%d", bean.getReceivingCount() + bean.getNotReceivingCount()));
        holder.tvPostHasReceiveCount.setText(String.valueOf(bean.getReceivingCount()));
        holder.ivStart.setVisibility(bean.isContainUrgentItem() ? View.VISIBLE : View.GONE);
        holder.clRoot.setOnClickListener(v -> {
            if (isTimeOut) {
                BuildingDetailTimeOutActivity.move(mContext, bean.getBuildingCode(), bean.getHouseNumber());
            } else if(isMine) {
                BuildingDetailActivity.move(mContext, bean.getBuildingCode(), bean.getHouseNumber());
            } else{
                showNormalDialog((FragmentActivity) mContext, bean.getBuildingCode(), bean.getHouseNumber());
            }
        });
    }
    private void showNormalDialog(FragmentActivity activity,String buildingCode, String houseNumber){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(activity);
        normalDialog.setTitle("温馨提醒");
        normalDialog.setMessage("是否确认揽件？");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        BuildingDetailActivity.move(mContext, buildingCode, houseNumber);
                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        // 显示
        normalDialog.show();
    }
    @Override
    public int getItemCount() {
        return datas.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvPostHasDeliveryCount)
        TextView tvPostHasDeliveryCount;
        @BindView(R.id.tvPostAllDeliveryCount)
        TextView tvPostAllDeliveryCount;
        @BindView(R.id.tvPostHasReceiveCount)
        TextView tvPostHasReceiveCount;
        @BindView(R.id.tvPostAllReceiveCount)
        TextView tvPostAllReceiveCount;
        @BindView(R.id.tvPost)
        TextView tvPost;
        @BindView(R.id.tvReceiver)
        TextView tvReceiver;
        @BindView(R.id.clRoot)
        ConstraintLayout clRoot;
        @BindView(R.id.ivStart)
        ImageView ivStart;

        public VH(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
