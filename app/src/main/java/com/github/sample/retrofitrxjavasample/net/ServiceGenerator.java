package com.github.sample.retrofitrxjavasample.net;

import com.github.sample.retrofitrxjavasample.api.DownloadService;
import com.github.sample.retrofitrxjavasample.net.download.ProgressResponseListener;
import com.github.sample.retrofitrxjavasample.net.upload.ProgressRequestListener;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;

/**
 * Created by Cmad on 2016/4/28.
 */
public class ServiceGenerator {
//    private static final String HOST = "http://www.xxx.com/ ";
//    http://gdown.baidu.com/data/wisegame/20eb4c5985bfa304/weixin_861.apk

    private static final String HOST = "http://gdown.baidu.com/";


    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(HOST)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create());


    public static Retrofit getRetrofit(ProgressResponseListener listener) {
        Retrofit build = builder
                .baseUrl(HOST)
                .client(HttpClientHelper.addProgressResponseListener(listener))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return build;
    }


    public void download(String url, File savePathFile, Subscriber subscriber) {

    }

    public static <T> T createService(Class<T> tClass){
        return builder.build().create(tClass);
    }


    /**
     * 创建带响应进度(下载进度)回调的service
     */
    public static <T> T createResponseService(Class<T> tClass, ProgressResponseListener listener){
        return builder
                .client(HttpClientHelper.addProgressResponseListener(listener))
                .build()
                .create(tClass);
    }


    /**
     * 创建带请求体进度(上传进度)回调的service
     */
    public static <T> T createReqeustService(Class<T> tClass, ProgressRequestListener listener){
        return builder
                .client(HttpClientHelper.addProgressRequestListener(listener))
                .build()
                .create(tClass);
    }

}