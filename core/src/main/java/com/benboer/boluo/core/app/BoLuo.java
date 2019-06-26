package com.benboer.boluo.core.app;

import android.content.Context;
import android.os.Handler;

/**
 * Created by BenBoerBoluojiushiwo on 2019/6/26.
 */
public class BoLuo {

    public static Configurator init(Context context) {
        Configurator.getInstance()
                .getConfigs()
                .put(Configurator.ConfigKeys.APPLICATION_CONTEXT, context.getApplicationContext());

        return Configurator.getInstance();
    }

    public static Configurator getConfigurator() {
        return Configurator.getInstance();
    }

    //通get方法过key获取全局HashMap中的对象
    public static <T> T getConfiguration(Object key) {
        return getConfigurator().getConfiguration(key);
    }

    public static Handler getHandler() {
        return getConfiguration(Configurator.ConfigKeys.HANDLER);
    }

    public static Context getApplicationContext() {
        return (Context) getConfiguration(Configurator.ConfigKeys.APPLICATION_CONTEXT);
    }
}