package com.dino.tryeverything.mvp.recently;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.dino.tryeverything.bean.GanHuoTitleBean;

import java.util.List;


/**
 * Created by _SOLID
 * Date:2016/5/18
 * Time:15:54
 */
public class GanHuoRecentlyPageAdapter extends FragmentStatePagerAdapter {

    private List<String> mDateString;
    private List<GanHuoTitleBean> mTitleList;

    public GanHuoRecentlyPageAdapter(FragmentManager fm, List<String> dateString, List<GanHuoTitleBean> titleList) {
        super(fm);
        mDateString = dateString;
        mTitleList = titleList;
    }

    @Override
    public Fragment getItem(int position) {
        return RecentlyListFragment.newInstance(mDateString.get(position),mTitleList.get(position).getTitle());
    }

    @Override
    public int getCount() {
        return mDateString.size();
    }
}
