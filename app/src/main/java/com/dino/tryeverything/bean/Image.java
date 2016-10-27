package com.dino.tryeverything.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Entity mapped to table "IMAGE".
 */
@Entity
public class Image {

    @Id(autoincrement = true)
    private Long imageId;
    private String description;
    @Unique
    private String image_url;
    @Generated(hash = 566878717)
    public Image(Long imageId, String description, String image_url) {
        this.imageId = imageId;
        this.description = description;
        this.image_url = image_url;
    }
    @Generated(hash = 1590301345)
    public Image() {
    }
    public Long getImageId() {
        return this.imageId;
    }
    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getImage_url() {
        return this.image_url;
    }
    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }




}
