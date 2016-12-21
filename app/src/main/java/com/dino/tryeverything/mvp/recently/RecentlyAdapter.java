package com.dino.tryeverything.mvp.recently;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;

import com.dino.tryeverything.AppConfig;
import com.dino.tryeverything.BR;
import com.dino.tryeverything.R;
import com.dino.tryeverything.base.BaseRecyclerAdapter;
import com.dino.tryeverything.base.BindingViewHolder;
import com.dino.tryeverything.bean.GanHuoDataBean;
import java.util.List;

/**
 * Created by lianghuiyong@outlook.com
 * Date on 2016/9/18
 */
public class RecentlyAdapter<T> extends BaseRecyclerAdapter<T,BindingViewHolder> {
    private Context context;
    public RecentlyAdapter(Context context, RecyclerView recyclerView, int layoutResId, List<T> data) {
        super(context, recyclerView,layoutResId, data);
        this.context = context;
    }


    @Override
    protected void convert(BindingViewHolder baseViewHolder, T data) {
        ViewDataBinding binding = baseViewHolder.getBinding();
        if(data instanceof String){
            binding.setVariable(BR.title, data);
        }else if(data instanceof GanHuoDataBean){
            binding.setVariable(BR.title, "");
            binding.setVariable(BR.bean, data);
        }

//        binding.setVariable(BR.presenter, mPresenter);
        binding.executePendingBindings();
    }

    public static SpannableString toSpannableString(String s){
                    SpannableString spannableString = new SpannableString(s);
            spannableString.setSpan(new ForegroundColorSpan(AppConfig.getInstance().getResources().getColor(R.color.textcolor9)), spannableString.toString().indexOf("["),spannableString.toString().indexOf("]")+1,Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            return spannableString;
    }


    @Override
    protected BindingViewHolder createBaseViewHolder(View view) {
        return new BindingViewHolder(view);
    }

}
