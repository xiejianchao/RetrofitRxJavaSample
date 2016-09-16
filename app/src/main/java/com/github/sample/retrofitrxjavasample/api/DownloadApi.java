package com.github.sample.retrofitrxjavasample.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by ljd on 3/29/16.
 */
public interface DownloadApi {

    @GET("/mobilesafe/shouji360/360safesis/360MobileSafe_6.2.3.1060.apk")
    Call<ResponseBody> retrofitDownload();

    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);

}
