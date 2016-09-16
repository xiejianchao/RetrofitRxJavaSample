package com.github.sample.retrofitrxjavasample.utils;

import android.os.Looper;

/**
 * Created by xiejianchao on 16/9/15.
 */
public class Util {

    public static boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

}
