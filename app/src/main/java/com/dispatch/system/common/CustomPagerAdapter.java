package com.dispatch.system.common;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.dispatch.system.base.BaseFragment;

import java.util.List;

/**
 * 通用viewpager适配器
 *
 * @author chenjunxu
 * @date 2017/10/13
 */
public class CustomPagerAdapter extends FragmentStatePagerAdapter {
    private List<BaseFragment> lists;

    public CustomPagerAdapter(FragmentManager fm, List<BaseFragment> lists) {
        super(fm);
        this.lists = lists;
    }

    /**
     * 设置数据
     */
    public void setDatas(List<BaseFragment> lists) {
        this.lists = lists;
        this.notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return lists.get(position);
    }

    @Override
    public int getCount() {
        if (lists == null) {
            return 0;
        }
        return lists.size();
    }
}