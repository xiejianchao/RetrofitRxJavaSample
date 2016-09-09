package com.github.sample.retrofitrxjavasample;

import android.app.Application;

import com.github.sample.retrofitrxjavasample.config.Config;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Settings;

/**
 * Created by xiejc on 16/9/9.
 */
public class MyApplication extends Application {

    private static final String TAG = MyApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        Settings logSetting = com.orhanobut.logger.Logger.init("Retrofit_APP");// default tag : PRETTYLOGGER
        logSetting.methodCount(3);// default 2
//        logSetting.hideThreadInfo();// default it is shown
        logSetting.logLevel(Config.IS_DEBUG ? LogLevel.FULL : LogLevel.NONE);//default : LogLevel.FULL,Release set as a LogLevel.NONE

    }
}
