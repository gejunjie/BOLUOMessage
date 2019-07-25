package com.benboer.boluo.common;

import android.os.SystemClock;

import java.io.File;

/**
 * Created by BenBoerBoluojiushiwo on 2019/4/1.
 */
public class Application extends android.app.Application {

    private static Application instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    /**
     * 外部获取单例
     *
     * @return Application
     */
    public static Application getInstance() {
        return instance;
    }

}
