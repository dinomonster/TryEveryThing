package com.dino.tryeverything.mvp.search;

import android.app.SearchManager;

import com.dino.tryeverything.R;
import com.dino.tryeverything.base.BaseActivity;

/**
 * Created by Dino on 12/16 0016.
 */

public class SearchResultActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_result;
    }

    @Override
    public void initView() {
        setToolbarCentel(true,getIntent().getStringExtra(SearchManager.QUERY));
    }

    @Override
    public void initData() {

    }
}
