package com.dispatch.system.module.home;

import android.Manifest;
import android.content.Intent;
import android.graphics.Matrix;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.dispatch.system.R;
import com.dispatch.system.base.BaseFragment;
import com.dispatch.system.common.CustomPagerAdapter;
import com.dispatch.system.module.home.ui.ErrorScanActivity;
import com.dispatch.system.module.home.ui.HomeMineTaskFragment;
import com.dispatch.system.module.home.ui.HomeOverdueFragment;
import com.dispatch.system.module.home.ui.HomePostFragment;
import com.dispatch.system.module.home.ui.PickUpScanActivity;
import com.dispatch.system.module.home.ui.SortScanActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.scwang.smartrefresh.layout.util.DensityUtil.dp2px;

/**
 * 首页
 * <p>
 *
 * @author chenjunxu
 * @date 2020/7/4
 */
public class MainHomeFragment extends BaseFragment {

    @BindView(R.id.tvTabMineWork)
    TextView tvTabMineWork;
    @BindView(R.id.tvTabPostWork)
    TextView tvTabPostWork;
    @BindView(R.id.tvTabOverdueWork)
    TextView tvTabOverdueWork;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.viewIndicator)
    ImageView viewIndicator;
    private RxPermissions rxPermissions;

    private List<BaseFragment> fragments = new ArrayList<>();
    private CustomPagerAdapter pagerAdapter;

    @Override
    protected int bindLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        super.initView();
        rxPermissions = new RxPermissions(this);
        fragments.add(new HomeMineTaskFragment());
        fragments.add(new HomePostFragment());
        fragments.add(new HomeOverdueFragment());
        pagerAdapter = new CustomPagerAdapter(getChildFragmentManager(), fragments);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switchWorkPage(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


        // 移动指示器
        tabWidth = dp2px(126);
        int offset = (tabWidth - dp2px(36)) / 3;
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        viewIndicator.setImageMatrix(matrix);
    }

    /**
     * 每一项的宽度
     */
    private int tabWidth;

    @Override
    public void onResume() {
        super.onResume();
    }

    @OnClick({R.id.clSortScan, R.id.clPickUpScan, R.id.clErrorScan,
            R.id.tvTabMineWork, R.id.tvTabPostWork, R.id.tvTabOverdueWork})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.clSortScan:
                rxPermissions.request(Manifest.permission.CAMERA).subscribe(grant -> {
                    startActivity(new Intent(getActivity(), SortScanActivity.class));
                });
                break;
            case R.id.clPickUpScan:
                rxPermissions.request(Manifest.permission.CAMERA).subscribe(grant -> {
                    startActivity(new Intent(getActivity(), PickUpScanActivity.class));
                });
                break;
            case R.id.clErrorScan:
                rxPermissions.request(Manifest.permission.CAMERA).subscribe(grant -> {
                    startActivity(new Intent(getActivity(), ErrorScanActivity.class));
                });
                break;
            case R.id.tvTabMineWork:
                viewPager.setCurrentItem(0);
                break;
            case R.id.tvTabPostWork:
                viewPager.setCurrentItem(1);
                break;
            case R.id.tvTabOverdueWork:
                viewPager.setCurrentItem(2);
                break;
        }
    }

    private int lastSelect = 0;

    private void switchWorkPage(int position) {
        tvTabPostWork.setTextSize(14F);
        tvTabPostWork.setTextColor(0xFF666666);
        tvTabMineWork.setTextSize(14F);
        tvTabMineWork.setTextColor(0xFF666666);
        tvTabOverdueWork.setTextSize(14F);
        tvTabOverdueWork.setTextColor(0xFF666666);
        if (position == 0) {
            tvTabMineWork.setTextSize(18F);
            tvTabMineWork.setTextColor(0xFFDE1111);
        } else if (position == 1) {
            tvTabPostWork.setTextSize(18F);
            tvTabPostWork.setTextColor(0xFFDE1111);
        } else {
            tvTabOverdueWork.setTextSize(18F);
            tvTabOverdueWork.setTextColor(0xFFDE1111);
        }

        indicatorAnim = new TranslateAnimation(lastSelect * tabWidth, position * tabWidth, 0, 0);
        indicatorAnim.setDuration(300);
        indicatorAnim.setFillAfter(true);
        viewIndicator.startAnimation(indicatorAnim);

        lastSelect = position;
    }


    /**
     * 指示器动画
     */
    private Animation indicatorAnim;

    private class PagerAdapter extends FragmentStatePagerAdapter {
        private FragmentManager fm;

        public PagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
            this.fm = fm;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return fragments.get(0);
            } else {
                return fragments.get(1);
            }
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        //        @NonNull
//        @Override
//        public Object instantiateItem(@NonNull ViewGroup container, int position) {
//            try {
//                Field mFragments = getClass().getSuperclass().getDeclaredField("mFragments");
//                mFragments.setAccessible(true);
//                ((ArrayList) mFragments.get(this)).clear();
//
//                Field mSavedState = getClass().getSuperclass().getDeclaredField("mSavedState");
//                mSavedState.setAccessible(true);
//                ((ArrayList) mSavedState.get(this)).clear();
//            } catch (NoSuchFieldException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
//            return super.instantiateItem(container, position);
//        }

        //        @Override
//        public Object instantiateItem(ViewGroup container, int position) {
//            FragmentTransaction ft = fm.beginTransaction();
//            for (int i = 0; i < getCount(); i++) {//通过遍历清除所有缓存
//                final long itemId = getId(i);
//                //得到缓存fragment的名字
//                String name = makeFragmentName(container.getId(), itemId);
//                //通过fragment名字找到该对象
//                BaseFragment fragment = (BaseFragment) fm.findFragmentByTag(name);
//                if (fragment != null) {
//                    //移除之前的fragment
//                    ft.remove(fragment);
//                }
//            }
//            //重新添加新的fragment:最后记得commit
//            ft.add(container.getId(), getItem(position)).attach(getItem(position)).commit();
//            return getItem(position);
//        }
//
//        @Override
//        public int getItemPosition(Object object) {
//            return POSITION_NONE;
//        }
//
//        /**
//         * 得到缓存fragment的名字
//         * @param viewId
//         * @param id
//         * @return
//         */
//        private String makeFragmentName(int viewId, long id) {
//            return "android:switcher:" + viewId + ":" + id;
//        }

        @Override
        public int getCount() {
            return fragments.size();
        }

//        @Override
//        public boolean isViewFromObject(View view, Object object) {
//            return view == object;
//        }
    }
}
