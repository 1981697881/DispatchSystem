package com.dispatch.system.base;

import android.os.Bundle;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import butterknife.ButterKnife;

public abstract class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(bindLayout());
        ButterKnife.bind(this);
        initView();
    }

    protected abstract int bindLayout();

    protected void initView() {

    }

    private QMUITipDialog tipDialog;

    public void showLoading() {
        if (tipDialog == null) {
            tipDialog = new QMUITipDialog.Builder(this)
                    .setTipWord("加载中")
                    .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING).create(false);
        }
        tipDialog.show();
    }

    public void hideLoading() {
        if (tipDialog != null) {
            tipDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tipDialog != null) {
            tipDialog.dismiss();
            tipDialog = null;
        }
    }
}
