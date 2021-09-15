package com.dispatch.system.module;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.dispatch.system.R;
import com.dispatch.system.base.BaseActivity;
import com.dispatch.system.entity.BusinessBean;
import com.dispatch.system.entity.NewListBean;
import com.dispatch.system.http.ApiClient;
import com.dispatch.system.http.MyObserver;
import com.dispatch.system.module.home.MainHomeFragment;
import com.dispatch.system.module.home.ui.HomeMineTaskFragment;
import com.dispatch.system.module.mine.MainMineFragment;
import com.dispatch.system.module.pick.MainPickUpFragment;
import com.dispatch.system.utils.BusinessHelper;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 主页面
 * <p>
 *
 * @author chenjunxu
 * @date 2020/7/4
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.llHome)
    LinearLayout llHome;
    @BindView(R.id.llPickUp)
    LinearLayout llPickUp;
    @BindView(R.id.llMine)
    LinearLayout llMine;
    @BindView(R.id.viewDot)
    View viewDot;

    /**
     * 记录上次选中的tab下标
     */
    private int lastSelectTab = -1;

    private MainHomeFragment homeFragment;
    private MainMineFragment mineFragment;
    private MainPickUpFragment pickUpFragment;
    private HomeMineTaskFragment homeMineTaskFragment;
    private Disposable subscribe;

    @Override
    protected int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        super.initView();
        clickHome();
        try {
            subscribe = Observable.interval(0, 1, TimeUnit.MINUTES)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            ApiClient.getInstance().getMessageNotice(new MyObserver<NewListBean>() {
                                @Override
                                protected void getDisposable(Disposable d) {
                                }

                                @Override
                                protected void onSuccess(NewListBean baseBean) {
                                    if (baseBean != null && baseBean.getData().getNewestList() != null
                                            && !baseBean.getData().getNewestList().isEmpty()) {
                                        viewDot.setVisibility(View.VISIBLE);
                                    }
                                }

                                @Override
                                protected void onError(String msg) {

                                }
                            });
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getBusiness();
    }

    private void getBusiness() {
        ApiClient.getInstance().getBusiness(new MyObserver<BusinessBean>() {
            @Override
            protected void getDisposable(Disposable d) {

            }

            @Override
            protected void onSuccess(BusinessBean businessBean) {
                List<BusinessBean.DataBean.BusinessesBean> businesses = businessBean.getData().getBusinesses();
                for (int i = 0; i < businesses.size(); i++) {
                    BusinessHelper.insert(businesses.get(i).getValue(), businesses.get(i).getName());
                }
            }

            @Override
            protected void onError(String msg) {

            }
        });
    }

    /**
     * 显示首页
     */
    @OnClick(value = {R.id.llHome, R.id.ivHome})
    public void clickHome() {
        switchTab(0);
    }

    /**
     * 显示揽件
     */
    @OnClick(value = {R.id.llPickUp, R.id.ivPickUp})
    public void clickPickUp() {
        switchTab(1);
    }

    /**
     * 隐藏红点
     */
    public void hideRedDot() {
        viewDot.setVisibility(View.GONE);
    }

    /**
     * 显示我的页面
     */
    @OnClick(value = {R.id.llMine, R.id.ivMine})
    public void clickMine() {
        switchTab(2);
    }

    /**
     * 切换选中的tab
     */
    private void switchTab(int position) {
        if (lastSelectTab == position) {
            return;
        }
        lastSelectTab = position;
        llHome.setSelected(false);
        llPickUp.setSelected(false);
        llMine.setSelected(false);

        switch (position) {
            case 0:
                llHome.setSelected(true);
                if (homeFragment == null) {
                    homeFragment = new MainHomeFragment();
                }
                handleFragment(homeFragment);
                break;
            case 1:
                llPickUp.setSelected(true);
                if (pickUpFragment == null) {
                    pickUpFragment = new MainPickUpFragment();
                }
                handleFragment(pickUpFragment);
                break;
            case 2:
                llMine.setSelected(true);
                if (mineFragment == null) {
                    mineFragment = new MainMineFragment();
                }
                handleFragment(mineFragment);
//                if (homeMineTaskFragment == null) {
//                    homeMineTaskFragment = new HomeMineTaskFragment();
//                }
//                handleFragment(homeMineTaskFragment);
                break;
        }
    }

    /**
     * 处理fragment
     *
     * @param showFragment 需要显示的fragment
     */
    private void handleFragment(@NonNull Fragment showFragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        List<Fragment> addFragments = manager.getFragments();
        for (int i = 0; i < addFragments.size(); i++) {
            transaction.hide(addFragments.get(i));
        }
        if (showFragment.isAdded()) {
            transaction.show(showFragment);
//            if (showFragment instanceof MainPickUpFragment) {
//                ((MainPickUpFragment)showFragment).requestData();
//            }
        } else {
            transaction.add(R.id.flContent, showFragment);
        }

//        transaction.replace(R.id.flContent, showFragment);
        transaction.commitNowAllowingStateLoss();
    }


    public static void move(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscribe != null) {
            subscribe.dispose();
        }
    }
}
