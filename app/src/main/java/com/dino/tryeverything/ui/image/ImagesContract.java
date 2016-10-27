package com.dino.tryeverything.ui.image;

import com.dino.tryeverything.base.BasePresenter;
import com.dino.tryeverything.base.BaseView;
import com.dino.tryeverything.bean.Image;

import java.util.List;

/**
 * Created by Dino on 10/24 0024.
 */

public interface ImagesContract {
    /**
     * view接口层  处理界面
     */
    interface View extends BaseView<Presenter>{
        /**
         * 显示照片选择界面
         */
        void showImagePicker();

        /**
         * 显示照片
         */
        void showImages(List<Image> images);
    }

    /**
     * Presenter接口层 处理业务
     */
    interface Presenter extends BasePresenter {
        /**
         * 添加照片
         */
        void addNewImage();

        /**
         * 保存照片到本地数据库
         */
        void saveImage(Image image);

        /**
         * 加载照片
         */
        void loadImage(int pageno,int pagesize);


    }
}
