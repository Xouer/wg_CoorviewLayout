package com.wangg.wg_coorviewlayout.bean;

/**
 * Created by Administrator on 2016/3/22.
 */
public class PictrueMessageContentBean {

    private String PictrueImage;
    private long ID;

    public PictrueMessageContentBean(String pictrueimgge, long ID) {
        this.PictrueImage = pictrueimgge;
        this.ID = ID;
    }

    public String getPictrueImage() {
        return PictrueImage;
    }

    public void setPictrueImage(String pictrueImage) {
        PictrueImage = pictrueImage;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }
}
