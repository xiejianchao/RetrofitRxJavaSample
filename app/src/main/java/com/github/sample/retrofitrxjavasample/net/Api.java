package com.github.sample.retrofitrxjavasample.net;

import com.github.sample.retrofitrxjavasample.api.MoiveService;
import com.github.sample.retrofitrxjavasample.model.MovieModel;
import com.orhanobut.logger.Logger;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by xiejc on 16/9/9.
 */
public class Api {

    private static final String TAG = Api.class.getSimpleName();

    private static Retrofit mRetrofit;
    private static Retrofit mRetrofitRxJava;

    static {
        mRetrofit =new Retrofit.Builder()
                //这里为主机加端口（或域名）
//                .baseUrl("http://192.168.1:8080/")
                .baseUrl("https://api.douban.com/")
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mRetrofitRxJava= new Retrofit.Builder()
                .baseUrl("https://api.douban.com/")
                //由于需要转为Observable 需要添加RxJavaCallAdapterFactory
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static void getMovieTop(int start,int count) {
        MoiveService movie = mRetrofit.create(MoiveService.class);

        Call<MovieModel> movieTop = movie.getMoiveTop(start + "", count + "");
        movieTop.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                Logger.d("response:" + response.body());
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {
                Logger.d("getMovieTop",t.getMessage());
            }
        });

    }

    public static void getMovieTopWithRxJava(int start,int count) {
        MoiveService movie = mRetrofitRxJava.create(MoiveService.class);

        movie.getMoiveTopWithRxJava(start + "", count + "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MovieModel>() {
                    @Override
                    public void onCompleted() {
                        Logger.d(TAG,"获取豆瓣电影完成");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(TAG,"获取豆瓣电影失败，",e.getMessage());
                    }

                    @Override
                    public void onNext(MovieModel movieModel) {
                        Logger.d(TAG,"retrofit和rxjava配合获取豆瓣电影成功：" + movieModel.toString());
                    }
                });

    }

}
