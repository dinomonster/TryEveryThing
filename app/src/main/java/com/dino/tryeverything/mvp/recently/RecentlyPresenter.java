package com.dino.tryeverything.mvp.recently;

import android.support.annotation.NonNull;


import com.dino.tryeverything.bean.BaseDataResponse;
import com.dino.tryeverything.bean.GanHuoTitleBean;
import com.dino.tryeverything.data.GanHuoRepository;
import com.socks.library.KLog;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
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
        this.view.setPresenter(this);
        disposables = new CompositeDisposable();
    }



    @Override
    public void getTitles() {
        disposables.clear();

        Disposable disposable = repository
                .getTitles()
//                .compose(RxUtil.normalSchedulers())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseDataResponse<List<GanHuoTitleBean>>>() {
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e(e.getMessage());
                        view.showNetWorkError();
                    }


                    @Override
                    public void onNext(BaseDataResponse<List<GanHuoTitleBean>> response) {
                        KLog.e("response"+response);
                        if(response.getResult()!=null){
                            view.showGanHuoRecently(response.getResult());
                        }
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
