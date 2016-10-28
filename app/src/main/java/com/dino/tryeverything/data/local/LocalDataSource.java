package com.dino.tryeverything.data.local;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.dino.tryeverything.AppConfig;
import com.dino.tryeverything.bean.Image;
import com.dino.tryeverything.data.DataSource;

import org.greenrobot.greendao.rx.RxQuery;

import java.util.Iterator;
import java.util.List;

import rx.Observable;

/**
 * Created by Dino on 10/25 0025.
 */

public class LocalDataSource implements DataSource{
    @Nullable
    private static LocalDataSource INSTANCE;


    @Override
    public Observable<List<Image>> getImages(int pageno, int pagesize) {
        RxQuery<Image> rxQuery = DaoManager.getDaoSession(AppConfig.getInstance()).getImageDao()
                .queryBuilder()
                .orderDesc(ImageDao.Properties.ImageId)
                .limit(pagesize)//限制查询返回结果的数目
                .offset(pageno*pagesize)//设置查询结果的偏移量
                .rx();
        return rxQuery.list();
    }

    @Override
    public Observable<Image> getImage(@NonNull String imageId) {
        return null;
    }

    @Override
    public Observable<Image> saveImage(Image image) {
        return DaoManager.getDaoSession(AppConfig.getInstance()).getImageDao().rx().insertOrReplace(image);
    }

    @Override
    public Observable<Iterable<Image>> saveImages(List<Image> images) {
//        DaoManager.getDaoSession(AppConfig.getInstance()).getImageDao().insertOrReplaceInTx(images);
        return DaoManager.getDaoSession(AppConfig.getInstance()).getImageDao().rx().insertOrReplaceInTx(images);
    }

    public static LocalDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LocalDataSource();
        }
        return INSTANCE;
    }
}
