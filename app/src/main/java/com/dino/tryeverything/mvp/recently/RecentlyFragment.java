package com.dino.tryeverything.mvp.recently;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.dino.tryeverything.R;
import com.dino.tryeverything.base.BaseFragment;
import com.dino.tryeverything.bean.GanHuoRecentlyWrapper;
import com.dino.tryeverything.data.GanHuoRepository;
import com.dino.tryeverything.data.local.LocalGanHuoDataSource;
import com.dino.tryeverything.data.remote.RemoteGanHuoDataSource;
import com.dino.tryeverything.widget.RotateDownPageTransformer;


import butterknife.BindView;

/**
 * Created by Dino on 10/24 0024.
 */

public class RecentlyFragment extends BaseFragment implements RecentlyContract.View{

    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    private GanHuoRecentlyPageAdapter mPageAdapter;

    private RecentlyPresenter presenter;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recently;
    }

    @Override
    public void initView() {
        presenter = new RecentlyPresenter(this, GanHuoRepository.getInstance(RemoteGanHuoDataSource.getInstance(), LocalGanHuoDataSource.getInstance()));
        dialogHelper.showProgressDialog("数据加载中...");
        presenter.subscribe();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }

    @Override
    public void initData() {
        presenter.subscribe();
    }

    @Override
    public void showNetWorkError() {
        dialogHelper.dismissProgressDialog();
    }
    @Override
    public void showGanHuoRecently(Object bean) {
        dialogHelper.dismissProgressDialog();
        if(bean instanceof GanHuoRecentlyWrapper) {
            GanHuoRecentlyWrapper wrapper = (GanHuoRecentlyWrapper) bean;
            mPageAdapter = new GanHuoRecentlyPageAdapter(getChildFragmentManager(), wrapper.dateList, wrapper.titleList);
            mViewPager.setAdapter(mPageAdapter);
            mViewPager.setPageTransformer(false,new RotateDownPageTransformer());
            for (int i = 0; i < wrapper.dateList.size(); i++) {
                mTabLayout.addTab(mTabLayout.newTab());
            }
            mTabLayout.setupWithViewPager(mViewPager);
        }
    }

}
