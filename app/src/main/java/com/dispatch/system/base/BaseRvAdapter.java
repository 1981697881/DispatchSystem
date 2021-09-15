package com.dispatch.system.base;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 列表适配器基类
 *
 * @author chenjunxu
 * @date 2018/1/24
 */
public abstract class BaseRvAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {
    protected LayoutInflater inflater;
    protected Context mContext;

    public BaseRvAdapter(Context context) {
        this.mContext = context;
        this.inflater = LayoutInflater.from(mContext);
    }

    /**
     * 获取 view
     */
    protected View getView(@LayoutRes int layoutId, ViewGroup parent) {
        return inflater.inflate(layoutId, parent, false);
    }

    protected View getView(@LayoutRes int layoutId) {
        return inflater.inflate(layoutId, null);
    }

}
