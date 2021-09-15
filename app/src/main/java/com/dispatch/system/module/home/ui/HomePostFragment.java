package com.dispatch.system.module.home.ui;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.dispatch.system.R;
import com.dispatch.system.base.BaseFragment;
import com.dispatch.system.entity.HomeTaskGroup;
import com.dispatch.system.entity.MineTodayTaskBean;
import com.dispatch.system.http.ApiClient;
import com.dispatch.system.http.MyObserver;
import com.dispatch.system.module.home.adapter.HomeTaskAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;

/**
 * 驿站的今日任务
 * <p>
 * @author chenjunxu
 * @date 2020/7/18
 */
public class HomePostFragment extends BaseFragment {

    @BindView(R.id.rv)
    RecyclerView rv;

    private HomeTaskAdapter adapter;
    List<HomeTaskGroup> group = new ArrayList<>();


    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    @Override
    protected int bindLayout() {
        return R.layout.fragment_home_post_task;
    }

    @Override
    protected void initView() {
        super.initView();
        refreshLayout.setEnableLoadmore(false);
        refreshLayout.setEnableLoadmoreWhenContentNotFull(true);
        refreshLayout.setOnRefreshListener(refreshLayout -> requestData());
    }

    @Override
    public void onResume() {
        super.onResume();
        requestData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (rv != null) {
                requestData();
            }
        }
    }

    private void requestData() {
        ApiClient.getInstance().getJsdTodayTask(new MyObserver<MineTodayTaskBean>() {
            @Override
            protected void getDisposable(Disposable d) {
            }

            @Override
            protected void onSuccess(MineTodayTaskBean mineTodayTaskBean) {
                refreshLayout.finishRefresh(500);
                if (mineTodayTaskBean == null
                        || mineTodayTaskBean.getData() == null
                        || mineTodayTaskBean.getData().getDeliveryTimeTasks() == null) {
                    if (mineTodayTaskBean != null) {
                        ToastUtils.showLong(mineTodayTaskBean.getDesc());
                    }
                    return;
                }
                List<MineTodayTaskBean.DataBean.DeliveryTimeTasksBean> tasks
                        = mineTodayTaskBean.getData().getDeliveryTimeTasks();
                group.clear();
                for (int i = 0; i < tasks.size(); i++) {
                    for (int j = 0; j < tasks.get(i).getBuildingTaskCountList().size(); j++) {
                        tasks.get(i).getBuildingTaskCountList().get(j).deliveryTimeCode = tasks.get(i).getCode();
                    }
                    HomeTaskGroup item = new HomeTaskGroup(
                            tasks.get(i).getTimeInterval(),
                            tasks.get(i).getBuildingTaskCountList());
                    group.add(item);
                }
                if (adapter == null) {
                    adapter = new HomeTaskAdapter(getContext(), group, false, false,true);
                    rv.setLayoutManager(new LinearLayoutManager(getContext()));
                    rv.setAdapter(adapter);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            protected void onError(String msg) {
                refreshLayout.finishRefresh(500);
            }

        });
    }
}
