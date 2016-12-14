package com.dino.tryeverything.network;

import com.dino.tryeverything.constants.UrlConstants;
import com.dino.tryeverything.network.api.GanHuoApi;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dino on 10/26 0026.
 */

public class NetWork {
    private static GanHuoApi ganHuoApi;
    private static OkHttpClient okHttpClient = new OkHttpClient();
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static RxJava2CallAdapterFactory rxJavaCallAdapterFactory = RxJava2CallAdapterFactory.create();


    /**
     * 通过拦截器写入固定的请求参数
     * @return
     */
//    private static OkHttpClient getHttpClient(){
//        BasicParamsInterceptor basicParamsInterceptor =
//                new BasicParamsInterceptor.Builder()
//                        .addQueryParam("key", UrlConstants.API_KEY)
//                        .build();
//        OkHttpClient client = new OkHttpClient.Builder()
//                .addInterceptor(basicParamsInterceptor)
//                .build();
//        return client;
//    }


    public static GanHuoApi getGanHuoApi() {
        if (ganHuoApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(UrlConstants.GANHUO_BASE)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            ganHuoApi = retrofit.create(GanHuoApi.class);
        }
        return ganHuoApi;
    }

}
