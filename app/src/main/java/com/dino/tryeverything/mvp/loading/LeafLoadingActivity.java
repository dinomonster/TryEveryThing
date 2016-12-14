package com.dino.tryeverything.mvp.loading;

import android.os.Handler;
import android.os.Message;

import com.dino.tryeverything.R;
import com.dino.tryeverything.base.BaseActivity;
import com.dino.tryeverything.widget.LeafLoadingView;

import java.util.Random;

import butterknife.BindView;

/**
 * Created by Dino on 10/9 0009.
 */

public class LeafLoadingActivity extends BaseActivity {
    @BindView(R.id.loading_view)
    LeafLoadingView loadingView;
    private LeafLoadingView mLeafLoadingView;

    private static final int REFRESH_PROGRESS = 0x10;
    private int mProgress;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_leafloading;
    }

    @Override
    public void initView() {
        setToolbarCentel(true, getResources().getString(R.string.leafloading));
        mLeafLoadingView = (LeafLoadingView) findViewById(R.id.loading_view);
        mHandler.sendEmptyMessageDelayed(REFRESH_PROGRESS, 500);
    }

    @Override
    public void initData() {

    }

    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REFRESH_PROGRESS:
                    if (mProgress < 40) {
                        mProgress += 1;
                        // 随机800ms以内刷新一次
                        mHandler.sendEmptyMessageDelayed(REFRESH_PROGRESS,
                                new Random().nextInt(800));
                        mLeafLoadingView.setCurrentProgress(mProgress);
                    } else {
                        mProgress += 1;
                        // 随机1200ms以内刷新一次
                        mHandler.sendEmptyMessageDelayed(REFRESH_PROGRESS,
                                new Random().nextInt(1200));
                        mLeafLoadingView.setCurrentProgress(mProgress);

                    }
                    break;

                default:
                    break;
            }
        }

        ;
    };
}
