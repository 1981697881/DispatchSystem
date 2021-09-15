package com.dispatch.system.module.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.dispatch.system.R;
import com.dispatch.system.base.BaseRvAdapter;
import com.dispatch.system.entity.ExceptionTemplateBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 异常原因适配器
 * <p>
 *
 * @author chenjunxu
 * @date 2020/7/21
 */
public class ScanExceptionAdapter extends BaseRvAdapter<ScanExceptionAdapter.VH> {

    private List<ExceptionTemplateBean.DataBean.ExceptionTemplatesBean> dataList;

    private OnScanExceptionCheck onScanExceptionCheck;

    public ScanExceptionAdapter(Context context,
                                List<ExceptionTemplateBean.DataBean.ExceptionTemplatesBean> dataList,
                                OnScanExceptionCheck onScanExceptionCheck) {
        super(context);
        this.dataList = dataList;
        this.onScanExceptionCheck = onScanExceptionCheck;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(getView(R.layout.item_error_reason, parent));
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        ExceptionTemplateBean.DataBean.ExceptionTemplatesBean bean = dataList.get(position);
        holder.tvContent.setText(bean.getDescription());
        holder.ivCheck.setSelected(bean.isCheck());
        holder.clRoot.setOnClickListener(v -> {
            for (int i = 0; i < dataList.size(); i++) {
                dataList.get(i).setCheck(false);
            }
            dataList.get(position).setCheck(true);
            notifyDataSetChanged();
            onScanExceptionCheck.onCheckException(bean);
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        @BindView(R.id.ivCheck)
        ImageView ivCheck;
        @BindView(R.id.tvContent)
        TextView tvContent;
        @BindView(R.id.clRoot)
        ConstraintLayout clRoot;

        public VH(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnScanExceptionCheck {
        void onCheckException(ExceptionTemplateBean.DataBean.ExceptionTemplatesBean bean);
    }
}

