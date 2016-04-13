package com.wangg.wg_coorviewlayout.adapter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.socks.library.KLog;
import com.wangg.wg_coorviewlayout.R;
import com.wangg.wg_coorviewlayout.api.APIService;
import com.wangg.wg_coorviewlayout.api.TuangouImageBean;
import com.wangg.wg_coorviewlayout.bean.PictrueMessageContentBean;
import com.wangg.wg_coorviewlayout.layout.TianGouLayoutAdapter;
import com.wangg.wg_coorviewlayout.view.AnimRFRecyclerView;
import com.wangg.wg_coorviewlayout.view.AnimRFStaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ListFragment extends Fragment {

    //标记加载页数
    private int pageNumber = 1;
    //类别ID
    private int gallertclassID;
    private List<PictrueMessageContentBean> message_picture;
    private TianGouLayoutAdapter tianGouLayoutAdapter;
    private AnimRFRecyclerView animRFRecyclerView;
    private Handler mHandler = new Handler();
    private int pictrueNumber;   // 图片的总数量
    private int pictrueLoadNumber = 20;

    public ListFragment(int pageNumber ,int gallertclassID) {
        this.pageNumber = pageNumber;
        this.gallertclassID = gallertclassID;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        animRFRecyclerView =
                (AnimRFRecyclerView) inflater.inflate(R.layout.layout_tiangoupicture, container, false);
        return animRFRecyclerView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
//        mRecyclerView.setAdapter(new RecyclerViewAdapter(getActivity()));

        animRFRecyclerView.setLayoutManager(new AnimRFStaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));
//        animRFRecyclerView.addFootView(footerView);
        message_picture = new ArrayList<PictrueMessageContentBean>();
        initData();

        tianGouLayoutAdapter = new TianGouLayoutAdapter(message_picture);
        animRFRecyclerView.setAdapter(tianGouLayoutAdapter);
        animRFRecyclerView.setLoadDataListener(new AnimRFRecyclerView.LoadDataListener() {
            @Override
            public void onRefresh() {
                new Thread(new MyRunnable(true)).start();
            }

            @Override
            public void onLoadMore() {
                new Thread(new MyRunnable(false)).start();
            }
        });
    }

    class MyRunnable implements Runnable {

        boolean isRefresh;

        public MyRunnable(boolean isRefresh) {
            this.isRefresh = isRefresh;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (isRefresh) {

                        initData();
                        refreshComplate();
                        animRFRecyclerView.refreshComplate();
                    } else {

                        initData2();
                        loadMoreComplate();
                        animRFRecyclerView.loadMoreComplate();
                    }
                }
            });
        }
    }

    public void refreshComplate() {
        animRFRecyclerView.getAdapter().notifyDataSetChanged();
    }

    public void loadMoreComplate() {
        /*
         * 用notifyDataSetChanged()的话，加载完重新滚动到顶部的时候会产生错位并自动调整布局，
         * 所以用requestLayout()刷新布局
         */
        // mRecyclerView.getAdapter().notifyDataSetChanged();
        animRFRecyclerView.requestLayout();
    }

    private void initData() {
        pageNumber = 1;
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIService.IMAGER_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();

        final APIService apiService = retrofit.create(APIService.class);
        apiService.imagelist(pageNumber, pictrueLoadNumber, gallertclassID).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TuangouImageBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("wg_log","onError:" + e.toString());
                    }

                    @Override
                    public void onNext(TuangouImageBean s) {

                        Log.e("wg_log", "total:" + s.total);
                        pictrueNumber = s.total;
                        for(int i = 0; i < pictrueLoadNumber; i++){

                            message_picture.add(new PictrueMessageContentBean(
                                    APIService.IMAGET_LOOK + s.mTngou.get(i).img, s.mTngou.get(i).id));
                        }
//                        Log.e("wg_log", "message:" + message_picture.toString());
                        animRFRecyclerView.getAdapter().notifyDataSetChanged();
                    }
                });
    }

    private void initData2() {
        pageNumber ++ ;

        Log.e("wg_log", "pageNumber:"+  pageNumber);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIService.IMAGER_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();

        final APIService apiService = retrofit.create(APIService.class);
        apiService.imagelist(pageNumber, 20, gallertclassID).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TuangouImageBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("wg_log","onError:" + e.toString());
                    }

                    @Override
                    public void onNext(TuangouImageBean s) {
                        pictrueNumber -= pictrueLoadNumber;
                        if(pictrueNumber >= pictrueLoadNumber) {
                            for (int i = 0; i < pictrueLoadNumber; i++) {
                                message_picture.add(new PictrueMessageContentBean(
                                        APIService.IMAGET_LOOK + s.mTngou.get(i).img, s.mTngou.get(i).id));
                            }
                        }else if(pictrueNumber < pictrueLoadNumber && pictrueNumber > 0){
                            for (int i = 0; i < pictrueNumber; i++) {
                                message_picture.add(new PictrueMessageContentBean(
                                        APIService.IMAGET_LOOK + s.mTngou.get(i).img, s.mTngou.get(i).id));
                            }
                        }else{
                            Toast.makeText(getContext(), "已经是最后一页了", Toast.LENGTH_SHORT).show();
                        }
                        Log.e("wg_log", "pictrueNumber:" + pictrueNumber);
                        animRFRecyclerView.getAdapter().notifyDataSetChanged();
                    }
                });
    }
}
