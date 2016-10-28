package com.dino.tryeverything.data;

import android.support.annotation.NonNull;


import com.dino.tryeverything.bean.Image;

import java.util.List;

import rx.Observable;

/**
 * Created by Dino on 10/25 0025.
 */

public interface DataSource {

    Observable<List<Image>> getImages(int pageno,int pagesize);

    Observable<Image> getImage(@NonNull String imageId);

    Observable<Image> saveImage(Image image);

    Observable<Iterable<Image>> saveImages(List<Image> images);

}
