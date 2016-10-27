package com.dino.tryeverything.data.local;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.dino.tryeverything.bean.Image;

import com.dino.tryeverything.data.local.ImageDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig imageDaoConfig;

    private final ImageDao imageDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        imageDaoConfig = daoConfigMap.get(ImageDao.class).clone();
        imageDaoConfig.initIdentityScope(type);

        imageDao = new ImageDao(imageDaoConfig, this);

        registerDao(Image.class, imageDao);
    }
    
    public void clear() {
        imageDaoConfig.clearIdentityScope();
    }

    public ImageDao getImageDao() {
        return imageDao;
    }

}