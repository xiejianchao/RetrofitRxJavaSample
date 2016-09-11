package com.github.sample.retrofitrxjavasample.api;

import com.github.sample.retrofitrxjavasample.model.MovieModel;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by xiejc on 16/9/9.
 *
 */
public interface DoubanApi {

//    https://api.douban.com/v2/movie/top250?start=0&count=10

    @POST("v2/movie/top250")
    Call<MovieModel> getMoiveTop (
            @Query("start") String start,
            @Query("count") String count
    );

    @POST("v2/movie/top250")
    Observable<MovieModel> getMoiveTopWithRxJava (
            @Query("start") int start,
            @Query("count") int count
    );



}
