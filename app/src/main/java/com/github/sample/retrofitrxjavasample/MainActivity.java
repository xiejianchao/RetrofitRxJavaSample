package com.github.sample.retrofitrxjavasample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.github.sample.retrofitrxjavasample.api.GankApi;
import com.github.sample.retrofitrxjavasample.model.GankBeauty;
import com.github.sample.retrofitrxjavasample.model.MovieModel;
import com.github.sample.retrofitrxjavasample.model.TwoResultModel;
import com.github.sample.retrofitrxjavasample.net.ApiManager;
import com.orhanobut.logger.Logger;

import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * 本例以获取豆瓣电影为例进行演示，请求结果以日志形式展示
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

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
        ApiManager.getMovieTop(0,10);
    }


    @OnClick(R.id.btn_post_rxjava)
    public void postRxJavaClick() {
        ApiManager.getMovieTopWithRxJava(0,10);
    }

    /**
     * 这是一个非常实用的操作，有时候我们需要请求两个不同的接口，将两个接口的数据都拿到后才能进行下一步的操作
     * 传统做法你需要请求两次网络，在第一次网络请求成功的回调里执行第二次网络请求和保存第一次请求的结果，然后
     * 在第二次网络请求成功的回调里合并这两次的结果，异常繁琐，而使用RxJava和Retrofit进行基于RxJava的zip
     * 操作，将会异常轻松，一个链式调用即可。
     * <br<
     * 本例将GankApi和DoubanApi的请求结果合并成一个TwoResultModel的结果
     */
    @OnClick(R.id.btn_rxjava_zip)
    public void zipRxJavaClick() {
        Observable.zip(ApiManager.getGankApi().getBeauties(200, 1),
                ApiManager.getDoubanApi().getMoiveTopWithRxJava(0,10),
                new Func2<GankBeauty, MovieModel, TwoResultModel>() {

                    @Override
                    public TwoResultModel call(GankBeauty gankBeauty, MovieModel movieModel) {
                        TwoResultModel model = new TwoResultModel();
                        model.setT1(gankBeauty);
                        model.setT2(movieModel);
                        return model;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TwoResultModel>() {
                    @Override
                    public void onCompleted() {
                        Logger.d("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e,"");
                    }

                    @Override
                    public void onNext(TwoResultModel twoResultModel) {
                        Logger.w("合并的结果获取成功...");
                        Logger.d(twoResultModel.toString());
                    }
                });
    }

}
