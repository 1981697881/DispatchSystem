package com.dispatch.system.module.home.ui;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.dispatch.system.R;
import com.dispatch.system.base.BaseActivity;
import com.dispatch.system.entity.BaseBean;
import com.dispatch.system.entity.BuildingTaskListBean;
import com.dispatch.system.entity.MineTodayTaskBean;
import com.dispatch.system.http.ApiClient;
import com.dispatch.system.http.MyObserver;
import com.dispatch.system.module.home.adapter.BuildingTaskListAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;

/**
 * 楼栋任务列表
 * <p>
 *
 * @author chenjunxu
 * @date 2020/7/18
 */
public  class BuildingTaskListActivity extends BaseActivity {

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvTitleEnglish)
    TextView tvTitleEnglish;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private String buildingCode;
    private String completeName;
    private String timeCode;
    private BuildingTaskListAdapter adapter;
    private List<BuildingTaskListBean.DataBean.HouseTasksBean> datas = new ArrayList<>();
    private MineTodayTaskBean.DataBean.DeliveryTimeTasksBean.BuildingTaskCountListBean bean;
    private boolean isMine;
    private boolean isTimeOut;
    private boolean isJsd;

    @Override
    protected int bindLayout() {
        return R.layout.activity_building_task_list;
    }

    @Override
    protected void initView() {
        super.initView();
        bean = (MineTodayTaskBean.DataBean.DeliveryTimeTasksBean.BuildingTaskCountListBean) getIntent().getSerializableExtra("bean");
        isMine = getIntent().getBooleanExtra("isMine", true);
        isTimeOut = getIntent().getBooleanExtra("isTimeOut", false);
        isJsd = getIntent().getBooleanExtra("isJsd", false);
        buildingCode = bean.getCode();
        completeName = bean.getName();
        timeCode = bean.deliveryTimeCode;
        String name = bean.getName();
        String[] s = name.split(" ", 2);
        if (s.length > 0) {
            tvTitle.setText(s[0]);
            if (s.length > 1) {
                tvTitleEnglish.setText(s[1]);
            } else {
                tvTitleEnglish.setText("");
            }
        } else {
            tvTitle.setText("");
            tvTitleEnglish.setText("");
        }
        adapter = new BuildingTaskListAdapter(this, datas, isTimeOut, isMine,isJsd);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
        requestData();

        ivBack.setVisibility(View.VISIBLE);

        adapter.setOnClickMyTextView(new BuildingTaskListAdapter.OnClickMyTextView() {
            @Override
            public void myTextViewClick(String userAddress, String jsdOrderNumber, String userPhone) {
                comfimData(userAddress,jsdOrderNumber,userPhone);
            }
        });
        refreshLayout.setEnableLoadmore(false);
        refreshLayout.setEnableLoadmoreWhenContentNotFull(true);
        refreshLayout.setOnRefreshListener(refreshLayout -> requestData());
    }

    @OnClick(R.id.ivBack)
    void back() {
        finish();
    }

    private void requestData() {
        if (isMine) {
            ApiClient.getInstance().getMineBuildingTaskTaskList(buildingCode, timeCode,
                    new MyObserver<BuildingTaskListBean>() {
                        @Override
                        protected void getDisposable(Disposable d) {
                        }

                        @Override
                        protected void onSuccess(BuildingTaskListBean listBean) {
                            refreshLayout.finishRefresh(500);
                            datas.clear();
                            datas.addAll(listBean.getData().getHouseTasks());
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        protected void onError(String msg) {
                            refreshLayout.finishRefresh(500);
                        }
                    });
        } else if (isTimeOut) {
            ApiClient.getInstance().getMineBuildingTimeoutTaskTaskList(buildingCode,
                    new MyObserver<BuildingTaskListBean>() {
                        @Override
                        protected void getDisposable(Disposable d) {
                        }

                        @Override
                        protected void onSuccess(BuildingTaskListBean listBean) {
                            refreshLayout.finishRefresh(500);
                            datas.clear();
                            datas.addAll(listBean.getData().getHouseTasks());
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        protected void onError(String msg) {
                            refreshLayout.finishRefresh(500);
                        }
                    });
        } else if (isJsd) {
            ApiClient.getInstance().getJsdBuildingaskList(buildingCode,completeName,
                    new MyObserver<BuildingTaskListBean>() {
                        @Override
                        protected void getDisposable(Disposable d) {
                        }
                        @Override
                        protected void onSuccess(BuildingTaskListBean listBean) {
                            refreshLayout.finishRefresh(500);
                            datas.clear();
                            datas.addAll(listBean.getData().getHouseTasks());
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        protected void onError(String msg) {
                            refreshLayout.finishRefresh(500);
                        }
                    });
        } else {
            ApiClient.getInstance().getBuildingTaskList(buildingCode, timeCode,
                    new MyObserver<BuildingTaskListBean>() {
                        @Override
                        protected void getDisposable(Disposable d) {
                        }

                        @Override
                        protected void onSuccess(BuildingTaskListBean listBean) {
                            refreshLayout.finishRefresh(500);
                            datas.clear();
                            datas.addAll(listBean.getData().getHouseTasks());
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        protected void onError(String msg) {
                            refreshLayout.finishRefresh(500);
                        }
                    });
        }
    }

    public void comfimData(String userAddress, String jsdOrderNumber, String userPhone) {
        ApiClient.getInstance().confirmJsd(userAddress, jsdOrderNumber,userPhone,
                new MyObserver<BuildingTaskListBean>() {
                    @Override
                    protected void getDisposable(Disposable d) {

                    }
                    @Override
                    protected void onSuccess(BuildingTaskListBean listBean) {
                        refreshLayout.finishRefresh(500);
                        datas.clear();
                        datas.addAll(listBean.getData().getHouseTasks());
                        adapter.notifyDataSetChanged();
                    }
                    @Override
                    protected void onError(String msg) {
                        hideLoading();
                    }
                });
    }
    /**
     * 跳转到当前 Activity
     */
    public static void move(Context context, MineTodayTaskBean.DataBean.DeliveryTimeTasksBean.BuildingTaskCountListBean bean,
                            boolean isMine, boolean isTimeOut, boolean isJsd) {
        Intent intent = new Intent(context, BuildingTaskListActivity.class);
        intent.putExtra("bean", bean);
        intent.putExtra("isMine", isMine);
        intent.putExtra("isTimeOut", isTimeOut);
        intent.putExtra("isJsd", isJsd);
        context.startActivity(intent);
    }
}
