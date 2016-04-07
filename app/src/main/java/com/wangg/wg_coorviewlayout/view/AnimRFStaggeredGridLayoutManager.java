package com.wangg.wg_coorviewlayout.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;

import com.wangg.wg_coorviewlayout.test.OverScrollListener;


/**
 * Created by shichaohui on 2015/8/3 0003.
 * <br/>
 * 增加了{@link OverScrollListener}的StaggeredGridLayoutManager
 */
public class AnimRFStaggeredGridLayoutManager extends StaggeredGridLayoutManager {

    private OverScrollListener mListener;

    public AnimRFStaggeredGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public AnimRFStaggeredGridLayoutManager(int spanCount, int orientation) {
        super(spanCount, orientation);
    }

    //这里处理了垂直滚动的动作  dy 下滑是负数，大小变化差是滑动速度
    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int scrollRange = super.scrollVerticallyBy(dy, recycler, state);

//        Log.e("wg_log", "dy:" + dy) ;
//        Log.e("wg_log", "dy-scrollRange:" + (dy-scrollRange));
        mListener.overScrollBy(dy - scrollRange);

        return scrollRange;
    }

    /**
     * 设置滑动过度监听
     *
     * @param listener
     */
    public void setOverScrollListener(OverScrollListener listener) {
        mListener = listener;
    }

}
