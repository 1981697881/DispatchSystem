package com.dispatch.system.module.home.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.dispatch.system.R;
import com.dispatch.system.base.BaseActivity;
import com.dispatch.system.base.BaseRvAdapter;
import com.dispatch.system.entity.BaseBean;
import com.dispatch.system.entity.DeliveryTimeBean;
import com.dispatch.system.http.ApiClient;
import com.dispatch.system.http.MyObserver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;

public class DeliveryDetailChangeTimeActivity extends BaseActivity {

    @BindView(R.id.tvToday)
    TextView tvToday;
    @BindView(R.id.rvToday)
    RecyclerView rvToday;
    @BindView(R.id.tvTomorrow)
    TextView tvTomorrow;
    @BindView(R.id.rvTomorrow)
    RecyclerView rvTomorrow;
    private String dataType;
    private ArrayList<String> trackingNumbers;
    private String timeCode;
    private TodayTimeAdapter todayTimeAdapter;
    private TodayTimeAdapter tomorrowTimeAdapter;

    private List<DeliveryTimeBean.DataBean.DeliveryTimesBean> todayList = new ArrayList<>();
    private List<DeliveryTimeBean.DataBean.DeliveryTimesBean> tomorrowList = new ArrayList<>();

    @Override
    protected int bindLayout() {
        return R.layout.activity_pick_detail_change_time;
    }

    @Override
    protected void initView() {
        super.initView();
        trackingNumbers = getIntent().getStringArrayListExtra("trackingNumbers");
        requestData();
    }

