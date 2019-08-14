package com.benboer.boluo.common.app;

import android.content.Context;
import android.os.Handler;

import com.benboer.boluo.common.util.db.DBFlowExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by BenBoerBoluojiushiwo on 2019/6/26.
 */
public class BoLuo {

    private static final BoLuo instance;

    private static final Executor executor;
    // 全局的Gson
    private static final Gson gson;

    static {
        instance = new BoLuo();

        executor = Executors.newFixedThreadPool(4);

        gson = new GsonBuilder()
                // 设置时间格式
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
                // 设置一个过滤器，数据库级别的Model不进行Json转换
                .setExclusionStrategies(new DBFlowExclusionStrategy())
                .create();
    }

    private BoLuo(){

    }

    public static Configurator initConfig(Context context) {
        Configurator.getInstance()
                .getConfigs()
                .put(ConfigKeys.APPLICATION_CONTEXT, context.getApplicationContext());

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
        return getConfiguration(ConfigKeys.HANDLER);
    }

    public static Context getApplicationContext() {
        return (Context) getConfiguration(ConfigKeys.APPLICATION_CONTEXT);
    }

    public static void runOnAsync(Runnable runnable){
        executor.execute(runnable);
    }

    /**
     * 返回一个全局的Gson
     *
     * @return Gson
     */
    public static Gson getGson() {
        return instance.gson;
    }

}
