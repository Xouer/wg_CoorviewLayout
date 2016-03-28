package com.wangg.wg_coorviewlayout.layout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.wangg.wg_coorviewlayout.api.APIService;
import com.wangg.wg_coorviewlayout.R;
import com.wangg.wg_coorviewlayout.api.TULING;
import com.wangg.wg_coorviewlayout.bean.MessageContentBean;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/3/16.
 */
public class Tuling_dialogueActivity extends AppCompatActivity {

    private RecyclerView dialogueReView;
    private ListView dialogueListView;
    private Button send_btn;
    private EditText text_et;
    private TulingLayoutAdapter tulingLayoutAdapter;
    public List<String> array;//数据
//    public MessageContentBean messageContentBean;
    private List<MessageContentBean> messageArray;
    private String testString;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buling_layout);
//        initData();
//        initView();   //RecyclerView 有bug，取消
        initView2();
    }

    private void initView2() {
        dialogueListView = (ListView)findViewById(R.id.dialog_listview);
        dialogueListView.setItemsCanFocus(true);
        send_btn = (Button) findViewById(R.id.send_btn);
        text_et = (EditText)findViewById(R.id.text_et);

        messageArray = new ArrayList<MessageContentBean>();
        final TulingLayoutAdapter02 tulingLayoutAdapter02 = new TulingLayoutAdapter02(messageArray);
        dialogueListView.setAdapter(tulingLayoutAdapter02);

        text_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogueListView.setSelection(dialogueListView.getCount());
                tulingLayoutAdapter02.notifyDataSetChanged();
            }
        });

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testString = text_et.getText().toString();
//                messageContentBean.setComMeg(true);
//                messageContentBean.setMessagetext(testString);
                messageArray.add(new MessageContentBean(testString, true));
                dialogueListView.setSelection(dialogueListView.getCount());
                //************************************//

                Retrofit retrofit = new Retrofit.Builder().baseUrl(APIService.TULING_HOST)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();

                APIService apiService = retrofit.create(APIService.class);
                apiService.wordAPI(APIService.TULONG_KEY, testString, "123456789")
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<TULING>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("wg_log","onError:" + e.toString());
                            }

                            @Override
                            public void onNext(TULING string) {
                                Log.e("wg_log","call:" + string.text );
                                switch(string.code){
                                    case "100000":
                                        messageArray.add(new MessageContentBean(string.text,false));
                                     Log.e("wg_log", "bean;" + messageArray.size());
                                        tulingLayoutAdapter02.notifyDataSetChanged();
                                        dialogueListView.setSelection(dialogueListView.getCount());
                                        break;
                                }
                            }
                        });
                text_et.setText("");
                Log.e("wg_log", "size():" + messageArray.size());
            }
        });
        }
    }
//    private void initData(){
//        array = new ArrayList<>();
//        for(int i = 0; i < 20; i++){
//            array.add("第一句话" + i);
//        }
//    }

//    private void initView() {
//        dialogueReView = (RecyclerView) findViewById(R.id.dialogue_reccycleView);
//        send_btn = (Button) findViewById(R.id.send_btn);
//        text_et = (EditText)findViewById(R.id.text_et);
//
//
//        dialogueReView.setLayoutManager(new LinearLayoutManager(this));
////        dialogueReView.getHeight();
//
//        messageArray = new ArrayList<MessageContentBean>();
//        tulingLayoutAdapter = new TulingLayoutAdapter(messageArray);
//        dialogueReView.setAdapter(tulingLayoutAdapter);
//
//
////        messageContentBean = new MessageContentBean();
//
//        send_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                testString = text_et.getText().toString();
////                messageContentBean.setComMeg(true);
////                messageContentBean.setMessagetext(testString);
//                messageArray.add(new MessageContentBean(testString, true));
//
//                //************************************//
//
//                Retrofit retrofit = new Retrofit.Builder().baseUrl(APIService.TULING_HOST)
//                        .addConverterFactory(GsonConverterFactory.create())
//                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
//
//                APIService apiService = retrofit.create(APIService.class);
//                apiService.wordAPI(APIService.TULONG_KEY, testString, "123456789")
//                        .subscribeOn(Schedulers.newThread())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Subscriber<TULING>() {
//                            @Override
//                            public void onCompleted() {
//
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                Log.e("wg_log","onError:" + e.toString());
//                            }
//
//                            @Override
//                            public void onNext(TULING string) {
//                                Log.e("wg_log","call:" + string.text );
//                                switch(string.code){
//                                    case "100000":
//                                        messageArray.add(new MessageContentBean(string.text,false));
//                                     Log.e("wg_log", "bean;" + messageArray.size());
//                                        tulingLayoutAdapter.notifyDataSetChanged();
//                                        break;
//                                }
//                            }
//                        });
//                text_et.setText("");
//                Log.e("wg_log", "size():" + messageArray.size());
//                dialogueReView.smoothScrollToPosition(messageArray.size() + 1);
//
//            }
//        });


//    }

