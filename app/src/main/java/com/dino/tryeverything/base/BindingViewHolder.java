package com.dino.tryeverything.base;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.dino.tryeverything.R;

/**
 * Created by Dino on 12/20 0020.
 */

public class BindingViewHolder extends BaseViewHolder {

    public BindingViewHolder(View view) {
        super(view);
    }

    public ViewDataBinding getBinding() {
        return (ViewDataBinding)getConvertView().getTag(R.id.BaseQuickAdapter_databinding_support);
    }

}
