package com.wangg.wg_coorviewlayout.CustomLayout;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2016/3/24.
 */
public class AnimRecycleView extends RecyclerView  {

    private LoadDataListener mLoadDataListener;
    private boolean isLoadingData = false; // 是否正在加载数据

    public void setmLoadDataListener(LoadDataListener mLoadDataListener) {
        this.mLoadDataListener = mLoadDataListener;
    }

    public AnimRecycleView(Context context) {
        super(context);
    }

    public AnimRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void init(){

    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);

        if(state == RecyclerView.SCROLL_STATE_IDLE &&
                mLoadDataListener != null && !isLoadingData){

        }
    }

    public interface LoadDataListener{
        /**
         * 加载更多
         */
        void onLoadMore();
    }
}
