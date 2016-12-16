package com.dino.tryeverything.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.dino.tryeverything.R;
import com.dino.tryeverything.interf.BaseViewInterface;
import com.dino.tryeverything.utils.DialogHelper;

import butterknife.ButterKnife;


/**
 * Created by Dino on 10/21 0021.
 */
public abstract class BaseFragment extends Fragment implements BaseViewInterface {

    private View view;
    protected DialogHelper dialogHelper;

    /**
     * 绑定布局
     * 使用：View.inflate(getContext(),R.layout.fragment_main_1, null)
     */
    protected abstract int getLayoutId();

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return View.inflate(getActivity(),getLayoutId(),null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);     //注解注册
        dialogHelper = new DialogHelper(getActivity());
        setView(view);


        initView();
        initData();
    }

    @Override
    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(null!=dialogHelper){
            dialogHelper.dismissProgressDialog();
        }
    }


    public void showSnackbar(View view, String s) {
        Snackbar sb = Snackbar.make(view, s, Snackbar.LENGTH_SHORT);
        sb.getView().setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        sb.show();
    }

    /**
     * Toast提示
     */
    public void showToast(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }


    /**
     * 跳转
     */
    public void nextActivity(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        startActivity(intent);
    }

    /**
     * 跳转
     */
    public void nextActivity(Context context, Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }
}
