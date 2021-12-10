package com.dispatch.system.module.home.ui;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.dispatch.system.R;
import com.dispatch.system.base.BaseFragment;
import com.dispatch.system.entity.BuildingDetailDeliveryBean;
import com.dispatch.system.http.ApiClient;
import com.dispatch.system.http.MyObserver;
import com.dispatch.system.module.home.adapter.BuildingDetailDeliveryAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;

/**
 * 楼栋详情-派送件
 * <p>
 *
 * @author chenjunxu
 * @date 2020/7/23
 */
public class BuildingDetailDeliveryTimeoutFragment extends BaseFragment {

    @BindView(R.id.rvData)
    RecyclerView rvData;
    @BindView(R.id.tvErrorPost)
    TextView tvErrorPost;
    @BindView(R.id.tvChangeTime)
    TextView tvChangeTime;
    @BindView(R.id.tvUserSign)
    TextView tvUserSign;
    @BindView(R.id.tvCancel)
    TextView tvCancel;
    @BindView(R.id.tvConfirm)
    TextView tvConfirm;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private String buildingCode;
    private String houseNumber;
    private BuildingDetailDeliveryAdapter adapter;
    private List<BuildingDetailDeliveryBean.DataBean.HouseDeliveryRecordsBean> dataList = new ArrayList<>();

    public BuildingDetailDeliveryTimeoutFragment(String buildingCode, String houseNumber) {
        this.buildingCode = buildingCode;
        this.houseNumber = houseNumber;
    }

    @Override
    protected int bindLayout() {
        return R.layout.fragment_building_detail_delivery;
    }

    @Override
    protected void initView() {
        super.initView();
        rvData.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new BuildingDetailDeliveryAdapter(getActivity(), dataList);


        refreshLayout.setEnableLoadmore(true);
        refreshLayout.setEnableLoadmoreWhenContentNotFull(true);
        refreshLayout.setOnRefreshListener(refreshLayout -> requestData(true));
        refreshLayout.setOnLoadmoreListener(loadMoreListener -> requestData(false));

        rvData.setAdapter(adapter);

        requestData(true);
    }

    @OnClick({R.id.tvErrorPost, R.id.tvChangeTime, R.id.tvUserSign, R.id.tvCancel, R.id.tvConfirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvErrorPost:
                type = Type.TYPE_ERROR;
                checkStatus(true);
                adapter.setCheckMode(true);
                break;
            case R.id.tvChangeTime:
                type = Type.TYPE_CHANGE_TIME;
                checkStatus(true);
                adapter.setCheckMode(true);
                break;
            case R.id.tvUserSign:
                boolean isNeedShow = false;
                for (int i = 0; i < dataList.size(); i++) {
                    String state = dataList.get(i).getState();
                    if ("WAIT".equals(state) || "DELIVERING".equals(state) || "EXCEPTION".equals(state) || "TIMEOUT".equals(state)) {
                        // 待派送
                        isNeedShow = true;
                        dataList.get(i).setCheck(false);
                        dataList.get(i).setOpenCheckMode(true);
                    }
                }
                if (!isNeedShow) {
                    ToastUtils.showLong("当前没有待派送任务");
                    return;
                }
                type = Type.TYPE_SIGN;
                checkStatus(true);
                adapter.notifyDataSetChanged();
                break;
            case R.id.tvCancel:
                type = Type.TYPE_NONE;
                checkStatus(false);
                adapter.setCheckMode(false);
                break;
            case R.id.tvConfirm:
                if (type == Type.TYPE_ERROR) {
                    ErrorPartReasonActivity.move(getActivity(), getTrackNumbers());
                } else if (type == Type.TYPE_CHANGE_TIME) {
                    DeliveryDetailChangeTimeActivity.move(getActivity(), getTrackNumbers());
                } else if (type == Type.TYPE_SIGN) {
                    HandSignActivity.move(getActivity(), getTrackNumbers());
                }
//                handler.postDelayed(() -> adapter.setCheckMode(false), 200);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            requestData();
//            type = Type.TYPE_NONE;
//            adapter.setCheckMode(false);
//            adapter.notifyDataSetChanged();
//        }
    }

    public void update() {
        requestData(true);
        type = Type.TYPE_NONE;
        adapter.setCheckMode(false);
        adapter.notifyDataSetChanged();
        checkStatus(false);
    }

    private ArrayList<String> getTrackNumbers() {
        ArrayList<String> str = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            if (dataList.get(i).isCheck()) {
                str.add(dataList.get(i).getTrackingNumber());
            }
        }
        return str;
    }

    private static Handler handler = new Handler();

    private Type type = Type.TYPE_NONE;

    private enum Type {
        TYPE_ERROR,
        TYPE_CHANGE_TIME,
        TYPE_SIGN,
        TYPE_NONE
    }

    private void checkStatus(boolean showCheck) {
        if (showCheck) {
            tvErrorPost.setVisibility(View.GONE);
            tvChangeTime.setVisibility(View.GONE);
            tvUserSign.setVisibility(View.GONE);
            tvCancel.setVisibility(View.VISIBLE);
            tvConfirm.setVisibility(View.VISIBLE);
        } else {
            tvErrorPost.setVisibility(View.VISIBLE);
            tvChangeTime.setVisibility(View.VISIBLE);
            tvUserSign.setVisibility(View.VISIBLE);
            tvCancel.setVisibility(View.GONE);
            tvConfirm.setVisibility(View.GONE);
        }
    }

    private int pageIndex = 1;

    private void requestData(boolean isRefresh) {
        if (isRefresh) {
            pageIndex = 1;
        }
        showLoading();
        ApiClient.getInstance().getDeliveryDetailTimeoutList(buildingCode, houseNumber, pageIndex,
                new MyObserver<BuildingDetailDeliveryBean>() {
                    @Override
                    protected void getDisposable(Disposable d) {
                    }
                    @Override
                    protected void onSuccess(BuildingDetailDeliveryBean bean) {
                        hideLoading();
                        if (isRefresh) {
                            dataList.clear();
                        }
                        List<BuildingDetailDeliveryBean.DataBean.HouseDeliveryRecordsBean> beanList = bean.getData().getHouseDeliveryRecords();
                        if (beanList.size() > 0) {
                            pageIndex++;
                            if (!isRefresh) {
                                if (type == Type.TYPE_ERROR || type == Type.TYPE_CHANGE_TIME || type == Type.TYPE_SIGN) {
                                    for (int i = 0; i < beanList.size(); i++) {
                                        String state = dataList.get(i).getState();
                                        if ("WAIT".equals(state) || "DELIVERING".equals(state) || "EXCEPTION".equals(state) || "TIMEOUT".equals(state)) {
                                            beanList.get(i).setOpenCheckMode(true);
                                            beanList.get(i).setCheck(false);
                                        }
                                    }
                                }
                            }
                        }
                        dataList.addAll(beanList);
                        refreshLayout.finishLoadmore(500);
                        refreshLayout.finishRefresh(500);
                        adapter.notifyDataSetChanged();
                        ((BuildingDetailTimeOutActivity) getActivity()).updatePostTab(dataList.size());
                    }

                    @Override
                    protected void onError(String msg) {
                        refreshLayout.finishLoadmore(500);
                        refreshLayout.finishRefresh(500);
                        hideLoading();
                    }
                });
    }
}
