package com.dino.tryeverything.mvp.recently;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.dino.tryeverything.R;
import com.dino.tryeverything.base.BaseFragment;
import com.dino.tryeverything.bean.GanHuoTitleBean;
import com.dino.tryeverything.data.GanHuoRepository;
import com.dino.tryeverything.data.local.LocalGanHuoDataSource;
import com.dino.tryeverything.data.remote.RemoteGanHuoDataSource;
import com.dino.tryeverything.utils.AnimatorUtils;
import com.google.common.base.Preconditions;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Dino on 10/24 0024.
 */

public class RecentlyFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,RecentlyContract.View{

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swiprefresh)
    SwipeRefreshLayout swiprefresh;

    private RecentlyAdapter adapter;

    private RecentlyContract.Presenter mPresenter;

    private RecentlyPresenter presenter;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_foods;
    }

    @Override
    public void initView() {
        presenter = new RecentlyPresenter(this, GanHuoRepository.getInstance(RemoteGanHuoDataSource.getInstance(), LocalGanHuoDataSource.getInstance()));
//        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);//瀑布流
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new RecentlyAdapter(getActivity(), recyclerView, R.layout.item_recently, null);
        adapter.openLoadAnimation(2);
        recyclerView.setAdapter(adapter);

        /** 下拉刷新颜色 */
        swiprefresh.setColorSchemeResources(
                R.color.swiperefresh_color1, R.color.swiperefresh_color2,
                R.color.swiperefresh_color3, R.color.swiperefresh_color4);

        swiprefresh.setOnRefreshListener(this);

        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                AnimatorUtils.startRotation(getActivity(), view, 500, new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);

                    }

                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                    }
                });
            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                super.onItemChildClick(adapter, view, position);
                switch (view.getId()) {
                    case R.id.image:
                        break;
                }

            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @Override
    public void initData() {
        mPresenter.subscribe();
    }

    @Override
    public void onRefresh() {
    }

    @Override
    public void setPresenter(RecentlyContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    public void showNetWorkError() {
        swiprefresh.setRefreshing(false);
        adapter.showNetWorkErrorView();
//        adapter.showFooter();
    }

    @Override
    public void showGanHuoRecently(List<GanHuoTitleBean> bean) {
        swiprefresh.setRefreshing(false);
        adapter.setNewData(bean);

    }

}
