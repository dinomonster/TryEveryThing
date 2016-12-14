package com.dino.tryeverything.data.remote;

import com.dino.tryeverything.bean.BaseDataResponse;
import com.dino.tryeverything.bean.GanHuoDataBean;
import com.dino.tryeverything.bean.GanHuoRecentlyBean;
import com.dino.tryeverything.bean.GanHuoTitleBean;
import com.dino.tryeverything.bean.SearchResultBean;
import com.dino.tryeverything.data.GanHuoDataSource;
import com.dino.tryeverything.network.NetWork;

import java.util.List;

import io.reactivex.Observable;


/**
 * Created by Dino on 10/25 0025.
 */

public class RemoteGanHuoDataSource implements GanHuoDataSource {

    private static RemoteGanHuoDataSource INSTANCE;

    public static RemoteGanHuoDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteGanHuoDataSource();
        }
        return INSTANCE;
    }


    @Override
    public Observable<BaseDataResponse<List<String>>> getRecentlyDate() {
        return NetWork.getGanHuoApi().getRecentlyDate();
    }

    @Override
    public Observable<BaseDataResponse<List<GanHuoTitleBean>>> getTitles() {
        return NetWork.getGanHuoApi().getTitles();
    }

    @Override
    public Observable<BaseDataResponse<List<GanHuoDataBean>>> getGanHuo(String category, int pageIndex) {
        return NetWork.getGanHuoApi().getGanHuo(category,pageIndex);
    }

    @Override
    public Observable<BaseDataResponse<GanHuoRecentlyBean>> getRecentlyGanHuo(String date) {
        return NetWork.getGanHuoApi().getRecentlyGanHuo(date);
    }

    @Override
    public Observable<BaseDataResponse<List<SearchResultBean>>> search(String keyword, int pageIndex) {
        return NetWork.getGanHuoApi().search(keyword,pageIndex);
    }
}
