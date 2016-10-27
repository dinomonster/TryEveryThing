package com.dino.tryeverything;

import android.app.Application;
import android.util.TypedValue;

import com.dino.tryeverything.data.local.DaoManager;
import com.dino.tryeverything.widget.UILImageLoader;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ImageLoader;
import cn.finalteam.galleryfinal.ThemeConfig;

/**
 * Created by Dino on 10/9 0009.
 */

public class AppConfig extends Application {

    private static AppConfig instance;


    public static AppConfig getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //初始化图片选择
        initImageSelector();
    }

    private void initImageSelector() {
        //设置主题
        //ThemeConfig.CYAN

        TypedValue typedValue = new TypedValue();
        this.getTheme().resolveAttribute(android.R.attr.textColorPrimary, typedValue, true);
        int color = typedValue.resourceId;

        ThemeConfig theme = new ThemeConfig.Builder()
                .setTitleBarIconColor(getResources().getColor(R.color.colorPrimary))
                .setTitleBarBgColor(getResources().getColor(R.color.toolbarBackColor))
                .setTitleBarTextColor(getResources().getColor(R.color.black))
//                .setTitleBarTextColor(getResources().getColor(color))
                .build();
        //配置功能
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(false)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setCropSquare(true)
                .setEnablePreview(true)
                .build();

        //配置imageloader
        ImageLoader imageloader = new UILImageLoader();
        CoreConfig coreConfig = new CoreConfig.Builder(this, imageloader, theme)
//                .setDebug(BuildConfig.DEBUG)
                .setFunctionConfig(functionConfig)
//                .setNoAnimcation(true)
                .build();
        GalleryFinal.init(coreConfig);
    }

}
