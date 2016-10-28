package com.dino.tryeverything.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;


import com.dino.tryeverything.bean.Image;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Dino on 10/25 0025.
 */

public class Repository implements DataSource {
    @Nullable
    private static Repository INSTANCE = null;

    @NonNull
    private final DataSource mRemoteDataSource;

    @NonNull
    private final DataSource mLocalDataSource;

    private Repository(@NonNull DataSource remoteDataSource,
                       @NonNull DataSource localDataSource) {
        mRemoteDataSource = checkNotNull(remoteDataSource);
        mLocalDataSource = checkNotNull(localDataSource);
    }

    public static Repository getInstance(@NonNull DataSource remoteDataSource,
                                         @NonNull DataSource localDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new Repository(remoteDataSource, localDataSource);
        }
        return INSTANCE;
    }

    /**
     * 同时执行本地取数和网络取数
     * 网络取数后缓存到本地
     * 如果本地有数据，则返回本地数据
     *
     * @param pageno
     * @param pagesize
     * @return
     */
    @Override
    public Observable<List<Image>> getImages(int pageno, int pagesize) {
        Observable<List<Image>> localObservable = mLocalDataSource.getImages(pageno, pagesize);
        Observable<List<Image>> remoteObservable = getAndSaveRemoteImages(pageno, pagesize);
        return Observable
                .concat(localObservable, remoteObservable)
                .filter(new Func1<List<Image>, Boolean>() {
                    @Override
                    public Boolean call(List<Image> images) {
                        return !images.isEmpty();
                    }
                })
                .first();
    }

    private Observable<List<Image>> getAndSaveRemoteImages(int pageno, int pagesize) {
        return mRemoteDataSource
                .getImages(pageno, pagesize)
                .doOnNext(new Action1<List<Image>>() {
                    @Override
                    public void call(List<Image> images) {
                        mLocalDataSource
                                .saveImages(images)
                                .subscribe();//这里必须subscribe了，greendao的rxdao操作才会生效
                    }
                });

    }

    @Override
    public Observable<Image> getImage(@NonNull String imageId) {
        return null;
    }

    @Override
    public Observable<Image> saveImage(Image image) {
        return mLocalDataSource.saveImage(image);
    }

    @Override
    public Observable<Iterable<Image>> saveImages(List<Image> images) {
        return mLocalDataSource.saveImages(images);
    }
}
