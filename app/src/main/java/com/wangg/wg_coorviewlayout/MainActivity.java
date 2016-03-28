package com.wangg.wg_coorviewlayout;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wangg.wg_coorviewlayout.layout.Tuling_dialogueActivity;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private DrawerLayout drawer_layout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initview();
    }

    private void initview() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_favorite_24dp);
        mToolbar.setTitle("项目集合");

        drawer_layout = (DrawerLayout)findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, drawer_layout, mToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer_layout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);

        setupDrawerContent(navigationView);  //navigationview跳转
    }



    public void setupDrawerContent(NavigationView upDrawerContent) {
        upDrawerContent.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.nav_city:
                                Intent intent = new Intent();
                                intent.setClass(MainActivity.this,Tuling_dialogueActivity.class);
                                startActivity(intent);
                                break;
//                            case R.id.navigation_item_example:
//                                switchToExample();
//                                break;
//                            case R.id.navigation_item_blog:
//                                switchToBlog();
//                                break;
//                            case R.id.navigation_item_about:
//                                switchToAbout();
//                                break;

                        }
//                        item.setChecked(true);
                        drawer_layout.closeDrawers();
                        return true;
                    }
                }
        );
    }
}
