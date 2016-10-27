package com.dino.tryeverything.data.remote.network.api;


import com.dino.tryeverything.bean.Image;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Dino on 10/26 0026.
 */

public interface ImageApi {
    @GET("search")
    Observable<List<Image>> search(@Query("q") String query);
}
