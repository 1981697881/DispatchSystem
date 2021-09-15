package com.dispatch.system.module.home.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dispatch.system.R;
import com.dispatch.system.base.BaseActivity;
import com.dispatch.system.constants.IntentConstants;
import com.dispatch.system.entity.BuildingTreeBean;
import com.dispatch.system.entity.BuildingTreeChildBean;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 地区选择页面（伪对话框形式显示）
 *
 * @author chenjunxu
 * @date 2017/9/14
 */
public class RegisterAreaDialogActivity extends BaseActivity {
    /**
     * 取消
     */
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    /**
     * 确定
     */
    @BindView(R.id.tv_right)
    TextView tvRight;
    /**
     * 省市区
     */
    @BindView(R.id.wheel_view_province)
    WheelView wheelViewProvince;
    @BindView(R.id.wheel_view_city)
    WheelView wheelViewCity;
    @BindView(R.id.wheel_view_area)
    WheelView wheelViewArea;
    /**
     * 整体布局
     */
    @BindView(R.id.ll_layout)
    LinearLayout llLayout;
    /**
     * 选择的省市区
     */
    private String area;

    @Override
    protected int bindLayout() {
        return R.layout.mine_setting_area_dialog_activity;
    }

    @Override
    protected void initView() {
        super.initView();

        BuildingTreeBean bean = (BuildingTreeBean) getIntent().getSerializableExtra(IntentConstants.AREA_BEAN);

        // 省
        List<BuildingTreeBean.DataBean.BuildingsBean> buildings = bean.getData().getBuildings();
        wheelViewProvince.setWheelAdapter(new ArrayWheelAdapter(this));
        wheelViewProvince.setWheelData(buildings);
        wheelViewProvince.setOnWheelItemSelectedListener((position, o) -> {
            wheelViewCity.setWheelData(buildings.get(position).getChildren());
            int poi = wheelViewCity.getCurrentPosition();
            poi = poi == -1 ? 0 : poi;
            wheelViewArea.setWheelData(buildings.get(position).getChildren().get(poi).getChildren());
        });

        // 市
        BuildingTreeBean.DataBean.BuildingsBean secondBean = buildings.get(0);
        wheelViewCity.setWheelAdapter(new ArrayWheelAdapter(this));
        wheelViewCity.setWheelData(secondBean.getChildren());
        wheelViewCity.setOnWheelItemSelectedListener((position, o) -> {
            int poi = wheelViewProvince.getCurrentPosition();
            poi = poi == -1 ? 0 : poi;
            wheelViewArea.setWheelData(buildings.get(poi).getChildren().get(position).getChildren());
        });

        // 区
        wheelViewArea.setWheelAdapter(new ArrayWheelAdapter(this));
        wheelViewArea.setWheelData(secondBean.getChildren().get(0).getChildren());
    }

    @OnClick({R.id.tv_cancel, R.id.tv_right, R.id.ll_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
                // 确定
                Intent intent = new Intent();
                area = ((BuildingTreeChildBean)wheelViewProvince.getSelectionItem()).getName() + " "
                        + (wheelViewCity.getSelectionItem() == null ? "" : ((BuildingTreeChildBean)wheelViewCity.getSelectionItem()).getName()) + " "
                        + (wheelViewArea.getSelectionItem() == null ? "" : ((BuildingTreeChildBean)wheelViewArea.getSelectionItem()).getName());
                intent.putExtra(IntentConstants.AREA, (BuildingTreeChildBean) wheelViewArea.getItemAtPosition(wheelViewArea.getCurrentPosition()));
                intent.putExtra(IntentConstants.AREA_NAME, area);
                setResult(RESULT_OK, intent);
            case R.id.tv_cancel:
                // 取消
            case R.id.ll_layout:
                // 整体布局
                RegisterAreaDialogActivity.this.finish();
                break;
            default:
                break;
        }
    }

    /**
     * 跳转到当前 Activity
     */
    public static void moveForResult(Context context, BuildingTreeBean bean, int requestCode) {
        Intent intent = new Intent(context, RegisterAreaDialogActivity.class);
        intent.putExtra(IntentConstants.AREA_BEAN, bean);
        ((Activity) context).startActivityForResult(intent, requestCode);
    }

    @Override
    public void finish() {
        super.finish();
        // 部分机型退出动画会有影响，导致页面闪一下
        overridePendingTransition(0, 0);
    }
}
