package com.dispatch.system.module.pick;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dispatch.system.R;
import com.dispatch.system.base.BaseFragment;
import com.dispatch.system.entity.MinePickUpBean;
import com.dispatch.system.entity.ReceivingRecordsBean;
import com.dispatch.system.http.ApiClient;
import com.dispatch.system.http.MyObserver;
import com.dispatch.system.module.MainActivity;
import com.dispatch.system.module.pick.adapter.MainPickUpAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;

/**
 * 揽件页面
 * <p>
 *
 * @author chenjunxu
 * @date 2020/7/4
 */
public class MainPickUpFragment extends BaseFragment {

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private MainPickUpAdapter adapter;
    private List<ReceivingRecordsBean> dataList = new ArrayList<>();

    @Override
    protected int bindLayout() {
        return R.layout.fragment_pick_up;
    }

    @Override
    protected void initView() {
        super.initView();
        adapter = new MainPickUpAdapter(getContext(), dataList);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(adapter);

        refreshLayout.setEnableLoadmore(true);
        refreshLayout.setEnableLoadmoreWhenContentNotFull(true);
        refreshLayout.setOnRefreshListener(refreshLayout -> requestData(true));
        refreshLayout.setOnLoadmoreListener(loadMoreListener -> requestData(false));
        requestData(true);
    }

    private int pageIndex = 1;

    public void requestData(boolean isRefresh) {
        if (isRefresh) {
            pageIndex = 1;
        }
        ApiClient.getInstance().getMinePickUpListPage("10", String.valueOf(pageIndex),
                true, new MyObserver<MinePickUpBean>() {
                    @Override
                    protected void getDisposable(Disposable d) {
                    }

                    @Override
                    protected void onSuccess(MinePickUpBean minePickUpBean) {
                        if (isRefresh) {
                            dataList.clear();
                        }
                        List<ReceivingRecordsBean> records = minePickUpBean.getData().getReceivingRecords();
                        if (records.size() > 0) {
                            pageIndex++;
                        }
                        dataList.addAll(records);
                        adapter.notifyDataSetChanged();
                        refreshLayout.finishRefresh(500);
                        refreshLayout.finishLoadmore(500);
                        if (isRefresh) {
                            ((MainActivity) getActivity()).hideRedDot();
                        }
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
