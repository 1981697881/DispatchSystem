package com.dispatch.system.module.home.ui;


import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.dispatch.system.R;
import com.dispatch.system.base.BaseActivity;
import com.dispatch.system.common.ExpressHelper;
import com.dispatch.system.constants.IntentConstants;
import com.dispatch.system.entity.BaseBean;
import com.dispatch.system.entity.BuildingTreeBean;
import com.dispatch.system.entity.BuildingTreeChildBean;
import com.dispatch.system.entity.MemberBean;
import com.dispatch.system.entity.SearchMemberBean;
import com.dispatch.system.http.ApiClient;
import com.dispatch.system.http.MyObserver;
import com.dispatch.system.module.home.adapter.SearchMemberAdapter;
import com.dispatch.system.utils.BusinessHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;

/**
 * 扫描结果页面
 * <p>
 *
 * @author chenjunxu
 * @date 2020/7/11
 */
public class ScanResultActivity extends BaseActivity {

    @BindView(R.id.tvExpressName)
    TextView tvExpressName;
    @BindView(R.id.tvExpressNum)
    TextView tvExpressNum;
    @BindView(R.id.etPhoneNum)
    EditText etPhoneNum;
    @BindView(R.id.tvMatchVip)
    TextView tvMatchVip;
    @BindView(R.id.clTop)
    ConstraintLayout clTop;
    @BindView(R.id.rvData)
    RecyclerView rvData;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.viewDivider)
    View viewDivider;
    @BindView(R.id.tvPhone)
    TextView tvPhone;
    @BindView(R.id.etPhone)
    EditText etPhone;
    @BindView(R.id.etHouse)
    EditText etHouse;
    @BindView(R.id.viewDivider1)
    View viewDivider1;
    @BindView(R.id.tvAddress)
    TextView tvAddress;
    @BindView(R.id.tvInputAddress)
    TextView tvInputAddress;
    @BindView(R.id.viewDivider2)
    View viewDivider2;
    @BindView(R.id.tvCreateUser)
    TextView tvCreateUser;
    @BindView(R.id.clNoResult)
    ConstraintLayout clNoResult;
    private String expressNum;
    private SearchMemberAdapter adapter;
    private List<MemberBean> dataList = new ArrayList<>();


    @Override
    protected int bindLayout() {
        return R.layout.activity_scan_result;
    }

    @Override
    protected void initView() {
        super.initView();
        expressNum = getIntent().getStringExtra("expressNum");
        String expressName = ExpressHelper.getExpressName(expressNum);
        tvExpressName.setText(expressName);
        if ("无法识别快递公司".equals(expressName) || "其他快递".equals(expressName)) {
            tvExpressName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ExpressDialogActivity.moveForResult(ScanResultActivity.this, 111);
                }
            });
        }
        tvExpressNum.setText(expressNum);

        rvData.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SearchMemberAdapter(this, dataList);
        adapter.setOnConfirmListener(new SearchMemberAdapter.OnConfirmListener() {
            @Override
            public boolean onConfirm() {
                if ("无法识别快递公司".equals(tvExpressName.getText().toString()) || "其他快递".equals(tvExpressName.getText().toString())) {
                    ExpressDialogActivity.moveForResult(ScanResultActivity.this, 111);
                    ToastUtils.showLong("请选择快递公司");
                    return true;
                }
                return false;
            }
        });
        adapter.setExpressNum(expressNum);
        rvData.setAdapter(adapter);

        etPhoneNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 4) {
                    requestData(s.toString());
                }
            }
        });

        etPhoneNum.postDelayed(() -> {
            if (!KeyboardUtils.isSoftInputVisible(ScanResultActivity.this)) {
                KeyboardUtils.showSoftInput(etPhoneNum);
            }
        }, 300);
    }

    private void requestData(String phoneTail) {
        showLoading();
        ApiClient.getInstance().searchMember(phoneTail, new MyObserver<SearchMemberBean>() {
            @Override
            protected void getDisposable(Disposable d) {
            }

            @Override
            protected void onSuccess(SearchMemberBean bean) {
                hideLoading();
                List<MemberBean> beanList = bean.getData().getMembers();
                if (beanList.isEmpty()) {
                    clNoResult.setVisibility(View.VISIBLE);
                    rvData.setVisibility(View.GONE);
                } else {
                    clNoResult.setVisibility(View.GONE);
                    rvData.setVisibility(View.VISIBLE);
                    dataList.clear();
                    dataList.addAll(bean.getData().getMembers());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            protected void onError(String msg) {
                hideLoading();
            }
        });
    }

    /**
     * 搜索三级楼栋树
     */
    private void getBuildingTree() {
        showLoading();
        ApiClient.getInstance().getBuildingTree(new MyObserver<BuildingTreeBean>() {
            @Override
            protected void getDisposable(Disposable d) {
            }

            @Override
            protected void onSuccess(BuildingTreeBean buildingTreeBean) {
                hideLoading();
                RegisterAreaDialogActivity.moveForResult(ScanResultActivity.this,
                        buildingTreeBean, mRequestCode);
            }

            @Override
            protected void onError(String msg) {
                hideLoading();
            }
        });
    }

    private int mRequestCode = 123;
    private BuildingTreeChildBean selectBuildingTreeBean;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123 && resultCode == RESULT_OK && data != null) {
            selectBuildingTreeBean = (BuildingTreeChildBean) data.getSerializableExtra(IntentConstants.AREA);
            addressCode = selectBuildingTreeBean.getCode();
            String buildName = data.getStringExtra(IntentConstants.AREA_NAME);
            tvInputAddress.setText(buildName);
        }

        if (requestCode == 111 && resultCode == RESULT_OK && data != null) {
            String business = data.getStringExtra(IntentConstants.EXPRESS_NAME);
            adapter.setBusiness(BusinessHelper.getKey(business));
            tvExpressName.setText(business);
        }
    }

    public static void move(Context context, String expressNum) {
        Intent intent = new Intent(context, ScanResultActivity.class);
        intent.putExtra("expressNum", expressNum);
        context.startActivity(intent);
    }

    @OnClick({R.id.tvMatchVip, R.id.tvCreateUser, R.id.tvCancel, R.id.tvInputAddress, R.id.tvShowCreateUser})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvMatchVip:
                String phone = etPhoneNum.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    ToastUtils.showLong("请输入手机号");
                    return;
                }
                requestData(phone);
                break;
            case R.id.tvCreateUser:
                createUnVipUser();
                break;
            case R.id.tvInputAddress:
                getBuildingTree();
                break;
            case R.id.tvCancel:
                finish();
                break;
            case R.id.tvShowCreateUser:
                clNoResult.setVisibility(View.VISIBLE);
                rvData.setVisibility(View.GONE);
                break;
        }
    }

    private void createUnVipUser() {
        String name = etName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            ToastUtils.showLong("请输入用户名");
            return;
        }
        String phone = etPhone.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showLong("请输入手机号");
            return;
        }

        String house = etHouse.getText().toString();
        if (TextUtils.isEmpty(house)) {
            ToastUtils.showLong("请输入户号");
            return;
        }

        if (TextUtils.isEmpty(addressCode)) {
            ToastUtils.showLong("请选择地址");
            return;
        }

        showLoading();
        ApiClient.getInstance().addUnVipMember(name, phone, addressCode, house,
                new MyObserver<BaseBean>() {
                    @Override
                    protected void getDisposable(Disposable d) {
                    }

                    @Override
                    protected void onSuccess(BaseBean searchMemberBean) {
                        hideLoading();
                        ToastUtils.showLong("创建非会员用户成功");
                        finish();
                    }

                    @Override
                    protected void onError(String msg) {
                        hideLoading();
                    }
                });
    }

    private String addressCode;

    @Override
    public void finish() {
        super.finish();
        // 部分机型退出动画会有影响，导致页面闪一下
        overridePendingTransition(0, 0);
    }
}