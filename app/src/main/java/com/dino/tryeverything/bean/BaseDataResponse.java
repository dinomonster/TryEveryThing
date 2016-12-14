package com.dino.tryeverything.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Dino on 11/3 0003.
 */

public class BaseDataResponse<T> {
    private T results;

    /**
     * 返回说明
     */
    private boolean error;


    /**
     * 数据是否过期
     */
    private boolean upToDate;



    public boolean isUpToDate() {
        return upToDate;
    }

    public void setUpToDate(boolean upToDate) {
        this.upToDate = upToDate;
    }

    public T getResult() {
        return results;
    }

    public void setResult(T results) {
        this.results = results;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
