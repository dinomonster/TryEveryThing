package com.dino.tryeverything.mvp.recently;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.dino.tryeverything.AppConfig;
import com.dino.tryeverything.R;
import com.dino.tryeverything.base.BaseFragment;
import com.dino.tryeverything.bean.GanHuoDataBean;
import com.dino.tryeverything.bean.GanHuoRecentlyBean;
import com.dino.tryeverything.data.GanHuoRepository;
import com.dino.tryeverything.data.local.LocalGanHuoDataSource;
import com.dino.tryeverything.data.remote.RemoteGanHuoDataSource;
import com.dino.tryeverything.mvp.MainWebActivity;
import com.dino.tryeverything.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dino on 12/15 0015.
 */

public class RecentlyListFragment extends BaseFragment implements RecentlyContract.View, SwipeRefreshLayout.OnRefreshListener {
    public static final String DATE_STRING = "dateString";
    public static final String TITLE = "fragment_index";
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;
    private RecentlyAdapter adapter;

    private String date;
    private String title = "";

    private RecentlyPresenter presenter;

    public static RecentlyListFragment newInstance(String date, String title) {
        Bundle args = new Bundle();
        args.putString(RecentlyListFragment.DATE_STRING, date);
        args.putString(RecentlyListFragment.TITLE, title);
        RecentlyListFragment fragment = new RecentlyListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_recyclerview;
    }

    @Override
    public void initView() {
        presenter = new RecentlyPresenter(this, GanHuoRepository.getInstance(RemoteGanHuoDataSource.getInstance(), LocalGanHuoDataSource.getInstance()));
        swiperefresh.setColorSchemeColors(getResources().getColor(R.color.colorPrimary), Color.GREEN, Color.RED, Color.YELLOW);
        swiperefresh.setOnRefreshListener(this);
        recyclerView.setHasFixedSize(true);
        adapter = new RecentlyAdapter(getActivity(), recyclerView, R.layout.item_recently, null);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                if(baseQuickAdapter.getData().get(i) instanceof GanHuoDataBean){
                    Bundle bundle= new Bundle();
                    bundle.putString("url",((GanHuoDataBean) baseQuickAdapter.getData().get(i)).getUrl());
                    bundle.putString("title",((GanHuoDataBean) baseQuickAdapter.getData().get(i)).getDesc());
                    nextActivity(getActivity(), MainWebActivity.class,bundle);
                }
            }
        });

        date = getArguments().getString(DATE_STRING).replace('-', '/');
        title = getArguments().getString(TITLE);



    }

    @Override
    public void initData() {
        swiperefresh.setRefreshing(true);
        presenter.getRecentlyGanHuo(date);
    }

    @Override
    public void showNetWorkError() {

    }

    @Override
    public void showGanHuoRecently(Object bean) {
        swiperefresh.setRefreshing(false);
        if (bean instanceof GanHuoRecentlyBean) {
            GanHuoRecentlyBean recentlyBean = (GanHuoRecentlyBean) bean;
            if (recentlyBean != null) {
                if (recentlyBean.get福利() != null) {
                    addData(recentlyBean);
                }
            }
        }
    }

    private void addData(GanHuoRecentlyBean recentlyBean) {
        adapter.removeAllHeaderView();
        LayoutInflater inflater = LayoutInflater.from(AppConfig.getInstance());
        View headView = inflater.inflate(R.layout.head_view, (ViewGroup) recyclerView.getParent(), false);
        ((TextView) headView.findViewById(R.id.tv)).setText(title);
        GlideUtils.load(recentlyBean.get福利().get(0).getUrl(), (ImageView) headView.findViewById(R.id.iv));
        adapter.addHeaderView(headView);
        List list = new ArrayList();
        if (recentlyBean != null) {
            if (recentlyBean.getAndroid() != null) {
                list.add("Android");
                list.addAll(recentlyBean.getAndroid());
            }
            if (recentlyBean.getApp() != null) {
                list.add("App");
                list.addAll(recentlyBean.getApp());
            }
            if (recentlyBean.getIOS() != null) {
                list.add("IOS");
                list.addAll(recentlyBean.getIOS());
            }
            if (recentlyBean.get休息视频() != null) {
                list.add("休息视频");
                list.addAll(recentlyBean.get休息视频());
            }
            if (recentlyBean.get瞎推荐() != null) {
                list.add("瞎推荐");
                list.addAll(recentlyBean.get瞎推荐());
            }
//            if (recentlyBean.get福利() != null) {
//                list.add("福利");
//                list.addAll(recentlyBean.get福利());
//            }
            if (recentlyBean.get拓展资源() != null) {
                list.add("拓展资源");
                list.addAll(recentlyBean.get拓展资源());
            }
        }

        adapter.setNewData(list);
    }


    @Override
    public void onRefresh() {
        presenter.getRecentlyGanHuo(date);
    }
}
