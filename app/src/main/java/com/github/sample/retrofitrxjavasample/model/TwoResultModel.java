package com.github.sample.retrofitrxjavasample.model;

/**
 * Created by xiejianchao on 16/9/12.
 */
public class TwoResultModel<T,T2> {

    private T t1;
    private T2 t2;

    public TwoResultModel() {
    }

    public TwoResultModel(T t1, T2 t2) {
        this.t1 = t1;
        this.t2 = t2;
    }

    public T getT1() {
        return t1;
    }

    public void setT1(T t1) {
        this.t1 = t1;
    }

    public T2 getT2() {
        return t2;
    }

    public void setT2(T2 t2) {
        this.t2 = t2;
    }

    @Override
    public String toString() {
        return "TwoResultModel{" +
                "t1=" + t1 +
                ", t2=" + t2 +
                '}';
    }
}
