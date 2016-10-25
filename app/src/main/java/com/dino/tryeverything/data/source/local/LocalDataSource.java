package com.dino.tryeverything.data.source.local;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.dino.tryeverything.AppConfig;
import com.dino.tryeverything.data.source.DataSource;

import org.greenrobot.greendao.rx.RxQuery;

import java.util.List;

import rx.Observable;

/**
 * Created by Dino on 10/25 0025.
 */

public class LocalDataSource implements DataSource{
    @Nullable
    private static LocalDataSource INSTANCE;

    @Override
    public Observable<List<Image>> getImages() {
        RxQuery<Image> rxQuery = DaoManager.getDaoSession(AppConfig.getInstance()).getImageDao().queryBuilder().rx();
        return rxQuery.list();
    }

    @Override
    public Observable<Image> getImage(@NonNull String imageId) {
        return null;
    }

    @Override
    public Observable<Image> saveImage(Image image) {
        DaoManager.getDaoSession(AppConfig.getInstance()).getImageDao().insertOrReplace(image);
        return DaoManager.getDaoSession(AppConfig.getInstance()).getImageDao().rx().insertOrReplace(image);
    }

    public static LocalDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LocalDataSource();
        }
        return INSTANCE;
    }
}
