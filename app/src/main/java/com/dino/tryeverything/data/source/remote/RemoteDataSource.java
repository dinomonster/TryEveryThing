package com.dino.tryeverything.data.source.remote;

import android.support.annotation.NonNull;

import com.dino.tryeverything.data.source.DataSource;
import com.dino.tryeverything.data.source.local.Image;

import java.util.List;

import rx.Observable;

/**
 * Created by Dino on 10/25 0025.
 */

public class RemoteDataSource implements DataSource {

    private static RemoteDataSource INSTANCE;

    @Override
    public Observable<List<Image>> getImages() {
        return null;
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
