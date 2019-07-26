package com.benboer.boluo.common.util;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by BenBoerBoluojiushiwo on 2019/7/26.
 */
public class HandlerUtil {

    public static final Handler sHandler = new Handler(Looper.getMainLooper());

    /**
     * 如果当前对象中没有activity的引用，
     * 这个方法可以将任务投递到ui线程执行
     */
    public static void runOnUiThread(Runnable runnable){
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run();
        }else {
            sHandler.post(runnable);
        }
    }
}
