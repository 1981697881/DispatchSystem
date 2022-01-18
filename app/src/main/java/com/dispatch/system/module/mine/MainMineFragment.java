package com.dispatch.system.module.mine;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.dispatch.system.R;
import com.dispatch.system.base.BaseFragment;
import com.dispatch.system.constants.SPConstants;
import com.dispatch.system.entity.BaseBean;
import com.dispatch.system.entity.EmployeeInfoBean;
import com.dispatch.system.entity.WorkOrderBean;
import com.dispatch.system.http.ApiClient;
import com.dispatch.system.http.MyObserver;
import com.dispatch.system.module.login.LoginActivity;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;

/**
 * 我的页面
 * <p>
 *
 * @author chenjunxu
 * @date 2020/7/4
 */
public class MainMineFragment extends BaseFragment {

    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvPhone)
    TextView tvPhone;
    @BindView(R.id.tvWorkNum)
    TextView tvWorkNum;
    @BindView(R.id.workOrderNum)
    TextView workOrderNum;
    @BindView(R.id.viewWorkNumCard)
    ImageView viewWorkNumCard;

    @Override
    protected int bindLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {
        super.initView();
        String name = SPUtils.getInstance().getString(SPConstants.NICK_NAME);
        String phone = SPUtils.getInstance().getString(SPConstants.PHONE_NUM);
        tvName.setText(name);
        tvPhone.setText(phone);
        requestData();
        requestWorkOrderNum();
    }
    @Override
    public void onResume() {
        super.onResume();
        requestWorkOrderNum();//刷新数据
    }

    private void requestWorkOrderNum() {
        ApiClient.getInstance().getWorkOrderListPage("0",new MyObserver<WorkOrderBean>() {
            @Override
            protected void getDisposable(Disposable d) {
            }

            @Override
            protected void onSuccess(WorkOrderBean workOrderBean) {
                try {
                    workOrderNum.setText(String.valueOf(workOrderBean.getData().getWorkOrder().size()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void onError(String msg) {
            }
        });
    }
    private void requestData() {
        ApiClient.getInstance().getEmployeeInfo(new MyObserver<EmployeeInfoBean>() {
            @Override
            protected void getDisposable(Disposable d) {
            }

            @Override
            protected void onSuccess(EmployeeInfoBean employeeInfoBean) {
                try {
                    tvWorkNum.setText(employeeInfoBean.getData().getEpsEmployee().getNumber());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void onError(String msg) {
            }
        });
    }


    /**
     * 退出登录
     */
    @OnClick(R.id.tvLogout)
    public void logout() {
        showLoading();
        ApiClient.getInstance().logout(new MyObserver<BaseBean>() {
            @Override
            protected void getDisposable(Disposable d) {
            }

            @Override
            protected void onSuccess(BaseBean baseBean) {
                hideLoading();
                ToastUtils.showLong("退出登录成功");
                clearOldMsg();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                getActivity().startActivity(intent);
                getActivity().finish();
            }

            @Override
            protected void onError(String msg) {
                hideLoading();
                clearOldMsg();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });
    }
    /**
     * 工单信息
     */
    @OnClick(R.id.workOrder)
    public void workOrder() {
        Intent intent = new Intent(getActivity(), WorkOrderActivity.class);
        getActivity().startActivity(intent);
    }

    /**
     * 登录成功，清理旧数据
     */
    private void clearOldMsg() {
        SPUtils.getInstance().remove(SPConstants.SESSION_ID);
        SPUtils.getInstance().remove(SPConstants.SCREEN_ON);
        SPUtils.getInstance().remove(SPConstants.USER_ID);
        SPUtils.getInstance().remove(SPConstants.UUID);
        SPUtils.getInstance().remove(SPConstants.NICK_NAME);
        SPUtils.getInstance().remove(SPConstants.VACOLIST_ID);
    }
}
