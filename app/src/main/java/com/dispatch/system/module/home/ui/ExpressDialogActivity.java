package com.dispatch.system.module.home.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.dispatch.system.R;
import com.dispatch.system.base.BaseActivity;
import com.dispatch.system.constants.IntentConstants;
import com.dispatch.system.utils.BusinessHelper;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 地区选择页面（伪对话框形式显示）
 *
 * @author chenjunxu
 * @date 2017/9/14
 */
public class ExpressDialogActivity extends BaseActivity {
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
    @BindView(R.id.wheel_view_express)
    WheelView wheelViewExpress;
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
        return R.layout.express_dialog_activity;
    }

    @Override
    protected void initView() {
        super.initView();
        KeyboardUtils.hideSoftInput(this);

        List<String> values = new ArrayList<>();
        values.addAll(BusinessHelper.getMap().values());

        // 省
        wheelViewExpress.setWheelAdapter(new ArrayWheelAdapter(this));
        wheelViewExpress.setWheelData(values);
        wheelViewExpress.setOnWheelItemSelectedListener((position, o) -> {
        });
    }

    @OnClick({R.id.tv_cancel, R.id.tv_right, R.id.ll_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
                // 确定
                Intent intent = new Intent();
                String name = (String) wheelViewExpress.getSelectionItem();
//                area = ((BuildingTreeChildBean)wheelViewProvince.getSelectionItem()).getName() + " "
//                        + (wheelViewCity.getSelectionItem() == null ? "" : ((BuildingTreeChildBean)wheelViewCity.getSelectionItem()).getName()) + " "
//                        + (wheelViewArea.getSelectionItem() == null ? "" : ((BuildingTreeChildBean)wheelViewArea.getSelectionItem()).getName());
//                intent.putExtra(IntentConstants.AREA, (BuildingTreeChildBean) wheelViewArea.getItemAtPosition(wheelViewArea.getCurrentPosition()));
//                intent.putExtra(IntentConstants.AREA_NAME, area);

                intent.putExtra(IntentConstants.EXPRESS_NAME, name);
                setResult(RESULT_OK, intent);
            case R.id.tv_cancel:
                // 取消
            case R.id.ll_layout:
                // 整体布局
                ExpressDialogActivity.this.finish();
                break;
            default:
                break;
        }
    }

    /**
     * 跳转到当前 Activity
     */
    public static void moveForResult(Context context, int requestCode) {
        Intent intent = new Intent(context, ExpressDialogActivity.class);
        ((Activity) context).startActivityForResult(intent, requestCode);
    }

    @Override
    public void finish() {
        super.finish();
        // 部分机型退出动画会有影响，导致页面闪一下
        overridePendingTransition(0, 0);
    }
}
