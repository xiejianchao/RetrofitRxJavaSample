package com.github.sample.retrofitrxjavasample.net;

import com.github.sample.retrofitrxjavasample.api.GankApi;
import com.github.sample.retrofitrxjavasample.api.DoubanApi;
import com.github.sample.retrofitrxjavasample.model.MovieModel;
import com.orhanobut.logger.Logger;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xiejc on 16/9/9.
 */
public class ApiManager {

    private static final String TAG = ApiManager.class.getSimpleName();

    private static Retrofit mRetrofit;
    private static Retrofit mRetrofitRxJava;
    private static GankApi gankApi;
    private static DoubanApi doubanApi;

    private static OkHttpClient okHttpClient = new OkHttpClient();
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();

    static {
        mRetrofit =new Retrofit.Builder()
                //这里为主机加端口（或域名）
//                .baseUrl("http://192.168.1:8080/")
                .baseUrl("https://api.douban.com/")
                .client(okHttpClient)
                .addConverterFactory(gsonConverterFactory)
                .build();

        mRetrofitRxJava= new Retrofit.Builder()
                .baseUrl("https://api.douban.com/")
                //由于需要转为Observable 需要添加RxJavaCallAdapterFactory
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .addConverterFactory(gsonConverterFactory)
                .build();
    }

    public static GankApi getGankApi() {
        if (gankApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://gank.io/api/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            gankApi = retrofit.create(GankApi.class);
        }
        return gankApi;
    }

    public static DoubanApi getDoubanApi() {
        if (doubanApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.douban.com/")
                    //由于需要转为Observable 需要添加RxJavaCallAdapterFactory
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .addConverterFactory(gsonConverterFactory)
                    .build();
            doubanApi = retrofit.create(DoubanApi.class);
        }
        return doubanApi;
    }

    public static void getMovieTop(int start,int count) {
        DoubanApi movie = mRetrofit.create(DoubanApi.class);

        Call<MovieModel> movieTop = movie.getMoiveTop(start + "", count + "");
        movieTop.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                Logger.d(response.body());
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {
                Logger.d(t.getMessage());
            }
        });

    }

    public static void getMovieTopWithRxJava(int start,int count) {
        DoubanApi movie = mRetrofitRxJava.create(DoubanApi.class);

        movie.getMoiveTopWithRxJava(start, count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MovieModel>() {
                    @Override
                    public void onCompleted() {
                        Logger.d("获取豆瓣电影完成");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(e.getMessage());
                    }

                    @Override
                    public void onNext(MovieModel movieModel) {
                        Logger.d(movieModel.toString());
                    }
                });

    }

}
