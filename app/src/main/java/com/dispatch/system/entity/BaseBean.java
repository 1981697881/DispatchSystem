package com.dispatch.system.entity;

import android.text.TextUtils;

import java.io.Serializable;

public class BaseBean implements Serializable {

    private String desc;
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDesc() {
        if (TextUtils.isEmpty(desc)) {
            return "网络请求失败";
        }
        return desc;
    }

    public void setDesc(String message) {
        this.desc = message;
    }
}
