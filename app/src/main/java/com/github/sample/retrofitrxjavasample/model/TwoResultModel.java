package com.github.sample.retrofitrxjavasample.model;

/**
 * Created by xiejianchao on 16/9/12.
 */
public class TwoResultModel<T,T2> {

    private T gankBeauty;
    private T2 movieModel;

    public TwoResultModel() {
    }

    public TwoResultModel(T gankBeauty, T2 movieModel) {
        this.gankBeauty = gankBeauty;
        this.movieModel = movieModel;
    }

    public T getGankBeauty() {
        return gankBeauty;
    }

    public void setGankBeauty(T gankBeauty) {
        this.gankBeauty = gankBeauty;
    }

    public T2 getMovieModel() {
        return movieModel;
    }

    public void setMovieModel(T2 movieModel) {
        this.movieModel = movieModel;
    }

    @Override
    public String toString() {
        return "TwoResultModel{" +
                "gankBeauty=" + gankBeauty +
                ", movieModel=" + movieModel +
                '}';
    }
}
