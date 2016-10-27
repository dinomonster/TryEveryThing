package com.dino.tryeverything.ui.image;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.dino.tryeverything.R;
import com.dino.tryeverything.base.BaseRecyclerAdapter;
import com.dino.tryeverything.bean.Image;
import com.dino.tryeverything.utils.GlideUtils;

import java.io.File;
import java.util.List;

/**
 * Created by lianghuiyong@outlook.com
 * Date on 2016/9/18
 */
public class ImagesAdapter extends BaseRecyclerAdapter<Image> {

    public ImagesAdapter(Context context, RecyclerView recyclerView, int layoutResId, List<Image> data) {
        super(context, recyclerView,layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder baseViewHolder, Image image) {
        if(image.getImage_url().startsWith("http")){
            GlideUtils.load(image.getImage_url(),(ImageView) baseViewHolder.getView(R.id.image));
        }else{
            File file = new File(image.getImage_url());
            GlideUtils.loadLocal(file,(ImageView) baseViewHolder.getView(R.id.image));
        }
        baseViewHolder.setText(R.id.title,image.getDescription());
    }


}
