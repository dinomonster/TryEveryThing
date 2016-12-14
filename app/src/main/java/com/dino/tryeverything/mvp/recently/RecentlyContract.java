package com.dino.tryeverything.mvp.recently;

import com.dino.tryeverything.base.BasePresenter;
import com.dino.tryeverything.base.BaseView;
import com.dino.tryeverything.bean.GanHuoTitleBean;

import java.util.List;

/**
 * Created by Dino on 10/24 0024.
 */

public interface RecentlyContract {
    /**
     * view接口层  处理界面
     */
    interface View extends BaseView<Presenter>{
        /**
         * 错误显示
         */
        void showNetWorkError();

        /**
         * 列表
         */
        void showGanHuoRecently(List<GanHuoTitleBean> bean);
    }

    /**
     * Presenter接口层 处理业务
     */
    interface Presenter extends BasePresenter {


        void getTitles();


    }
}
