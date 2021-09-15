package com.dispatch.system.module.home.ui;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.dispatch.system.R;
import com.dispatch.system.base.BaseActivity;
import com.dispatch.system.entity.BaseBean;
import com.dispatch.system.entity.ExceptionTemplateBean;
import com.dispatch.system.http.ApiClient;
import com.dispatch.system.http.MyObserver;
import com.dispatch.system.module.home.adapter.ScanExceptionAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;

/**
 * 异常件原因选择
 * <p>
 *
 * @author chenjunxu
 * @date 2020/7/11
 */
public class ErrorPartReasonActivity extends BaseActivity {

    @BindView(R.id.rvData)
    RecyclerView rvData;
    private ScanExceptionAdapter adapter;
    private List<ExceptionTemplateBean.DataBean.ExceptionTemplatesBean> dataList = new ArrayList<>();
    private ArrayList<String> trackingNumbers;
    private ExceptionTemplateBean.DataBean.ExceptionTemplatesBean selectBean;

    @Override
    protected int bindLayout() {
        return R.layout.activity_error_parts_reason;
    }

    @Override
    protected void initView() {
        super.initView();
        rvData.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ScanExceptionAdapter(this, dataList, bean -> {
            selectBean = bean;
        });
        rvData.setAdapter(adapter);
        trackingNumbers = getIntent().getStringArrayListExtra("trackingNumbers");
        getException();
    }

    private void getException() {
        showLoading();
        ApiClient.getInstance().getException(new MyObserver<ExceptionTemplateBean>() {
            @Override
            protected void getDisposable(Disposable d) {
            }

            @Override
            protected void onSuccess(ExceptionTemplateBean exceptionTemplateBean) {
                hideLoading();
                dataList.clear();
                dataList.addAll(exceptionTemplateBean.getData().getExceptionTemplates());
                if (!dataList.isEmpty()) {
                    dataList.get(0).setCheck(true);
                    selectBean = dataList.get(0);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            protected void onError(String msg) {
                hideLoading();
            }
        });
    }

    public static void move(Activity context, ArrayList<String> trackingNumbers) {
        Intent intent = new Intent(context, ErrorPartReasonActivity.class);
        intent.putStringArrayListExtra("trackingNumbers", trackingNumbers);
        context.startActivityForResult(intent, 0);
    }

    @OnClick({R.id.ivClose, R.id.tvCancel, R.id.tvConfirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivClose:
            case R.id.tvCancel:
                finish();
                break;
            case R.id.tvConfirm:
                addBatchErrorReason();
                break;
        }
    }

    private void addBatchErrorReason() {
        if (selectBean == null) {
            ToastUtils.showLong("请选中异常件原因");
            return;
        }

        showLoading();
        ApiClient.getInstance().addBatchDeliveryException(trackingNumbers, selectBean.getCode(),
                new MyObserver<BaseBean>() {
                    @Override
                    protected void getDisposable(Disposable d) {

                    }

                    @Override
                    protected void onSuccess(BaseBean baseBean) {
                        hideLoading();
                        ToastUtils.showLong("添加异常信息成功");
                        setResult(RESULT_OK);
                        finish();
                    }

                    @Override
                    protected void onError(String msg) {
                        hideLoading();
                    }
                });
    }
}
