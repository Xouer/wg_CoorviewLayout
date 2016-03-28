package com.wangg.wg_coorviewlayout;


import android.util.Log;

import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

/**
 * Created by Administrator on 2016/3/11.
 */
public class TEST {

    public static void main(String args[]) {

        String[] names = {"ss","s", "s03", "s04"};
        Observable.from(names).subscribe(new Action1<String>() {
            @Override
            public void call(String name) {
                Log.d("wg_log","call:" + name);
            }
        });



    }
}
