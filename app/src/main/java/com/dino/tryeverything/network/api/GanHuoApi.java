package com.dino.tryeverything.network.api;

import com.dino.tryeverything.bean.BaseDataResponse;
import com.dino.tryeverything.bean.GanHuoDataBean;
import com.dino.tryeverything.bean.GanHuoRecentlyBean;
import com.dino.tryeverything.bean.GanHuoTitleBean;
import com.dino.tryeverything.bean.SearchResultBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Dino on 10/26 0026.
 */

public interface GanHuoApi {

    /**
     * 获取发布干货的日期
     *
     * @return
     */
    @GET("day/history")
    Observable<BaseDataResponse<List<String>>> getRecentlyDate();

    /**
     * 获取最近5日干货网站数据
     *
     * @return
     */
    @GET("history/content/5/1")
    Observable<BaseDataResponse<List<GanHuoTitleBean>>> getTitles();

    /***
     * 根据类别查询干货
     *
     * @param category
     * @param pageIndex
     * @return
     */
    @GET("data/{category}/20/{pageIndex}")
    Observable<BaseDataResponse<List<GanHuoDataBean>>> getGanHuo(@Path("category") String category
            , @Path("pageIndex") int pageIndex);

    /**
     * 获取某天的干货
     *
     * @param date
     * @return
     */
    @GET("day/{date}")
    Observable<BaseDataResponse<GanHuoRecentlyBean>> getRecentlyGanHuo(@Path("date") String date);

    /**
     * 搜索
     *
     * @param keyword
     * @param pageIndex
     * @return
     */
    @GET("search/query/{keyword}/category/all/count/20/page/{pageIndex}")
    Observable<BaseDataResponse<List<SearchResultBean>>> search(@Path("keyword") String keyword, @Path("pageIndex") int pageIndex);


}
