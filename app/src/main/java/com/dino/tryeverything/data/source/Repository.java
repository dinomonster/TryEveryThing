package com.dino.tryeverything.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import com.dino.tryeverything.AppConfig;
import com.dino.tryeverything.data.source.local.DaoManager;
import com.dino.tryeverything.data.source.local.DaoMaster;
import com.dino.tryeverything.data.source.local.Image;

import java.util.List;

import rx.Observable;

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

    @Override
    public Observable<List<Image>> getImages() {
        return mLocalDataSource.getImages();
    }

    @Override
    public Observable<Image> getImage(@NonNull String imageId) {
        return null;
    }

    @Override
    public Observable<Image> saveImage(Image image) {
        return mLocalDataSource.saveImage(image);
    }
}
