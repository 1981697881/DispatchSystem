package com.dispatch.system.module.home.adapter;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.dispatch.system.R;
import com.dispatch.system.base.BaseRvAdapter;
import com.dispatch.system.common.ExpressHelper;
import com.dispatch.system.common.PhoneHelper;
import com.dispatch.system.entity.BuildingDetailDeliveryBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 楼栋详情-派送
 * <p>
 *
 * @author chenjunxu
 * @date 2020/7/23
 */
public class BuildingDetailDeliveryAdapter extends BaseRvAdapter<BuildingDetailDeliveryAdapter.VH> {

    private List<BuildingDetailDeliveryBean.DataBean.HouseDeliveryRecordsBean> dataList;

    public BuildingDetailDeliveryAdapter(Context context, List<BuildingDetailDeliveryBean.DataBean.HouseDeliveryRecordsBean> dataList) {
        super(context);
        this.dataList = dataList;
    }

    public void setCheckMode(boolean isCheckMode) {
        for (int i = 0; i < dataList.size(); i++) {
            dataList.get(i).setOpenCheckMode(isCheckMode);
            dataList.get(i).setCheck(false);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(getView(R.layout.item_building_detail, parent));
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        BuildingDetailDeliveryBean.DataBean.HouseDeliveryRecordsBean bean = dataList.get(position);
        holder.tvName.setText(String.format("%s %s", bean.getAddressee(), bean.getAddresseeMobileNumber()));
        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneHelper.callPhone((FragmentActivity) mContext, bean.getAddresseeMobileNumber());
            }
        });
        holder.tvTime.setVisibility(View.GONE);
        holder.tvExpress.setText(String.format("%s %s", bean.getBusiness(), bean.getTrackingNumber()));
        holder.tvExpress.setOnLongClickListener(v -> {
            ClipboardManager clipboardManager = (ClipboardManager) mContext.getSystemService(
                    Context.CLIPBOARD_SERVICE);
            String copy = bean.getTrackingNumber();
            clipboardManager.setPrimaryClip(ClipData.newPlainText(null, copy));
            Toast.makeText(mContext.getApplicationContext(),  "复制快递单号成功！", Toast.LENGTH_SHORT).show();
            return false;
        });
        holder.tvAddTime.setVisibility(View.VISIBLE);
        holder.tvAddTime.setText("入库时间 " + ExpressHelper.getTime(bean.getCreateTime()));
        if ("SIGNED".equals(bean.getState())) {
            // 已签收
            holder.tvTime.setVisibility(View.VISIBLE);
            holder.tvTime.setText("签收时间 " + ExpressHelper.getTime(bean.getSignedTime()));
            holder.tvMask.setText("已签收");
            holder.tvMask.setBackgroundColor(0xFF999999);
        } else if ("DELIVERING".equals(bean.getState())) {
            // 派送中
            holder.tvMask.setText("派送中");
            holder.tvMask.setBackgroundColor(0xFFFF9F1E);
        } else if ("REFUSE".equals(bean.getState())) {
            // 拒收
            holder.tvMask.setText("用戶已拒收");
            holder.tvMask.setBackgroundColor(0xFFDE1111);
        } else if ("WAIT".equals(bean.getState())) {
            // 待派送
            holder.tvMask.setText("待派送");
            holder.tvMask.setBackgroundColor(0xFFDE1111);
        } else if ("TIMEOUT".equals(bean.getState())) {
            // 超时
            holder.tvMask.setText("超时");
            holder.tvMask.setBackgroundColor(0xFFDE1111);
        } else {
            // 异常
            if (TextUtils.isEmpty(bean.getExceptionDesc())) {
                holder.tvMask.setText("异常");
            } else {
                holder.tvMask.setText(bean.getExceptionDesc());
            }
            holder.tvMask.setBackgroundColor(0xFFDE1111);
        }

        if (bean.isOpenCheckMode()) {
            holder.ivCheck.setVisibility(View.VISIBLE);
        } else {
            holder.ivCheck.setVisibility(View.GONE);
        }

        if (bean.isCheck()) {
            holder.ivCheck.setImageResource(R.mipmap.ic_red_check);
        } else {
            holder.ivCheck.setImageResource(R.mipmap.ic_gray_un_check);
        }

        holder.clRoot.setOnClickListener(v -> {
            if (bean.isCheck()) {
                bean.setCheck(false);
                notifyItemChanged(position);
                return;
            }

//            for (int i = 0; i < dataList.size(); i++) {
//                dataList.get(i).setCheck(false);
//            }
            bean.setCheck(true);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        @BindView(R.id.ivCheck)
        ImageView ivCheck;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvExpress)
        TextView tvExpress;
        @BindView(R.id.tvTime)
        TextView tvTime;
        @BindView(R.id.tvAddTime)
        TextView tvAddTime;
        @BindView(R.id.tvMask)
        TextView tvMask;
        @BindView(R.id.clRoot)
        ConstraintLayout clRoot;

        public VH(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
