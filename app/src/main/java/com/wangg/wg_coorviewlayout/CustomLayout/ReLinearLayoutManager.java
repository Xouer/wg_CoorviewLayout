package com.wangg.wg_coorviewlayout.CustomLayout;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2016/3/17.
 */
public class ReLinearLayoutManager extends LinearLayout {


    public ReLinearLayoutManager(Context context) {
        super(context);
    }

    public ReLinearLayoutManager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
//        Log.e("onSizeChanged ", "=>onResize called! w="+w + ",h="+h+",oldw="+oldw+",oldh="+oldh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        Log.e("onMeasure ", "=>onMeasure called! widthMeasureSpec=" + widthMeasureSpec + ", heightMeasureSpec=" + heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
//        Log.e("onLayout ", "=>OnLayout called! l=" + l + ", t=" + t + ",r=" + r + ",b="+b);
    }
}
