package com.github.sample.retrofitrxjavasample.net;

import android.util.Log;

import com.github.sample.retrofitrxjavasample.model.ProgressBean;
import com.github.sample.retrofitrxjavasample.net.download.ProgressResponseBody;
import com.github.sample.retrofitrxjavasample.net.download.ProgressResponseListener;
import com.github.sample.retrofitrxjavasample.net.progress.DownloadProgressHandler;
import com.github.sample.retrofitrxjavasample.net.progress.ProgressHandler;
import com.github.sample.retrofitrxjavasample.net.upload.ProgressRequestBody;
import com.github.sample.retrofitrxjavasample.net.upload.ProgressRequestListener;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Cmad on 2016/4/28.
 */
public class HttpClientHelper2 {

    private static final int DEFAULT_TIMEOUT = 15;
    private static ProgressBean progressBean = new ProgressBean();
    private static DownloadProgressHandler mProgressHandler;

    /**
     * 包装OkHttpClient，用于下载文件的回调
     * @return 包装后的OkHttpClient
     */
    public static OkHttpClient addProgressResponseListener(){

        //该方法在子线程中运行
        final ProgressResponseListener progressListener = new ProgressResponseListener() {

            @Override
            public void onResponseProgress(long bytesRead, long contentLength, boolean done) {
                Log.d("progress:",String.format("%d%% done\n",(100 * bytesRead) / contentLength));
                if (mProgressHandler == null){
                    return;
                }

                progressBean.setBytesRead(bytesRead);
                progressBean.setContentLength(contentLength);
                progressBean.setDone(done);
                mProgressHandler.sendMessage(progressBean);
            }
        };

        OkHttpClient.Builder client = new OkHttpClient.Builder();

        client.retryOnConnectionFailure(true)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                //增加拦截器
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        //拦截
                        Response originalResponse = chain.proceed(chain.request());

                        //包装响应体并返回
                        return originalResponse.newBuilder()
                                .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                                .build();
                    }
                });
        return client.build();
    }


    /**
     * 包装OkHttpClient，用于上传文件的回调
     * @param progressListener 进度回调接口
     * @return 包装后的OkHttpClient
     */
    public static OkHttpClient addProgressRequestListener(final ProgressRequestListener progressListener){
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        //增加拦截器
        client.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .method(original.method(), new ProgressRequestBody(original.body(),progressListener))
                        .build();
                return chain.proceed(request);
            }
        });
        return client.build();
    }

    public static void setProgressHandler(DownloadProgressHandler progressHandler){
        mProgressHandler = progressHandler;
    }

}