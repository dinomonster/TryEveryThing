package com.dino.tryeverything.widget;


import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.dino.tryeverything.R;

/**
 * Created by Dino on 11/29 0016.
 */

public final class CustomLoadMoreView extends LoadMoreView {

    @Override
    public int getLayoutId() {
        return R.layout.recyler_load_more;
    }

    @Override
    public boolean isLoadEndGone() {
        return false;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.load_more_loading_view;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.load_more_load_fail_view;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.load_more_end;
    }
}
