package com.wangg.wg_coorviewlayout.CustomLayout;

import android.support.v4.util.CircularArray;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wangg.wg_coorviewlayout.R;

import java.util.List;

/**
 * Created by Administrator on 2016/3/24.
 */
public abstract class BaseLoadingAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final  String TAG = "BaseLoadingAdapter";
    //是否正在加载
    public boolean mIsLoading =  false;
    //正常条目
    private static final int TYPE_NORMAL_ITEM = 0;
    //加载条目
    private static final int TYPE_LOADING_ITEM = 1;
    //加载viewHolder
    private LoadingViewHolder mLoadingViewHolder;
    //瀑布流
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    //首次进入
    private boolean mFirstEnter = true;
    //数据集
    private CircularArray<T> mTs;

    public BaseLoadingAdapter(){

    }

    public interface OnLoadingListener{
        void loading();
    }

    //显示加载
    private void notifyLoading(){
        if(mTs.size() != 0 && mTs.getLast() == null){
            mTs.addLast(null);
            notifyItemInserted(mTs.size() - 1);
        }

    }

    private void setScrollListener(RecyclerView recyclerView){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //首次进入不加载
                if(!mIsLoading && mFirstEnter){
                    notifyLoading();
                    mIsLoading = true;

                    if(mLoadingViewHolder != null){
                        mLoadingViewHolder.tv_loading.setText("正在加载中");
                    }

//                    if(mOnLoadingListener != null){
//                        mOnLoadingListener.loading();
//                    }
                }
            }
        });
    }

    /**
     *  创建viewHolder
     * @param parent
     * @return
     */
    public abstract RecyclerView.ViewHolder onCreateNormalViewHolder(ViewGroup parent);
    /**
     * 绑定viewholder
     * @param holder
     * @param position
     */
    public  abstract void onBindNormalVieHolder(RecyclerView.ViewHolder holder, int position);

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_NORMAL_ITEM){
            return onCreateNormalViewHolder(parent);
        }else{
            View view  = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.itemdownre_view, parent, false);
            mLoadingViewHolder = new LoadingViewHolder(view);
            return mLoadingViewHolder;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type =  getItemViewType(position);
        if(type == TYPE_NORMAL_ITEM){
            onBindNormalVieHolder(holder, position);
        }else{
            //
            StaggeredGridLayoutManager.LayoutParams layoutParams =
                    new StaggeredGridLayoutManager.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setFullSpan(true);
            mLoadingViewHolder.lly_logding.setLayoutParams(layoutParams);
        }
    }

    @Override
    public int getItemViewType(int position) {
        T t =  mTs.get(position);
        if(t == null){
            return TYPE_LOADING_ITEM;
        }else{
            return TYPE_NORMAL_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return mTs.size();
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder{

        private  TextView tv_loading;
        private  LinearLayout lly_logding;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            tv_loading =  (TextView) itemView.findViewById(R.id.tv_loading);
            lly_logding =  (LinearLayout) itemView.findViewById(R.id.lly_loading);
        }
    }
}
