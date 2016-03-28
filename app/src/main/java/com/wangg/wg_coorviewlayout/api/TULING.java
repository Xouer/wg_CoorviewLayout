package com.wangg.wg_coorviewlayout.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/15.
 */
public class TULING implements Serializable{

    @SerializedName("text") public String text;
    @SerializedName("code") public String code;
    @SerializedName("url") public String url;
    @SerializedName("list") @Expose
    public List<listNew> list = new ArrayList<>();


    public class listNew implements Serializable{
        @SerializedName("article") public String article;
        @SerializedName("source") public String source;
        @SerializedName("icon") public String icon;
        @SerializedName("detailurl") public String detailurl;

        @SerializedName("name") public String name;
        @SerializedName("info") public String info;
    }
}
