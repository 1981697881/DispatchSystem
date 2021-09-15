package com.dispatch.system.module.home.ui;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dispatch.system.R;
import com.dispatch.system.base.BaseFragment;
import com.dispatch.system.entity.HouseReceivingListBean;
import com.dispatch.system.entity.ReceivingRecordsBean;
import com.dispatch.system.http.ApiClient;
import com.dispatch.system.http.MyObserver;
import com.dispatch.system.module.pick.adapter.MainPickUpAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;

/**
 * 楼栋详情-揽收件
 * <p>
 *
 * @author chenjunxu
 * @date 2020/7/23
 */
public class BuildingDetailTimeoutReceiverFragment extends BaseFragment {

    @BindView(R.id.rvData)
    RecyclerView rvData;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private String buildingCode;
    private String houseNumber;

    private MainPickUpAdapter adapter;
    private List<ReceivingRecordsBean> dataList = new ArrayList<>();

    public BuildingDetailTimeoutReceiverFragment(String buildingCode, String houseNumber) {
        this.buildingCode = buildingCode;
        this.houseNumber = houseNumber;
    }

    @Override
    protected void initView() {
        super.initView();
        adapter = new MainPickUpAdapter(getContext(), dataList);
        rvData.setLayoutManager(new LinearLayoutManager(getContext()));
        rvData.setAdapter(adapter);

        refreshLayout.setEnableLoadmore(true);
        refreshLayout.setEnableLoadmoreWhenContentNotFull(true);
        refreshLayout.setOnRefreshListener(refreshLayout -> requestData(true));
        refreshLayout.setOnLoadmoreListener(loadMoreListener -> requestData(false));

        requestData(true);
    }

    private int pageIndex = 1;

    private void requestData(boolean isRefresh) {
        if (isRefresh) {
            pageIndex = 1;
        }
        showLoading();
        ApiClient.getInstance().getReceivingDetailTimeoutList(buildingCode, houseNumber, String.valueOf(pageIndex),
                new MyObserver<HouseReceivingListBean>() {
                    @Override
                    protected void getDisposable(Disposable d) {

                    }

                    @Override
                    protected void onSuccess(HouseReceivingListBean houseReceivingListBean) {
                        hideLoading();
                        if (isRefresh) {
                            dataList.clear();
                        }
                        List<ReceivingRecordsBean> beanList = houseReceivingListBean.getData().getHouseReceivingRecords();
                        if (beanList.size() > 0) {
                            pageIndex++;
                        }
                        refreshLayout.finishLoadmore(500);
                        refreshLayout.finishRefresh(500);
                        dataList.addAll(beanList);
                        adapter.notifyDataSetChanged();
                        ((BuildingDetailTimeOutActivity) getActivity()).updateReceiverTab(dataList.size());
                    }

                    @Override
                    protected void onError(String msg) {
                        refreshLayout.finishLoadmore(500);
                        refreshLayout.finishRefresh(500);
                        hideLoading();
                    }
                });
    }

    @Override
    protected int bindLayout() {
        return R.layout.fragment_building_detail_receiver;
    }


}
