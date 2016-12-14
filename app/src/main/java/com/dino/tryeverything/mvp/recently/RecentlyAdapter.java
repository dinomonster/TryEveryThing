package com.dino.tryeverything.mvp.recently;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;

import com.chad.library.adapter.base.BaseViewHolder;
import com.dino.tryeverything.R;
import com.dino.tryeverything.base.BaseRecyclerAdapter;
import com.dino.tryeverything.bean.GanHuoTitleBean;

import java.util.List;

/**
 * Created by lianghuiyong@outlook.com
 * Date on 2016/9/18
 */
public class RecentlyAdapter extends BaseRecyclerAdapter<GanHuoTitleBean> {

    public RecentlyAdapter(Context context, RecyclerView recyclerView, int layoutResId, List<GanHuoTitleBean> data) {
        super(context, recyclerView,layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder baseViewHolder, GanHuoTitleBean data) {
//            GlideUtils.load(data.getThumbnail(),(ImageView) baseViewHolder.getView(R.id.image));
            baseViewHolder.setText(R.id.title, Html.fromHtml(data.getContent().replaceAll("\\&quot;"," ")));
    }


}