    private void requestData() {
        showLoading();
        ApiClient.getInstance().getDeliveryTime(new MyObserver<DeliveryTimeBean>() {
            @Override
            protected void getDisposable(Disposable d) {
            }

            @Override
            protected void onSuccess(DeliveryTimeBean deliveryTimeBean) {
                hideLoading();
                List<DeliveryTimeBean.DataBean.DeliveryTimesBean> deliveryTimes
                        = deliveryTimeBean.getData().getDeliveryTimes();
                boolean isCheckFirst = false;
                for (int i = 0; i < deliveryTimes.size(); i++) {
                    DeliveryTimeBean.DataBean.DeliveryTimesBean timesBean = deliveryTimes.get(i);
                    timesBean.setShowText(formatTime(timesBean.getStartTime()) + "~" + formatTime(timesBean.getEndTime()));
                    if (timesBean.getStartTime() > getCurrentTime()) {
                        if (!isCheckFirst) {
                            timesBean.isCheck = true;
                            isCheckFirst = true;
                        }
                        todayList.add(timesBean);
                    }
                    try {
                        DeliveryTimeBean.DataBean.DeliveryTimesBean clone = (DeliveryTimeBean.DataBean.DeliveryTimesBean) timesBean.clone();
                        clone.isCheck = false;
                        tomorrowList.add(clone);
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                }
                if (todayList.isEmpty()) {
                    tvToday.setVisibility(View.GONE);
                    rvToday.setVisibility(View.GONE);
                    if (!tomorrowList.isEmpty()) {
                        tomorrowList.get(0).isCheck = true;
                    }
                } else {
                    todayTimeAdapter = new TodayTimeAdapter(DeliveryDetailChangeTimeActivity.this,
                            todayList, bean -> {
                        todayTimeAdapter.clearCheck();
                        bean.isCheck = true;
                        todayTimeAdapter.notifyDataSetChanged();
                        tomorrowTimeAdapter.clearCheck();
                        tomorrowTimeAdapter.notifyDataSetChanged();
                    });
                    rvToday.setLayoutManager(new GridLayoutManager(DeliveryDetailChangeTimeActivity.this, 3));
                    rvToday.setAdapter(todayTimeAdapter);
                }
                rvTomorrow.setLayoutManager(new GridLayoutManager(DeliveryDetailChangeTimeActivity.this, 3));
                tomorrowTimeAdapter = new TodayTimeAdapter(DeliveryDetailChangeTimeActivity.this,
                        tomorrowList, bean -> {
                    tomorrowTimeAdapter.clearCheck();
                    bean.isCheck = true;
                    tomorrowTimeAdapter.notifyDataSetChanged();
                    todayTimeAdapter.clearCheck();
                    todayTimeAdapter.notifyDataSetChanged();
                });
                rvTomorrow.setAdapter(tomorrowTimeAdapter);
            }

            @Override
            protected void onError(String msg) {
                hideLoading();
            }
        });
    }

    private long getCurrentTime() {
        // 设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        // new Date()为获取当前系统时间，也可使用当前时间戳
        String date = df.format(new Date(System.currentTimeMillis()));
        try {
            return df.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private CharSequence formatTime(long time) {
        return DateFormat.format("HH:mm", time);
    }

    private DeliveryTimeBean.DataBean.DeliveryTimesBean getSelectBean() {
        for (int i = 0; i < todayList.size(); i++) {
            if (todayList.get(i).isCheck) {
                return todayList.get(i);
            }
        }
        for (int i = 0; i < tomorrowList.size(); i++) {
            if (tomorrowList.get(i).isCheck) {
                return tomorrowList.get(i);
            }
        }
        return null;
    }

    private void confirm() {
        DeliveryTimeBean.DataBean.DeliveryTimesBean selectBean = getSelectBean();
        if (selectBean == null) {
            ToastUtils.showLong("请选择时间");
            return;
        }
        dataType = selectBean.getType();
        timeCode = selectBean.getCode();
        showLoading();
        ApiClient.getInstance().batchUpdateDeliveryTime(dataType, trackingNumbers, timeCode,
                new MyObserver<BaseBean>() {
                    @Override
                    protected void getDisposable(Disposable d) {
                    }

                    @Override
                    protected void onSuccess(BaseBean listBean) {
                        showLoading();
                        ToastUtils.showLong("修改时间成功");
                        setResult(RESULT_OK);
                        finish();
                    }

                    @Override
                    protected void onError(String msg) {
                        showLoading();
                    }
                });
    }

    public static void move(Activity context, ArrayList<String> trackingNumbers) {
        Intent intent = new Intent(context, DeliveryDetailChangeTimeActivity.class);
        intent.putStringArrayListExtra("trackingNumbers", trackingNumbers);
        context.startActivityForResult(intent, 0);
    }

    @OnClick(R.id.tvConfirm)
    public void onViewClicked() {
        confirm();
    }

    @OnClick(R.id.ivClose)
    public void close() {
        finish();
    }

    public static class TodayTimeAdapter extends BaseRvAdapter<TodayTimeAdapter.VH> {

        private List<DeliveryTimeBean.DataBean.DeliveryTimesBean> dataList;
        private ICallback callback;

        public TodayTimeAdapter(Context context, List<DeliveryTimeBean.DataBean.DeliveryTimesBean> dataList,
                                ICallback callback) {
            super(context);
            this.dataList = dataList;
            this.callback = callback;
        }

        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new VH(getView(R.layout.item_change_time, parent));
        }

        @Override
        public void onBindViewHolder(@NonNull VH holder, int position) {
            DeliveryTimeBean.DataBean.DeliveryTimesBean bean = dataList.get(position);
            holder.tvTime.setText(bean.getShowText());
            holder.ivMark.setVisibility(bean.isCheck ? View.VISIBLE : View.GONE);
            holder.tvTime.setBackgroundColor(bean.isCheck ? 0xFFFFE7E7 : 0xFFF5F5F5);
            holder.tvTime.setOnClickListener(v -> {
                callback.onClick(bean);
            });
        }

        public void clearCheck() {
            for (int i = 0; i < dataList.size(); i++) {
                dataList.get(i).isCheck = false;
            }
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        static class VH extends RecyclerView.ViewHolder {

            @BindView(R.id.tvTime)
            TextView tvTime;
            @BindView(R.id.ivMark)
            ImageView ivMark;

            public VH(@NonNull View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

        public interface ICallback {
            void onClick(DeliveryTimeBean.DataBean.DeliveryTimesBean bean);
        }
    }

}
