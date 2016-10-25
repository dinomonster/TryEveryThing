package com.dino.tryeverything.data.source;

import android.support.annotation.NonNull;


import com.dino.tryeverything.data.source.local.Image;

import java.util.List;

import rx.Observable;

/**
 * Created by Dino on 10/25 0025.
 */

public interface DataSource {

    Observable<List<Image>> getImages();

    Observable<Image> getImage(@NonNull String imageId);

    Observable<Image> saveImage(Image image);

}
