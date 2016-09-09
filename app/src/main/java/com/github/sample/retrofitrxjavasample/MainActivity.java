package com.github.sample.retrofitrxjavasample;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.github.sample.retrofitrxjavasample.net.Api;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 本例以获取豆瓣电影为例进行演示，请求结果以日志形式展示
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);


    }

    @OnClick(R.id.btn_post)
    public void postClick() {
        Api.getMovieTop(0,10);
    }


    @OnClick(R.id.btn_post_rxjava)
    public void postRxJavaClick() {
        Api.getMovieTopWithRxJava(0,10);
    }

}
