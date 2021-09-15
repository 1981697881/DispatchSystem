package com.dispatch.system.entity;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class HomeTaskGroup extends ExpandableGroup<MineTodayTaskBean.DataBean.DeliveryTimeTasksBean.BuildingTaskCountListBean> {

    public HomeTaskGroup(String title, List<MineTodayTaskBean.DataBean.DeliveryTimeTasksBean.BuildingTaskCountListBean> items) {
        super(title, items);
    }
}
