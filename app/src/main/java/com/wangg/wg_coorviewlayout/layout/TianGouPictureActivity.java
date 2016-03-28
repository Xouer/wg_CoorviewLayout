package com.wangg.wg_coorviewlayout.layout;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.wangg.wg_coorviewlayout.R;
import com.wangg.wg_coorviewlayout.SpacesItemDecoration;
import com.wangg.wg_coorviewlayout.api.APIService;
import com.wangg.wg_coorviewlayout.api.TuangouImageBean;
import com.wangg.wg_coorviewlayout.test.AnimRFRecyclerView;
import com.wangg.wg_coorviewlayout.test.AnimRFStaggeredGridLayoutManager;
import com.wangg.wg_coorviewlayout.test.TestAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.ActionN;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/3/22.
 */
public class TianGouPictureActivity extends AppCompatActivity{

    private RecyclerView mRePicture;
    private List<String> message_picture;
    private TianGouLayoutAdapter tianGouLayoutAdapter;
    private AnimRFRecyclerView animRFRecyclerView;
    private View footerView;
    private Handler mHandler = new Handler();
    //标记加载页数
    private static int pageNumber = 1;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.layout_tiangoupicture);

//        initView();
//        animRFRecyclerView = new  TestAdapter(this);
        animRFRecyclerView = (AnimRFRecyclerView) findViewById(R.id.recylerview_picture);

//        footerView = LayoutInflater.from(this).inflate(R.layout.footer_view, null);
        animRFRecyclerView.setLayoutManager(new AnimRFStaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));
//        animRFRecyclerView.addFootView(footerView);
        message_picture = new ArrayList<String>();
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

    private void initView() {

        mRePicture = (RecyclerView) findViewById(R.id.recylerview_picture);

        mRePicture.setLayoutManager(new StaggeredGridLayoutManager(2,
                 StaggeredGridLayoutManager.VERTICAL));

        message_picture = new ArrayList<String>();

        initData();
        tianGouLayoutAdapter = new TianGouLayoutAdapter(message_picture);
        mRePicture.setAdapter(tianGouLayoutAdapter);
    }

    private void initData() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIService.IMAGER_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();

        final APIService apiService = retrofit.create(APIService.class);
        apiService.imagelist(pageNumber, 20,1).subscribeOn(Schedulers.newThread())
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
                        for(int i = 0; i < 20; i++){
                            message_picture.add(APIService.IMAGET_LOOK + s.mTngou.get(i).img);
                        }
//                        Log.e("wg_log", "message:" + message_picture.toString());
                        animRFRecyclerView.getAdapter().notifyDataSetChanged();
                    }
                });
    }

    private void initData2() {
        pageNumber ++ ;
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIService.IMAGER_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();

        final APIService apiService = retrofit.create(APIService.class);
        apiService.imagelist(pageNumber, 20,1).subscribeOn(Schedulers.newThread())
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
                        for(int i = 0; i < 20; i++){
                            message_picture.add(APIService.IMAGET_LOOK + s.mTngou.get(i).img);
                        }
//                        Log.e("wg_log", "message:" + message_picture.toString());
//                        tianGouLayoutAdapter.notifyDataSetChanged();
                        animRFRecyclerView.getAdapter().notifyDataSetChanged();
                    }
                });
    }
}
