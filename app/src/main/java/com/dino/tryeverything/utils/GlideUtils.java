package com.dino.tryeverything.utils;

import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dino.tryeverything.R;

import java.io.File;

/**
 * 图片加载工具类 for Glide
 * Created by lianghuiyong@outlook.com
 * Date on 2016/9/20
 */
public final class GlideUtils {

    private static int defultImageID = R.mipmap.default_back_picture;

    /**
     * 加载本地图片
     */
    public static void loadLocal(int resId, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(resId)
                .placeholder(defultImageID)
                .crossFade()
                .into(imageView);
    }

    /**
     * 加载SD卡图片文件
     */
    public static void loadLocal(File file, ImageView imageView) {
        if(null!=file && file.isFile() && file.exists()){
            Glide.with(imageView.getContext())
                    .load(file)
                    .placeholder(defultImageID)
                    .crossFade()
                    .into(imageView);
        }
    }

    /**
     * 加载本地、网络图片
     */
    public static void load(String url, ImageView imageView) {
        if(!TextUtils.isEmpty(url)){
            Glide.with(imageView.getContext())
                    .load(url)
                    .placeholder(defultImageID)
                    .error(defultImageID)
                    .diskCacheStrategy( DiskCacheStrategy.SOURCE )
                    .crossFade()
                    .centerCrop()
                    .into(imageView);
        }
    }


}
