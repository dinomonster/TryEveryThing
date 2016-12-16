package com.dino.tryeverything.mvp.recently;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.format.DateUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.dino.tryeverything.R;
import com.dino.tryeverything.base.BaseRecyclerAdapter;
import com.dino.tryeverything.bean.GanHuoDataBean;
import com.dino.tryeverything.bean.GanHuoRecentlyBean;
import com.dino.tryeverything.bean.GanHuoTitleBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by lianghuiyong@outlook.com
 * Date on 2016/9/18
 */
public class RecentlyAdapter<T> extends BaseRecyclerAdapter<T> {
    private Context context;
    public RecentlyAdapter(Context context, RecyclerView recyclerView, int layoutResId, List<T> data) {
        super(context, recyclerView,layoutResId, data);
        this.context = context;
    }


    @Override
    protected void convert(BaseViewHolder baseViewHolder, T data) {
//            GlideUtils.load(data.getThumbnail(),(ImageView) baseViewHolder.getView(R.id.image));
        if(data instanceof String){
            baseViewHolder.setVisible(R.id.content_ll,false);
            baseViewHolder.setVisible(R.id.title,true);
            baseViewHolder.setText(R.id.title,(String)data);
        }else if(data instanceof GanHuoDataBean){
            baseViewHolder.setVisible(R.id.content_ll,true);
            baseViewHolder.setVisible(R.id.title,false);

            SpannableString spannableString = new SpannableString(((GanHuoDataBean) data).getDesc()+"["+((GanHuoDataBean) data).getWho()+"]");
            spannableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.textcolor9)), spannableString.toString().indexOf("["),spannableString.toString().indexOf("]")+1,Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            baseViewHolder.setText(R.id.content,spannableString);

            String date = ((GanHuoDataBean) data).getPublishedAt();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            try {
                Date d = format.parse(date);
                baseViewHolder.setText(R.id.date, "["+DateUtils.getRelativeTimeSpanString(d.getTime())+"]");
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }


}
