package com.dispatch.system.module.home.ui;

import com.dispatch.system.R;
import com.dispatch.system.base.BaseFragment;
import com.dispatch.system.entity.HouseReceivingListBean;
import com.dispatch.system.entity.ReceivingRecordsBean;
import com.dispatch.system.http.ApiClient;
import com.dispatch.system.http.MyObserver;
import com.dispatch.system.module.pick.adapter.MainPickUpAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import io.reactivex.disposables.Disposable;

/**
 * 楼栋详情-揽收件
 * <p>
 *
 * @author chenjunxu
 * @date 2020/7/23
 */
public class BuildingDetailReceiverFragment extends BaseFragment {

    @BindView(R.id.rvData)
    RecyclerView rvData;
    private String buildingCode;
    private String houseNumber;

    private MainPickUpAdapter adapter;
    private List<ReceivingRecordsBean> dataList = new ArrayList<>();

    public BuildingDetailReceiverFragment(String buildingCode, String houseNumber) {
        this.buildingCode = buildingCode;
        this.houseNumber = houseNumber;
    }

    @Override
    protected void initView() {
        super.initView();
        adapter = new MainPickUpAdapter(getContext(), dataList);
        rvData.setLayoutManager(new LinearLayoutManager(getContext()));
        rvData.setAdapter(adapter);
        requestData();
    }

    private void requestData() {
        showLoading();
        ApiClient.getInstance().getReceivingDetailList(buildingCode, houseNumber,
                new MyObserver<HouseReceivingListBean>() {
                    @Override
                    protected void getDisposable(Disposable d) {

                    }

                    @Override
                    protected void onSuccess(HouseReceivingListBean houseReceivingListBean) {
                        hideLoading();
                        dataList.clear();
                        dataList.addAll(houseReceivingListBean.getData().getHouseReceivingRecords());
                        adapter.notifyDataSetChanged();
                        ((BuildingDetailActivity)getActivity()).updateReceiverTab(dataList.size());
                    }

                    @Override
                    protected void onError(String msg) {
                        hideLoading();
                    }
                });
    }

    @Override
    protected int bindLayout() {
        return R.layout.fragment_building_detail_receiver;
    }


}
