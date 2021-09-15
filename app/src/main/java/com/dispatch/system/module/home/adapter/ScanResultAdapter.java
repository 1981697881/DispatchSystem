package com.dispatch.system.module.home.adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.dispatch.system.R;
import com.dispatch.system.base.BaseRvAdapter;
import com.dispatch.system.constants.SPConstants;
import com.dispatch.system.entity.MemberBean;
import com.dispatch.system.event.RemoveStorageEvent;
import com.dispatch.system.utils.BusinessHelper;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 扫描列表适配器
 * <p>
 *
 * @author chenjunxu
 * @date 2020/7/19
 */
public class ScanResultAdapter extends BaseRvAdapter<ScanResultAdapter.VH> {

    private List<MemberBean> dataList;

    public ScanResultAdapter(Context context, List<MemberBean> dataList) {
        super(context);
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(getView(R.layout.item_scan_message, parent));
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        ViewGroup.LayoutParams params = holder.clLayout.getLayoutParams();
        params.width = ScreenUtils.getScreenWidth();
        holder.clLayout.setLayoutParams(params);
        holder.updateUI(dataList.get(position));
        holder.ivClose.setOnClickListener(v -> {
            new AlertDialog.Builder(mContext)
                    .setTitle("是否确认删除？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            dataList.remove(position);
                            notifyItemRemoved(position);
                            notifyDataSetChanged();
                            SPUtils.getInstance().put(SPConstants.SP_ADD_STORAGE, new Gson().toJson(dataList));
                            EventBus.getDefault().post(new RemoveStorageEvent());
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create()
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class VH extends RecyclerView.ViewHolder {

        @BindView(R.id.tvExpressNum)
        TextView tvExpressNum;
        @BindView(R.id.tvUserPhone)
        TextView tvUserPhone;
        @BindView(R.id.tvExpressName)
        TextView tvExpressName;
        @BindView(R.id.tvUserName)
        TextView tvUserName;
        @BindView(R.id.ivClose)
        ImageView ivClose;
        @BindView(R.id.clLayout)
        ConstraintLayout clLayout;
        @BindView(R.id.tvExpressAddress)
        TextView tvExpressAddress;

        public VH(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void updateUI(MemberBean bean) {
            tvExpressNum.setText(bean.getExpressNum());
            tvUserPhone.setText(bean.getMobileNumber());
            tvUserName.setText(bean.getRealName());
            tvExpressName.setText(BusinessHelper.get(bean.getBusiness()));
            tvExpressAddress.setText(bean.getBuildingName());
        }
    }
}

