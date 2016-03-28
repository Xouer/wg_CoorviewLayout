package com.wangg.wg_coorviewlayout.test;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.wangg.wg_coorviewlayout.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/23.
 */
public class TestAdapter extends RecyclerView implements Runnable{

    private List<String> ListData;
    private ArrayList<View> mHeaderViews = new ArrayList<>();
    private ArrayList<View> mFootViews =  new ArrayList<>();
    private Adapter mAdapter;
    private View mFooterView;
    private int mFooterViewHeight;

    private boolean isEnable = true;
    private boolean isTouching = false; // 是否正在手指触摸的标识
    private boolean isLoadingData = false; // 是否正在加载数据

    private OverScrollListener mOverScrollListener = new OverScrollListener() {
        @Override
        public void overScrollBy(int dy) {
            // dy为拉伸过度时每毫秒拉伸的距离，正数表示向上拉伸多度，负数表示向下拉伸过度
            if (isEnable && !isLoadingData && isTouching) {
//                mHandler.obtainMessage(0, dy, 0, null).sendToTarget();
                onScrollChanged(0, 0, 0, 0);
            }
        }
    };

    public TestAdapter(Context context) {
        super(context);
    }

    public TestAdapter(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public TestAdapter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    private void initFooterView() {
        mFooterView = View.inflate(getContext(), R.layout.footer_view, null);
        this.addFootView(mFooterView);
//        mFooterView.measure(0, 0);
//        mFooterViewHeight = mFooterView.getMeasuredHeight();
//
//        mFooterView.setPadding(0,-mFooterViewHeight, 0, 0);
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        // 当前不滚动，且不是正在刷新或加载数据
        if(state == RecyclerView.SCROLL_STATE_IDLE && mLoadDataListener != null && !isLoadingData){
            LayoutManager layoutManager = getLayoutManager();  //获取当前的布局信息
            int lastVisibleItemPosition = 0;

            if(layoutManager instanceof AnimRFStaggeredGridLayoutManager){
                int[] into =new int[ ((AnimRFStaggeredGridLayoutManager) layoutManager).getSpanCount()];
                ((AnimRFStaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(into);
                lastVisibleItemPosition = findMax(into);
            }

            if(layoutManager.getChildCount() > 0
                    && lastVisibleItemPosition >= layoutManager.getItemCount() - 1){
                if(mFootViews.size() > 0){
                    mFootViews.get(0).setVisibility(VISIBLE);
                }
                //加载更多
                isLoadingData = true;
                mLoadDataListener.onLoadMore();
            }
        }
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);


    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch(e.getAction()){
            case MotionEvent.ACTION_DOWN:
                isTouching = true;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
                break;
        }

        return super.onTouchEvent(e);
    }

    /**
     * 添加头部视图，可以添加多个
     *
     * @param view 头部视图
     */
    public void addHeaderView(View view) {
        mHeaderViews.add(view);
        if (mAdapter != null) {
            if (!(mAdapter instanceof WrapAdapter)) {
                mAdapter = new WrapAdapter(mHeaderViews, mFootViews, mAdapter);
            }
        }
    }

    public void addFootView(final View view){
        mFootViews.clear();
        mFootViews.add(view);
        if (mAdapter != null) {
            if (!(mAdapter instanceof WrapAdapter)) {
                mAdapter = new WrapAdapter(mHeaderViews, mFootViews, mAdapter);
            }
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
//        if (mHeaderViews.isEmpty() || headerImage == null && isEnable) {
//            // 新建头部
//            RelativeLayout headerLayout = new RelativeLayout(mContext);
//            headerLayout.setLayoutParams(new LayoutParams(
//                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

//            headerImage = new AnimImageView(mContext);
//            ((AnimImageView) headerImage).setColor(progressColor, bgColor);
//            headerImage.setMaxHeight(dp1 * 130);
//            headerLayout.addView(headerImage, RelativeLayout.LayoutParams.MATCH_PARENT, dp1);
//            setScaleRatio(130);
//            setHeaderImage(headerImage);

//            mHeaderViews.add(0, headerLayout);
//        }
        if (mFootViews.isEmpty()) {
            // 新建脚部
            LinearLayout footerLayout = new LinearLayout(getContext());
            footerLayout.setGravity(Gravity.CENTER);
            footerLayout.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            mFootViews.add(footerLayout);

            footerLayout.addView(new ProgressBar(getContext(), null, android.R.attr.progressBarStyleSmall));
            TextView text = new TextView(getContext());
            text.setText("正在加载...aaa");
            footerLayout.addView(text);
        }
        // 使用包装了头部和脚部的适配器
        adapter = new WrapAdapter(mHeaderViews, mFootViews, adapter);
        super.setAdapter(adapter);
        // 根据是否有头部/脚部视图选择适配器
//         if (mHeaderViews.isEmpty() && mFootViews.isEmpty()) {
//             super.setAdapter(adapter);
//         } else {
//             adapter = new WrapAdapter(mHeaderViews, mFootViews, adapter);
//             super.setAdapter(adapter);
//         }
        mAdapter = adapter;
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(layout);
        // 根据布局管理器设置分割线
        if (layout instanceof AnimRFLinearLayoutManager) {
            super.addItemDecoration(new DividerItemDecoration(getContext(),
                    ((AnimRFLinearLayoutManager) layout).getOrientation(), true));
        } else {
            super.addItemDecoration(new DividerGridItemDecoration(getContext(), true));
        }
    }

    @Override
    public void run() {

    }


    /**
     *  自定义带有头部/脚部的适配器
     */
    private class WrapAdapter extends RecyclerView.Adapter<ViewHolder>{

        final ArrayList<View> EMPTY_INFO_LIST = new ArrayList<>();
        private  ArrayList<View> mFootViews;
        private  ArrayList<View> mHeaderView;
        private RecyclerView.Adapter mAdapter;
        private int headerPosition = 0;

        public WrapAdapter(ArrayList<View> mHeaderViews, ArrayList<View> mFootViews,
                           RecyclerView.Adapter mAdapter){
            this.mAdapter = mAdapter;

            if(mHeaderViews == null){
                this.mHeaderView = EMPTY_INFO_LIST;
            } else {
                this.mHeaderView = mHeaderViews;
            }
            if(mFootViews == null){
                this.mFootViews = EMPTY_INFO_LIST;
            } else {
                this.mFootViews = mFootViews;
            }
        }

        public boolean isHeader(int position){
            return position > 0 && position < mHeaderView.size();
        }

        /**
         * @param position 位置
         * @return 当前布局是否为Footer
         */
        public boolean isFooter(int position) {
            return position < getItemCount() && position >= getItemCount() - mFootViews.size();
        }

        public int getHeadersCount(){
            return mHeaderView.size();
        }

        public int getFootersCount(){
            return mFootViews.size();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if(viewType == RecyclerView.INVALID_TYPE){
                return new HeaderViewHolder(mHeaderView.get(headerPosition++));
            }else if (viewType == RecyclerView.INVALID_TYPE - 1) {
                StaggeredGridLayoutManager.LayoutParams params = new StaggeredGridLayoutManager.LayoutParams(
                        StaggeredGridLayoutManager.LayoutParams.MATCH_PARENT, StaggeredGridLayoutManager.LayoutParams.WRAP_CONTENT);
                params.setFullSpan(true);
                mFootViews.get(0).setLayoutParams(params);
                return new HeaderViewHolder(mFootViews.get(0));
            }
            return mAdapter.onCreateViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            int numHeaders = getHeadersCount();
            if(position < numHeaders){
                return ;
            }
            int adjPosition = position - numHeaders;
            int adapterCount;
            if(mAdapter != null){
                adapterCount = mAdapter.getItemCount();
                if(adjPosition < adapterCount){
                    mAdapter.onBindViewHolder(holder, adjPosition);
                }
            }
        }

        @Override
        public int getItemCount() {
            if(mAdapter != null){
                return getHeadersCount() + getFootersCount() + mAdapter.getItemCount();
            }else{
                return getHeadersCount() + getFootersCount();
            }
        }

        @Override
        public int getItemViewType(int position) {
            int numHeaders = getHeadersCount();
            if(position < numHeaders){
                return RecyclerView.INVALID_TYPE;
            }
            int adjPosition = position - numHeaders;
            int adapterCount;
            if(mAdapter != null){
                adapterCount = mAdapter.getItemCount();
                if(adjPosition < adapterCount){
                    return mAdapter.getItemViewType(adjPosition);
                }
            }
            return RecyclerView.INVALID_TYPE - 1;
        }

        @Override
        public long getItemId(int position) {
            int numHeaders = getHeadersCount();
            if(mAdapter != null && position >= numHeaders){
                int adjPosition = position - numHeaders;
                int adapterCount = mAdapter.getItemCount();
                if(adjPosition < adapterCount){
                    return mAdapter.getItemId(adjPosition);
                }
            }
            return -1;
        }

        private class HeaderViewHolder extends RecyclerView.ViewHolder{
            public HeaderViewHolder(View itemView) {
                super(itemView);
            }
        }

    }

    private LoadDataListener mLoadDataListener;

    public void setLoadDataListener(LoadDataListener listener){
        mLoadDataListener = listener;
    }

    /**
     * 刷新和加载更多数据的监听接口
     */
    public interface LoadDataListener{
        void onRefresh();
        void onLoadMore();
    }
}
