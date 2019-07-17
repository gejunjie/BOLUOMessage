package com.benboer.boluo.module_common;

import com.benboer.boluo.core.app.BoLuo;
import com.benboer.boluo.module_common.utils.DBFlowExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by BenBoerBoluojiushiwo on 2019/4/1.
 */
public class Factory {

    private static final Factory instance;

    private static Executor executor;

    // 全局的Gson
    private final Gson gson;

    static {
         instance = new Factory();
    }


    private Factory(){
        executor = Executors.newFixedThreadPool(4);

        gson = new GsonBuilder()
                // 设置时间格式
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
                // 设置一个过滤器，数据库级别的Model不进行Json转换
                .setExclusionStrategies(new DBFlowExclusionStrategy())
                .create();
    }

    /**
     * Factory 中的初始化
     */
    public static void setup() {
        // 初始化数据库
        FlowManager.init(new FlowConfig.Builder(BoLuo.getApplicationContext())
                .openDatabasesOnInit(true) // 数据库初始化的时候就开始打开
                .build());

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

    /**
     * 收到账户退出的消息需要进行账户退出重新登录
     */
    private void logout() {

    }

}
