package com.dino.tryeverything.data;


import com.dino.tryeverything.bean.BaseDataResponse;
import com.dino.tryeverything.bean.GanHuoDataBean;
import com.dino.tryeverything.bean.GanHuoRecentlyBean;
import com.dino.tryeverything.bean.GanHuoTitleBean;
import com.dino.tryeverything.bean.SearchResultBean;


import java.util.List;

import io.reactivex.Observable;


/**
 * Created by Dino on 10/25 0025.
 */

public interface GanHuoDataSource {
    /**
     * 获取发布干货的日期
     *
     * @return
     */
    Observable<BaseDataResponse<List<String>>> getRecentlyDate();

    /**
     * 获取最近5日干货网站数据
     *
     * @return
     */
    Observable<BaseDataResponse<List<GanHuoTitleBean>>> getTitles();

    /***
     * 根据类别查询干货
     *
     * @param category
     * @param pageIndex
     * @return
     */
    Observable<BaseDataResponse<List<GanHuoDataBean>>> getGanHuo(String category,int pageIndex);

    /**
     * 获取某天的干货
     *
     * @param date
     * @return
     */
    Observable<BaseDataResponse<GanHuoRecentlyBean>> getRecentlyGanHuo(String date);

    /**
     * 搜索
     *
     * @param keyword
     * @param pageIndex
     * @return
     */
    Observable<BaseDataResponse<List<SearchResultBean>>> search(String keyword,int pageIndex);


}
