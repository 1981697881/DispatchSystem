package com.dispatch.system.module.home.ui;

import android.content.Context;
import android.content.Intent;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.ScreenUtils;
import com.dispatch.system.R;
import com.dispatch.system.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.scwang.smartrefresh.layout.util.DensityUtil.dp2px;

/**
 * 楼栋详情
 * <p>
 *
 * @author chenjunxu
 * @date 2020/7/18
 */
public class BuildingDetailTimeOutActivity extends BaseActivity {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvPostTab)
    TextView tvPostTab;
    @BindView(R.id.tvReceiverTab)
    TextView tvReceiverTab;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.viewIndicator)
    ImageView viewIndicator;
    private String buildingCode;
    private String houseNumber;

    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected int bindLayout() {
        return R.layout.activity_building_detail;
    }

    @Override
    protected void initView() {
        super.initView();
        buildingCode = getIntent().getStringExtra("buildingCode");
        houseNumber = getIntent().getStringExtra("houseNumber");
        String title = houseNumber == null ? "" : (houseNumber + "户型") + "派送/揽收详情";
        tvTitle.setText(title);


        // 移动指示器
        tabWidth = (ScreenUtils.getScreenWidth() - dp2px(24)) / 2;
        int offset = (tabWidth - dp2px(40)) / 2 + dp2px(12);
//        Matrix matrix = new Matrix();
//        matrix.postTranslate(offset, 0);
//        viewIndicator.setImageMatrix(matrix);
        viewIndicator.animate().translationX(offset);

        switchTab(0);

        fragments.add(new BuildingDetailDeliveryTimeoutFragment(buildingCode, houseNumber));
        fragments.add(new BuildingDetailTimeoutReceiverFragment(buildingCode, houseNumber));
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switchTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    /**
     * 每一项的宽度
     */
    private int tabWidth;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            ((BuildingDetailDeliveryTimeoutFragment) fragments.get(0)).update();
        }
    }

    int lastSelect = -1;

    private void switchTab(int position) {
        if (position == 0) {
            tvPostTab.setTextColor(0xFFDE1111);
            tvPostTab.getPaint().setFakeBoldText(true);
            tvPostTab.setTextSize(18F);
            tvReceiverTab.setTextColor(0xFF666666);
            tvReceiverTab.getPaint().setFakeBoldText(false);
            tvReceiverTab.setTextSize(14);
        } else {
            tvReceiverTab.setTextColor(0xFFDE1111);
            tvReceiverTab.getPaint().setFakeBoldText(true);
            tvReceiverTab.setTextSize(18F);
            tvPostTab.setTextSize(14F);
            tvPostTab.setTextColor(0xFF666666);
            tvPostTab.getPaint().setFakeBoldText(false);
        }

        if (lastSelect != -1) {
            indicatorAnim = new TranslateAnimation(lastSelect * tabWidth, position * tabWidth, 0, 0);
            indicatorAnim.setDuration(300);
            indicatorAnim.setFillAfter(true);
            viewIndicator.startAnimation(indicatorAnim);
        }

        lastSelect = position;
    }

    /**
     * 指示器动画
     */
    private Animation indicatorAnim;

    public void updatePostTab(int num) {
        tvPostTab.setText("超时派送件详情" + num);
    }

    public void updateReceiverTab(int num) {
        tvReceiverTab.setText("超时揽收件详情" + num);
    }

    public static void move(Context context, String buildingCode, String houseNumber) {
        Intent intent = new Intent(context, BuildingDetailTimeOutActivity.class);
        intent.putExtra("buildingCode", buildingCode);
        intent.putExtra("houseNumber", houseNumber);
        context.startActivity(intent);
    }

    @OnClick(R.id.ivBack)
    public void onViewClicked() {
        finish();
    }

    @OnClick(R.id.tvPostTab)
    public void clickPostTab() {
        viewPager.setCurrentItem(0);
    }

    @OnClick(R.id.tvReceiverTab)
    public void clickReceiverTab() {
        viewPager.setCurrentItem(1);
    }

    private class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
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
