package com.dino.tryeverything.ui.image;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.dino.tryeverything.R;
import com.dino.tryeverything.base.BaseFragment;
import com.dino.tryeverything.data.source.local.Image;
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
//        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),3);
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);//瀑布流
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new ImagesAdapter(getActivity(), recyclerView, R.layout.item_image, null);
        recyclerView.setAdapter(adapter);

        /** 下拉刷新颜色 */
        swiprefresh.setColorSchemeResources(
                R.color.swiperefresh_color1, R.color.swiperefresh_color2,
                R.color.swiperefresh_color3, R.color.swiperefresh_color4);
        swiprefresh.setOnRefreshListener(this);

        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {

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
        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @Override
    public void initData() {

    }

    @Override
    public void onRefresh() {

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
    public void showImages(List<Image> images) {
        adapter.removeAll();
        adapter.addData(images);
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
                        image.setName(file.getName());
                        image.setPath(file.getAbsolutePath());
//                        adapter.addData(image);
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
