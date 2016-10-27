package com.dino.tryeverything.data.remote;

import android.support.annotation.NonNull;

import com.dino.tryeverything.data.DataSource;
import com.dino.tryeverything.bean.Image;
import com.dino.tryeverything.data.remote.network.NetWork;

import java.util.List;

import rx.Observable;

/**
 * Created by Dino on 10/25 0025.
 */

public class RemoteDataSource implements DataSource {

    private static RemoteDataSource INSTANCE;

    @Override
    public Observable<List<Image>> getImages(int pageno,int pagesize) {
        return NetWork.getImageApi().search("装逼");
    }

    @Override
    public Observable<Image> getImage(@NonNull String imageId) {
        return null;
    }

    @Override
    public Observable<Image> saveImage(Image image) {
        return null;
    }

    public static RemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteDataSource();
        }
        return INSTANCE;
    }
}
