package com.dino.tryeverything.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dino.tryeverything.AppConfig;
import com.dino.tryeverything.R;
import com.dino.tryeverything.widget.CustomLoadMoreView;
import com.socks.library.KLog;

import java.util.List;

/**
 * Created by lianghuiyong on 2016/9/20.
 * Description: RecyclerView 适配器基类
 * 1、列表为空时有一个err页面
 * 2、上拉加载数据时有一个过渡底部，加载完数据底部会消失
 * 3、上拉加载数据失败时，底部会有一个错误提示，点击该提示会自动再次加载
 * 4、请求完所有数据可以显示一个通用底部页面(这个页面需要手动show，上面三个页面是自动显示的)
 */
public abstract class BaseRecyclerAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {

    private RecyclerView recyclerView;
    private Context context;
    private View netErrorView;
    private View notDataView;

    public BaseRecyclerAdapter(Context context, RecyclerView recyclerView, int layoutResId, List<T> data) {
        super(layoutResId, data);
        this.recyclerView = recyclerView;
        this.context = context;
        setBaseView();
    }


    private void setBaseView() {
        netErrorView = LayoutInflater.from(AppConfig.getInstance())
                .inflate(R.layout.recycler_item_neterror, (ViewGroup) recyclerView.getParent(), false);

        notDataView = LayoutInflater.from(AppConfig.getInstance())
                .inflate(R.layout.recycler_item_empty, (ViewGroup) recyclerView.getParent(), false);

        setEmptyView(notDataView);
        setLoadMoreView(new CustomLoadMoreView());
        setAutoLoadMoreSize(3);
    }


    //TODO 暂无数据
    public void showNoDataView() {
        if (isEmptyView(notDataView)) {
            KLog.e("showNoDataView");
            setEmptyView(notDataView);
            notifyItemChanged(0);
        }
    }

    //TODO 网络错误
    public void showNetWorkErrorView() {
        if (isEmptyView(netErrorView)) {
            setEmptyView(netErrorView);
            notifyItemChanged(0);
        }
    }

    private boolean isEmptyView(View view) {
        return getEmptyView() != view;
    }

}
