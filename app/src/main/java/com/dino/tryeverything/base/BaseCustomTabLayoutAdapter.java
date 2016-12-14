package com.dino.tryeverything.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by lianghuiyong on 2016/5/29.
 *
 * 自定义TabLayout （String）适配器
 */
public class BaseCustomTabLayoutAdapter extends FragmentPagerAdapter {

    private List<Fragment> tab_fragments;

    public BaseCustomTabLayoutAdapter(FragmentManager fm,
                                      List<Fragment> tab_fragments) {
        super(fm);
        this.tab_fragments = tab_fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return tab_fragments.get(position);
    }

    @Override
    public int getCount() {
        return tab_fragments.size();
    }

}
