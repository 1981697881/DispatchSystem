package com.dispatch.system.module.login;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.dispatch.system.BuildConfig;
import com.dispatch.system.R;
import com.dispatch.system.base.BaseActivity;
import com.dispatch.system.constants.SPConstants;
import com.dispatch.system.entity.LoginBean;
import com.dispatch.system.http.ApiClient;
import com.dispatch.system.http.BaseObserver;
import com.dispatch.system.module.MainActivity;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import okhttp3.Headers;
import retrofit2.Response;

/**
 * 登录页面
 * <p>
 *
 * @author chenjunxu
 * @date 2020/7/4
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.etAccount)
    EditText etAccount;
    @BindView(R.id.etCode)
    EditText etCode;

    @Override
    protected int bindLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        super.initView();

        String lastPhone = SPUtils.getInstance().getString(SPConstants.PHONE_NUM, "");
        String lastPwd = SPUtils.getInstance().getString(SPConstants.USER_PWD, "");
        if (!TextUtils.isEmpty(lastPhone)) {
            etAccount.setText(lastPhone);
        }
        if (!TextUtils.isEmpty(lastPwd)) {
            etCode.setText(lastPwd);
        }


        // TODO: 2020/7/18 测试
        if (BuildConfig.DEBUG) {
//            etAccount.setText("13058141139");
//            etCode.setText("123456");
//            etAccount.setText("18816785821");
//            etCode.setText("123456");

            // 测试环境
//            etAccount.setText("13058141139");
//            etCode.setText("123456");

//            etAccount.setText("19898695680");
//            etCode.setText("xsd123456");

//            etAccount.setText("13058141139");
//            etCode.setText("xsd123456");

            etAccount.setText("17820424418");
            etCode.setText("123456");
        }
    }

    /**
     * 登录
     */
    private void login() {
        // 帐号判断
        final String account = etAccount.getText().toString();
        if (TextUtils.isEmpty(account)) {
            ToastUtils.showLong("请输入帐号");
            return;
        }

        // 密码判断
        String pwd = etCode.getText().toString();
        if (TextUtils.isEmpty(pwd)) {
            ToastUtils.showLong("请输入密码");
            return;
        }
        showLoading();
        ApiClient.getInstance().login(account, pwd, new BaseObserver<Response<LoginBean>>() {

            @Override
            public void onNext(@NonNull Response<LoginBean> resultBean) {
                hideLoading();
                LoginBean loginBean = resultBean.body();
                if (loginBean == null) {
                    return;
                }

                if (loginBean.getStatus() == 200 && loginBean.getData() != null) {
                    clearOldMsg();

                    LoginBean.DataBean dataBean = loginBean.getData();
                    // 需要获取sessionid
                    Headers headers = resultBean.headers();
                    String[] split = headers.toString().split("JSESSIONID=");
                    if (split.length <= 1) {
                        ToastUtils.showLong("登录失败，服务器异常");
                        return;
                    }
                    String index = split[1];
                    String sessionId = index.substring(0, index.indexOf(";"));
                    Log.e("SESSION_ID", "SESSION_ID =" + sessionId);
                    SPUtils.getInstance().put(SPConstants.SESSION_ID, sessionId);

                    String userId = String.valueOf(dataBean.getPassport().getAccount());
                    // 把个人信息存储起来
                    SPUtils.getInstance().put(SPConstants.USER_ID, userId);
                    SPUtils.getInstance().put(SPConstants.NICK_NAME, dataBean.getPassport().getNickname());

                    SPUtils.getInstance().put(SPConstants.PHONE_NUM, account);
                    SPUtils.getInstance().put(SPConstants.USER_PWD, pwd);

                    ToastUtils.showLong("登录成功");

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    ToastUtils.showLong(loginBean.getDesc());
                }
            }

            @Override
            protected void getDisposable(Disposable d) {
//                LoginActivity.this.d = d;
            }

            @Override
            protected void onError(String msg) {
                ToastUtils.showLong(msg);
                hideLoading();
            }

            @Override
            protected void onCancel() {
                super.onCancel();
            }

            @Override
            protected void onSuccess(Response<LoginBean> loginBeanResponse) {
            }
        });
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

    public static void move(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    @OnClick(R.id.tvLogin)
    public void onLogin() {
        login();
    }
}
