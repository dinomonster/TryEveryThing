package com.dino.tryeverything.data.remote.network;

import com.dino.tryeverything.constants.UrlConstants;
import com.dino.tryeverything.data.remote.network.api.ImageApi;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dino on 10/26 0026.
 */

public class NetWork {
    private static ImageApi imageApi;
    private static OkHttpClient okHttpClient = new OkHttpClient();
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();

    public static ImageApi getImageApi() {
        if (imageApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(UrlConstants.BASE)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            imageApi = retrofit.create(ImageApi.class);
        }
        return imageApi;
    }

}
