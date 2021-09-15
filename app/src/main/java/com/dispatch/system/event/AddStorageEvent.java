package com.dispatch.system.event;

import com.dispatch.system.entity.MemberBean;

public class AddStorageEvent {

    public MemberBean bean;

    public AddStorageEvent(MemberBean bean) {
        this.bean = bean;
    }
}
