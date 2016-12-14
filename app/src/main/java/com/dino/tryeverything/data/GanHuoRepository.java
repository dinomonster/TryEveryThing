package com.dino.tryeverything.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import com.dino.tryeverything.bean.BaseDataResponse;
import com.dino.tryeverything.bean.GanHuoDataBean;
import com.dino.tryeverything.bean.GanHuoRecentlyBean;
import com.dino.tryeverything.bean.GanHuoTitleBean;
import com.dino.tryeverything.bean.SearchResultBean;


import java.util.List;

import io.reactivex.Observable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Dino on 10/25 0025.
 */

public class GanHuoRepository implements GanHuoDataSource {
    @Nullable
    private static GanHuoRepository INSTANCE = null;

    @NonNull
    private final GanHuoDataSource mRemoteDataSource;

    @NonNull
    private final GanHuoDataSource mLocalDataSource;

    private GanHuoRepository(@NonNull GanHuoDataSource remoteDataSource,
                             @NonNull GanHuoDataSource localDataSource) {
        mRemoteDataSource = checkNotNull(remoteDataSource);
        mLocalDataSource = checkNotNull(localDataSource);
    }

    public static GanHuoRepository getInstance(@NonNull GanHuoDataSource remoteFoodsDataSource,
                                              @NonNull GanHuoDataSource localFoodsDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new GanHuoRepository(remoteFoodsDataSource, localFoodsDataSource);
        }
        return INSTANCE;
    }


    @Override
    public Observable<BaseDataResponse<List<String>>> getRecentlyDate() {
        return mRemoteDataSource.getRecentlyDate();
    }

    @Override
    public Observable<BaseDataResponse<List<GanHuoTitleBean>>> getTitles() {
        return mRemoteDataSource.getTitles();
    }

    @Override
    public Observable<BaseDataResponse<List<GanHuoDataBean>>> getGanHuo(String category, int pageIndex) {
        return mRemoteDataSource.getGanHuo(category,pageIndex);
    }

    @Override
    public Observable<BaseDataResponse<GanHuoRecentlyBean>> getRecentlyGanHuo(String date) {
        return mRemoteDataSource.getRecentlyGanHuo(date);
    }

    @Override
    public Observable<BaseDataResponse<List<SearchResultBean>>> search(String keyword, int pageIndex) {
        return mRemoteDataSource.search(keyword,pageIndex);
    }
}
