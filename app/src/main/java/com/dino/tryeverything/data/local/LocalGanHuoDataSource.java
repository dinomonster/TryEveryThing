package com.dino.tryeverything.data.local;

import android.support.annotation.Nullable;

import com.dino.tryeverything.AppConfig;
import com.dino.tryeverything.bean.BaseDataResponse;
import com.dino.tryeverything.bean.GanHuoDataBean;
import com.dino.tryeverything.bean.GanHuoRecentlyBean;
import com.dino.tryeverything.bean.GanHuoTitleBean;
import com.dino.tryeverything.bean.SearchResultBean;
import com.dino.tryeverything.constants.UrlConstants;
import com.dino.tryeverything.data.GanHuoDataSource;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.socks.library.KLog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;


/**
 * Created by Dino on 10/25 0025.
 */

public class LocalGanHuoDataSource implements GanHuoDataSource {
    @Nullable
    private static LocalGanHuoDataSource INSTANCE;

    Gson gson = new Gson();



    public void saveCacheToFile(GanHuoDataBean dataBean) {
//        return DaoManager.getDaoSession(AppConfig.getInstance()).getImageDao().rx().insertOrReplace(image);
        String json = gson.toJson(dataBean);
        File dataFile = new File(AppConfig.getInstance().getFilesDir(), UrlConstants.FOOD_LIST_CACHE);
        try {
            if (!dataFile.exists()) {
                try {
                    dataFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                dataFile.delete();
                dataFile.createNewFile();
            }
            Writer writer = new FileWriter(dataFile);
            writer.write(json);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static LocalGanHuoDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LocalGanHuoDataSource();
        }
        return INSTANCE;
    }

    @Override
    public Observable<BaseDataResponse<List<String>>> getRecentlyDate() {
        return null;
    }

    @Override
    public Observable<BaseDataResponse<List<GanHuoTitleBean>>> getTitles() {
        return null;
    }

    @Override
    public Observable<BaseDataResponse<List<GanHuoDataBean>>> getGanHuo(String category, int pageIndex) {
        KLog.e("getdata---mLocalDataSource");
        final File dataFile = new File(AppConfig.getInstance().getFilesDir(), UrlConstants.FOOD_LIST_CACHE);

        try {
            Reader reader = new FileReader(dataFile);
            return Observable.just((BaseDataResponse<List<GanHuoDataBean>>)gson.fromJson(reader, new TypeToken<BaseDataResponse<List<GanHuoDataBean>>>(){}.getType()))
                    .flatMap(new Function<BaseDataResponse<List<GanHuoDataBean>>, ObservableSource<BaseDataResponse<List<GanHuoDataBean>>>>() {
                        @Override
                        public ObservableSource<BaseDataResponse<List<GanHuoDataBean>>> apply(BaseDataResponse<List<GanHuoDataBean>> listBean) throws Exception {
                            if(System.currentTimeMillis()-dataFile.lastModified()<1000*60*5){
                                listBean.setUpToDate(true);
                            }else{
                                listBean.setUpToDate(false);
                            }
                            return Observable.just(listBean);
                        }
                    });
        } catch (FileNotFoundException e) {
//            e.printStackTrace();
            return Observable.empty();
        }
    }

    @Override
    public Observable<BaseDataResponse<GanHuoRecentlyBean>> getRecentlyGanHuo(String date) {
        return null;
    }

    @Override
    public Observable<BaseDataResponse<List<SearchResultBean>>> search(String keyword, int pageIndex) {
        return null;
    }
}
