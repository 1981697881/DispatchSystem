package com.dispatch.system.module.home.adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.dispatch.system.R;
import com.dispatch.system.entity.HomeTaskGroup;
import com.dispatch.system.entity.MineTodayTaskBean;
import com.dispatch.system.module.home.ui.BuildingTaskListActivity;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import java.util.List;

import butterknife.internal.Utils;

import static com.qmuiteam.qmui.util.QMUIDisplayHelper.getDisplayMetrics;

public class HomeTaskAdapter extends
        ExpandableRecyclerViewAdapter<HomeTaskAdapter.TimeViewHolder, HomeTaskAdapter.TaskViewHolder> {
    private LayoutInflater inflater;
    private boolean isMine;
    private boolean isTimeOut;
    private boolean isJsd;

    public HomeTaskAdapter(Context context, List<? extends ExpandableGroup> groups, boolean isMine, boolean isTimeOut, boolean isJsd) {
        super(groups);
        this.isMine = isMine;
        this.isTimeOut = isTimeOut;
        this.isJsd = isJsd;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public TimeViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_home_task_title, parent, false);
        return new TimeViewHolder(view);
    }

    @Override
    public TaskViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_home_task_content, parent, false);
        return new TaskViewHolder(view, isMine, isTimeOut,isJsd);
    }

    @Override
    public void onBindChildViewHolder(TaskViewHolder holder, int flatPosition,
                                      ExpandableGroup group, int childIndex) {
        final MineTodayTaskBean.DataBean.DeliveryTimeTasksBean.BuildingTaskCountListBean bean
                = ((HomeTaskGroup) group).getItems().get(childIndex);
        holder.onBind(flatPosition, bean);
    }

    @Override
    public void onBindGroupViewHolder(TimeViewHolder holder, int flatPosition,
                                      ExpandableGroup group) {
        holder.setGenreTitle(group);
    }

    static class TaskViewHolder extends ChildViewHolder {

        private TextView tvTitle;
        private ConstraintLayout clRoot;
        private TextView tvPostHasDeliveryCount;
        private TextView tvPostAllDeliveryCount;
        private TextView tvPost;
        private TextView tvPostHasReceiveCount;
        private TextView tvReceiver;
        private TextView tvPostAllReceiveCount;
        private ImageView ivStart;
        private boolean isMine;
        private boolean isTimeOut;
        private boolean isJsd;

        public TaskViewHolder(View itemView, boolean isMine, boolean isTimeOut, boolean isJsd) {
            super(itemView);
            this.isMine = isMine;
            this.isTimeOut = isTimeOut;
            this.isJsd = isJsd;
            tvTitle = itemView.findViewById(R.id.tvTitle);
            clRoot = itemView.findViewById(R.id.clRoot);
            tvPostHasDeliveryCount = itemView.findViewById(R.id.tvPostHasDeliveryCount);
            tvPostAllDeliveryCount = itemView.findViewById(R.id.tvPostAllDeliveryCount);
            tvPost = itemView.findViewById(R.id.tvPost);
            tvPostHasReceiveCount = itemView.findViewById(R.id.tvPostHasReceiveCount);
            tvPostAllReceiveCount = itemView.findViewById(R.id.tvPostAllReceiveCount);
            tvReceiver = itemView.findViewById(R.id.tvReceiver);
            if(isJsd){
                tvPostHasDeliveryCount.setVisibility(View.GONE);
                tvPostAllDeliveryCount.setVisibility(View.GONE);
                tvPost.setVisibility(View.GONE);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tvReceiver.getLayoutParams();
                layoutParams.setMarginStart(40);//把75dp转换为px
                tvReceiver.setLayoutParams(layoutParams);
            }
            ivStart = itemView.findViewById(R.id.ivStart);
            clRoot = itemView.findViewById(R.id.clRoot);
        }
        public void onBind(int flatPosition, MineTodayTaskBean.DataBean.DeliveryTimeTasksBean.BuildingTaskCountListBean bean) {
            tvTitle.setText(bean.getName());
            if (isTimeOut) {
                tvPostAllDeliveryCount.setText(String.format("/%d", bean.getTimeoutCount()));
                tvPostHasDeliveryCount.setText(String.valueOf(bean.getTimeoutCount()));
            } else {
                tvPostAllDeliveryCount.setText(String.format("/%d", bean.getDeliveryCount() + bean.getNotDeliveryCount()));
                tvPostHasDeliveryCount.setText(String.valueOf(bean.getDeliveryCount()));
            }
            tvPostAllReceiveCount.setText(String.format("/%d", bean.getReceivingCount() + bean.getNotReceivingCount()));
            tvPostHasReceiveCount.setText(String.valueOf(bean.getReceivingCount()));
            clRoot.setOnClickListener(v ->
                    BuildingTaskListActivity.move(clRoot.getContext(), bean, isMine, isTimeOut,isJsd));
            ivStart.setVisibility(bean.isContainUrgentItem() ? View.VISIBLE : View.GONE);
        }
    }

    static class TimeViewHolder extends GroupViewHolder {

        private TextView genreTitle;

        public TimeViewHolder(View itemView) {
            super(itemView);
            genreTitle = itemView.findViewById(R.id.tvTime);
        }

        public void setGenreTitle(ExpandableGroup group) {
            genreTitle.setText(group.getTitle());
        }
    }
}
