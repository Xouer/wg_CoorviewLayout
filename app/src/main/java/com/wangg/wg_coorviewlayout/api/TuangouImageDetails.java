package com.wangg.wg_coorviewlayout.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/11.
 */
public class TuangouImageDetails implements Serializable{

    @SerializedName("status") public boolean status;
    @SerializedName("size") public int size;
    @SerializedName("title") public String title;

    @SerializedName("list") @Expose
    public List<list> list = new ArrayList<>();


    public class list implements Serializable{
        @SerializedName("gallery") public  int gallery;
        @SerializedName("id") public long id;
        @SerializedName("src") public String src;
    }
}
