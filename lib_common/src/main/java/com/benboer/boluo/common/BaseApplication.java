package com.benboer.boluo.common;

import android.app.Application;

/**
 * Created by BenBoerBoluojiushiwo on 2019/7/25.
 */
public class BaseApplication extends Application {
    private static BaseApplication mApplication;
    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        if (BuildConfig.IS_DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
//            ARouter.openLog();     // 打印日志
//            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
//        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }
    public static BaseApplication getInstance(){
        return mApplication;
    }
}
