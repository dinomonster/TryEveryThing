package com.dino.tryeverything.mvp.recently;

import android.support.annotation.NonNull;


import com.dino.tryeverything.bean.BaseDataResponse;
import com.dino.tryeverything.bean.GanHuoRecentlyBean;
import com.dino.tryeverything.bean.GanHuoRecentlyWrapper;
import com.dino.tryeverything.bean.GanHuoTitleBean;
import com.dino.tryeverything.data.GanHuoRepository;
import com.dino.tryeverything.utils.RxUtil;
import com.socks.library.KLog;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Dino on 10/25 0025.
 */

public class RecentlyPresenter implements RecentlyContract.Presenter{

    @NonNull
    private RecentlyContract.View view;

    @NonNull
    private GanHuoRepository repository;

    @NonNull
    private CompositeDisposable disposables;



    public RecentlyPresenter(@NonNull RecentlyContract.View view,
                             @NonNull GanHuoRepository foodsRepository) {
        this.view = checkNotNull(view, "view cannot be null!");
        this.repository = checkNotNull(foodsRepository, "repository cannot be null!");
        disposables = new CompositeDisposable();
    }



    @Override
    public void getTitles() {
        Disposable disposable = repository
                .getTitles()
                .zipWith(repository.getRecentlyDate(), new BiFunction<BaseDataResponse<List<GanHuoTitleBean>>, BaseDataResponse<List<String>>, GanHuoRecentlyWrapper>() {
                    @Override
                    public GanHuoRecentlyWrapper apply(BaseDataResponse<List<GanHuoTitleBean>> listBaseDataResponse, BaseDataResponse<List<String>> listBaseDataResponse2) throws Exception {
                        GanHuoRecentlyWrapper wrapper = new GanHuoRecentlyWrapper();
                        wrapper.dateList = listBaseDataResponse2.getResult().subList(0, 5);
                        wrapper.titleList = listBaseDataResponse.getResult();
                        for (int i = 0; i < wrapper.dateList.size(); i++) {
                            String title = wrapper.titleList.get(i).getTitle();
                            title = "[" + wrapper.dateList.get(i) + "] :" + title;
                            wrapper.titleList.get(i).setTitle(title);
                        }
                        return wrapper;
                    }
                })
//                .compose(RxUtil.normalSchedulers())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<GanHuoRecentlyWrapper>() {
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e(e.getMessage());
                        view.showNetWorkError();
                    }


                    @Override
                    public void onNext(GanHuoRecentlyWrapper response) {
                        KLog.e("response"+response);
                        view.showGanHuoRecently(response);
                    }
                });
        disposables.add(disposable);
    }

    @Override
    public void getRecentlyGanHuo(String date) {
        Disposable disposable = repository
                .getRecentlyGanHuo(date)
//                .compose(RxUtil.normalSchedulers())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseDataResponse<GanHuoRecentlyBean>>() {
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e(e.getMessage());
                        view.showNetWorkError();
                    }


                    @Override
                    public void onNext(BaseDataResponse<GanHuoRecentlyBean> response) {
                        KLog.e("response"+response);
                        view.showGanHuoRecently(response.getResult());
                    }
                });
        disposables.add(disposable);
    }


    @Override
    public void subscribe() {
        //加载数据
//        loadFoods(RecentlyFragment.PAGENO, RecentlyFragment.PAGESIZE);
        getTitles();
    }

    @Override
    public void unsubscribe() {
        //清除数据
        disposables.clear();
    }


}
