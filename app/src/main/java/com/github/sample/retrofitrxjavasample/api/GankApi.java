package com.github.sample.retrofitrxjavasample.api;

import com.github.sample.retrofitrxjavasample.model.GankBeauty;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface GankApi {
    @GET("data/福利/{number}/{page}")
    Observable<GankBeauty> getBeauties(
            @Path("number") int number,
            @Path("page") int page);
}