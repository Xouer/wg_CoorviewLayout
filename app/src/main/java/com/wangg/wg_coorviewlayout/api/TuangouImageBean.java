package com.wangg.wg_coorviewlayout.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/21.
 */
public class TuangouImageBean implements Serializable{

    @SerializedName("tngou") @Expose
    public List<tngou> mTngou
            = new ArrayList<>();

    @SerializedName("total")
    public int total;

    public class tngou implements Serializable{
        @SerializedName("id") public long id;
        @SerializedName("title") public String title;
        @SerializedName("img") public String img;
        @SerializedName("gallertclass") public int gallertclass;
        @SerializedName("count") public int count;
        @SerializedName("rcount") public int rcount;
        @SerializedName("fcount") public int  fcount;
        @SerializedName("time") public long time;
    }
}
