package com.dino.tryeverything.ui.image;

import android.support.annotation.NonNull;
import android.util.Log;


import com.dino.tryeverything.data.Repository;
import com.dino.tryeverything.bean.Image;

import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Dino on 10/25 0025.
 */

public class ImagesPresenter implements ImagesContract.Presenter{

    @NonNull
    private ImagesContract.View view;

    @NonNull
    private Repository repository;

    @NonNull
    private CompositeSubscription mSubscriptions;


    public ImagesPresenter(@NonNull ImagesContract.View view,
                           @NonNull Repository repository) {
        this.view = checkNotNull(view, "view cannot be null!");
        this.repository = checkNotNull(repository, "repository cannot be null!");
        this.view.setPresenter(this);
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void addNewImage() {
        view.showImagePicker();
    }

    @Override
    public void saveImage(Image image) {
        repository.saveImage(image)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Image>() {
                    @Override
                    public void call(Image image) {
                        loadImage(ImagesFragment.PAGENO,ImagesFragment.PAGESIZE);
                    }
                });
    }

    @Override
    public void loadImage(int pageno,int pagesize) {
        mSubscriptions.clear();
        Subscription subscription = repository
                .getImages(pageno,pagesize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Image>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("onError","onError:"+e.getMessage());

                    }

                    @Override
                    public void onNext(List<Image> images) {
                        view.showImages(images);
                    }
                });
        mSubscriptions.add(subscription);
    }


    @Override
    public void subscribe() {
        //加载数据
        loadImage(ImagesFragment.PAGENO,ImagesFragment.PAGESIZE);
    }

    @Override
    public void unsubscribe() {
        //清除数据
        mSubscriptions.clear();
    }


}
