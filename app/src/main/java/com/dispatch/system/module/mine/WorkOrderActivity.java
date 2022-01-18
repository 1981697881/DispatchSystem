package com.dispatch.system.module.mine;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dispatch.system.R;
import com.dispatch.system.base.BaseActivity;
import com.dispatch.system.entity.WorkOrderBean;
import com.dispatch.system.http.ApiClient;
import com.dispatch.system.http.MyObserver;
import com.dispatch.system.module.pick.adapter.WorkOrderAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;

/**
 * 我的工单
 */
public class WorkOrderActivity extends BaseActivity {

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.ivBack)
    View ivBack;
    private WorkOrderAdapter adapter;
    private List<WorkOrderBean.DataBean.workOrderBean> dataList = new ArrayList<WorkOrderBean.DataBean.workOrderBean>();

    @Override
    protected int bindLayout() {
        return R.layout.fragment_work_order;
    }

    @Override
    protected void initView() {
        super.initView();
        ivBack.setVisibility(View.VISIBLE);
        adapter = new WorkOrderAdapter(this, dataList);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
        refreshLayout.setEnableLoadmore(true);
        refreshLayout.setEnableLoadmoreWhenContentNotFull(true);
        refreshLayout.setOnRefreshListener(refreshLayout -> requestData(true));
        refreshLayout.setOnLoadmoreListener(loadMoreListener -> requestData(false));
        requestData(true);
    }

    private int pageIndex = 1;
    @OnClick(R.id.ivBack)
    public void onViewClicked() {
        finish();
    }
    public void requestData(boolean isRefresh) {
        /*if (isRefresh) {
            pageIndex = 1;
        }*/
        ApiClient.getInstance().getWorkOrderListPage("", new MyObserver<WorkOrderBean>() {
                    @Override
                    protected void getDisposable(Disposable d) {
                    }

                    @Override
                    protected void onSuccess(WorkOrderBean workOrderBean) {
                        dataList.clear();
                        /*if (records.size() > 0) {
                            pageIndex++;
                        }*/
                        dataList.addAll(workOrderBean.getData().getWorkOrder());
                        adapter.notifyDataSetChanged();
                        refreshLayout.finishRefresh(500);
                        refreshLayout.finishLoadmore(500);
                    }
                    @Override
                    protected void onError(String msg) {
                        refreshLayout.finishRefresh(500);
                        refreshLayout.finishLoadmore(500);
                    }
                });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            requestData(true);
        }
    }
}
