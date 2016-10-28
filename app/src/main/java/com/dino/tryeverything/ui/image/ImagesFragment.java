package com.dino.tryeverything.ui.image;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.dino.tryeverything.R;
import com.dino.tryeverything.base.BaseFragment;
import com.dino.tryeverything.bean.Image;
import com.dino.tryeverything.utils.AnimatorUtils;
import com.google.common.base.Preconditions;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by Dino on 10/24 0024.
 */

public class ImagesFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,ImagesContract.View{

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swiprefresh)
    SwipeRefreshLayout swiprefresh;

    private ImagesAdapter adapter;

    private ImagesContract.Presenter mPresenter;

    public static final int REQUEST_CODE_GALLERY = 1001;
    public static int PAGENO = 0;
    public static final int PAGESIZE = 5;

    private boolean isRefresh = true;

    public ImagesFragment() {
    }

    public static ImagesFragment newInstance() {
        return new ImagesFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_image;
    }

    @Override
    public void initView() {
//        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
//        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);//瀑布流
//        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new ImagesAdapter(getActivity(), recyclerView, R.layout.item_image, null);
        adapter.openLoadAnimation(2);
        recyclerView.setAdapter(adapter);

        /** 下拉刷新颜色 */
        swiprefresh.setColorSchemeResources(
                R.color.swiperefresh_color1, R.color.swiperefresh_color2,
                R.color.swiperefresh_color3, R.color.swiperefresh_color4);

        swiprefresh.setOnRefreshListener(this);

        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                AnimatorUtils.startRotation(getActivity(), view, 500, new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                    }

                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                    }
                });
            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                super.onItemChildClick(adapter, view, position);
                switch (view.getId()) {
                    case R.id.image:
                        break;
                }

            }
        });

        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                isRefresh = false;
                mPresenter.loadImage(++PAGENO,PAGESIZE);
            }
        });


        // Set up floating action button
        FloatingActionButton fab =
                (FloatingActionButton) getActivity().findViewById(R.id.fab);

        fab.setImageResource(R.drawable.ic_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.addNewImage();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @Override
    public void initData() {
        PAGENO = 0;
        swiprefresh.setRefreshing(true);
        adapter.openLoadMore(PAGESIZE);
        mPresenter.subscribe();
    }

    @Override
    public void onRefresh() {
        PAGENO = 0;
        isRefresh = true;
        mPresenter.loadImage(PAGENO,PAGESIZE);
    }

    @Override
    public void setPresenter(ImagesContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    public void showImagePicker() {
        goImageSelect();
    }

    @Override
    public void showNetWorkError() {
        adapter.showNetWorkErrorView();
    }

    @Override
    public void showImages(List<Image> images) {
        if(isRefresh)adapter.removeAll();
        swiprefresh.setRefreshing(false);
//        adapter.dismissFooter();
        adapter.addData(images);

        if(images.size()<PAGESIZE){
            adapter.showFooter();
            adapter.loadComplete();
        }else{
            adapter.dismissFooter();
            adapter.openLoadMore(PAGESIZE);
        }
    }

    private void goImageSelect() {
        FunctionConfig config = new FunctionConfig.Builder()
                .setMutiSelectMaxSize(9)
                .build();
        GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERY, config, new GalleryFinal.OnHanlderResultCallback() {
            @Override
            public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                if (reqeustCode==REQUEST_CODE_GALLERY&&resultList != null) {

                    for (int i = 0; i < resultList.size(); i++) {
                        File file = new File(resultList.get(i).getPhotoPath());
                        Image image = new Image();
                        image.setDescription(file.getName());
                        image.setImage_url(file.getAbsolutePath());
//                        adapter.addData(image);
                        isRefresh = true;
                        PAGENO = 0;
                        mPresenter.saveImage(image);
                    }

                }
            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {

            }
        });
    }
}
