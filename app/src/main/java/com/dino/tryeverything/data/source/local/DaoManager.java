package com.dino.tryeverything.data.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by xintianweng on 2015/11/3.
 */
public class DaoManager {

    // 是否加密
    public static final boolean ENCRYPTED = true;

    private static final String DB_NAME = "tea.db";
    private static DaoManager mDbManager;
    private static DaoMaster.DevOpenHelper mDevOpenHelper;
    private static DaoMaster mDaoMaster;
    private static DaoSession mDaoSession;

    private Context mContext;

    private DaoManager(Context context) {
        this.mContext = context;
        // 初始化数据库信息
        mDevOpenHelper = new DaoMaster.DevOpenHelper(context, DB_NAME);
        getDaoMaster(context);
        getDaoSession(context);
    }

    public static DaoManager getInstance(Context context) {
        if (null == mDbManager) {
            synchronized (DaoManager.class) {
                if (null == mDbManager) {
                    mDbManager = new DaoManager(context);
                }
            }
        }
        return mDbManager;
    }

    /**
     * @desc 获取可读数据库
     * @autor Tiany
     * @time 2016/8/13
     **/
    public static SQLiteDatabase getReadableDatabase(Context context) {
        if (null == mDevOpenHelper) {
            getInstance(context);
        }
        return mDevOpenHelper.getReadableDatabase();
    }

    /**
     * @desc 获取可写数据库
     * @autor Tiany
     * @time 2016/8/13
     **/
    public static SQLiteDatabase getWritableDatabase(Context context) {
        if (null == mDevOpenHelper) {
            getInstance(context);
        }
        return mDevOpenHelper.getWritableDatabase();
    }

    /**
     * @desc 获取DaoMaster
     * @autor Tiany
     * @time 2016/8/13
     **/
    public static DaoMaster getDaoMaster(Context context) {
        if (null == mDaoMaster) {
            synchronized (DaoManager.class) {
                if (null == mDaoMaster) {
                    mDaoMaster = new DaoMaster(getWritableDatabase(context));
                }
            }
        }
        return mDaoMaster;
    }

    /**
     * @desc 获取DaoSession
     * @autor Tiany
     * @time 2016/8/13
     **/
    public static DaoSession getDaoSession(Context context) {
        if (null == mDaoSession) {
            synchronized (DaoManager.class) {
                mDaoSession = getDaoMaster(context).newSession();
            }
        }

        return mDaoSession;
    }

}
