package com.github.sample.retrofitrxjavasample.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by JokAr on 16/7/5.
 */
public interface DownloadService {


    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);

    @Streaming
    @GET
    Call<ResponseBody> download2(@Url String url);
}