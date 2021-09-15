package com.dispatch.system.module.home.ui;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.dispatch.system.R;
import com.dispatch.system.base.BaseActivity;
import com.dispatch.system.constants.SPConstants;
import com.dispatch.system.entity.AllBindUserBean;
import com.dispatch.system.entity.BaseBean;
import com.dispatch.system.entity.MemberBean;
import com.dispatch.system.event.AddStorageEvent;
import com.dispatch.system.event.RemoveStorageEvent;
import com.dispatch.system.http.ApiClient;
import com.dispatch.system.http.MyObserver;
import com.dispatch.system.module.home.adapter.ScanResultAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;
import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static cn.bingoogolapple.qrcode.core.BarcodeType.ONE_DIMENSION;

/**
 * 分拣扫描
 * <p>
 *
 * @author chenjunxu
 * @date 2020/7/4
 */
public class SortScanActivity extends BaseActivity implements QRCodeView.Delegate {

    @BindView(R.id.tvAddStorage)
    TextView tvAddStorage;
    @BindView(R.id.etInputExpressNum)
    EditText etInputExpressNum;
    @BindView(R.id.mZXingView)
    ZXingView mZXingView;
    @BindView(R.id.rv)
    RecyclerView rv;

    private ScanResultAdapter adapter;
    private List<MemberBean> dataList = new ArrayList<>();

    @Override
    protected int bindLayout() {
        return R.layout.activity_sort_scan;
    }

    @Override
    protected void initView() {
        super.initView();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        mZXingView.setDelegate(this);
        mZXingView.setType(ONE_DIMENSION, null);
        adapter = new ScanResultAdapter(this, dataList);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        String str = SPUtils.getInstance().getString(SPConstants.SP_ADD_STORAGE, "");
        if (!TextUtils.isEmpty(str)) {
            dataList.addAll(new Gson().fromJson(str, new TypeToken<List<MemberBean>>() {
            }.getType()));
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mZXingView.startCamera(); // 打开后置摄像头开始预览，但是并未开始识别
    }

    @Override
    protected void onResume() {
        super.onResume();
        mZXingView.startSpotAndShowRect(); // 显示扫描框，并开始识别
    }

    @Override
    protected void onPause() {
        super.onPause();
        mZXingView.stopCamera(); // 关闭摄像头预览，并且隐藏扫描框
    }

    @Override
    protected void onDestroy() {
        mZXingView.onDestroy(); // 销毁二维码扫描控件
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @OnClick({R.id.ivBack, R.id.tvAddStorage, R.id.tvConfirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.tvAddStorage:
                // 全部入库
                requestAddStorage();
                break;
            case R.id.tvConfirm:
                searchExpress();
                break;
        }
    }

    private boolean isRequest;

    /**
     * 全部入库
     */
    private void requestAddStorage() {
        if (dataList.isEmpty()) {
            ToastUtils.showLong("请扫描添加入库信息");
            return;
        }
        if (isRequest) {
            return;
        }

        isRequest = true;

        List<AllBindUserBean> beanList = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            MemberBean memberBean = dataList.get(i);
            AllBindUserBean bean = new AllBindUserBean();
            bean.setMemberCode(memberBean.getCode());
            bean.setTrackingNumber(memberBean.getExpressNum());
            bean.setBusiness(memberBean.getBusiness());
            beanList.add(bean);
        }

        String data = new Gson().toJsonTree(beanList).toString();

        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), data);
        showLoading();

        ApiClient.getInstance().bindAllMembers(body, new MyObserver<BaseBean>() {
            @Override
            protected void getDisposable(Disposable d) {
            }

            @Override
            protected void onSuccess(BaseBean baseBean) {
                ToastUtils.showLong("入库成功！");
                tvAddStorage.setText("全部入库");
                dataList.clear();
                SPUtils.getInstance().remove(SPConstants.SP_ADD_STORAGE);
                adapter.notifyDataSetChanged();
                hideLoading();
                isRequest = false;
            }

            @Override
            protected void onError(String msg) {
                hideLoading();
                isRequest = false;
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void addStorage(AddStorageEvent event) {
        dataList.add(0, event.bean);
        adapter.notifyDataSetChanged();
        tvAddStorage.setText(String.format("全部入库%d", dataList.size()));
        SPUtils.getInstance().put(SPConstants.SP_ADD_STORAGE, new Gson().toJson(dataList));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void removeStorage(RemoveStorageEvent event) {
        tvAddStorage.setText(String.format("全部入库%d", dataList.size()));
    }

    /**
     * 确认快递单号
     */
    private void searchExpress() {
        String expressNum = etInputExpressNum.getText().toString();
        if (TextUtils.isEmpty(expressNum)) {
            ToastUtils.showLong("请输入快递单号");
            return;
        }
        if (expressNum.length() < 10 || expressNum.length() > 25) {
            ToastUtils.showLong("运单号长度为10-25位！");
            return;
        }
        etInputExpressNum.setText(null);
        ScanResultActivity.move(this, expressNum);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
//        mZXingView.startSpot(); // 开始识别
        if (TextUtils.isEmpty(result) || (result.length() < 10 || result.length() > 25) ) {
            ToastUtils.showLong("运单号长度为10-25位！");
            mZXingView.startSpot(); // 开始识别
            return;
        }
        ScanResultActivity.move(this, result);
        etInputExpressNum.setText(null);
    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {

    }

    @Override
    public void onScanQRCodeOpenCameraError() {

    }
}
