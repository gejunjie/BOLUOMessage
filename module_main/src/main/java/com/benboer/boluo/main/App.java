package com.benboer.boluo.main;


import android.content.Context;
import android.util.Log;

import androidx.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.benboer.boluo.common.BaseApplication;
import com.benboer.boluo.common.app.BoLuo;
import com.benboer.boluo.common.icon.FontBoluoModule;
import com.benboer.boluo.common.net.interceptors.TokenInterceptor;
import com.benboer.boluo.common.util.launchstarter.TaskDispatcher;
import com.benboer.boluo.main.Launchtasks.InitAccountTask;
import com.benboer.boluo.main.Launchtasks.InitBoluoTask;
import com.benboer.boluo.main.Launchtasks.InitDbFlowTask;
import com.benboer.boluo.main.Launchtasks.InitPushManagerTask;

import com.benboer.boluo.message.PushIntentService;
import com.igexin.sdk.PushManager;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by BenBoerBoluojiushiwo on 2019/3/28.
 */
public class App extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        long a0 = System.currentTimeMillis();
        //ARouter初始化
        ARouter.openLog();     // 打印日志
        ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        ARouter.init(this);
        TaskDispatcher.init(this);
        TaskDispatcher dispatcher = TaskDispatcher.createInstance();
        dispatcher
                .addTask(new InitAccountTask())
                .addTask(new InitBoluoTask())
                .addTask(new InitDbFlowTask())
                .addTask(new InitPushManagerTask())
                .start();
//        dispatcher.await();
        Log.e("---------------------->", String.valueOf(System.currentTimeMillis() - a0));
//        long a0 = System.currentTimeMillis();
        initBoluo();
//        Log.e("---------------------->", String.valueOf(System.currentTimeMillis() - a0));
//        long a1 = System.currentTimeMillis();
//        initDBFlow();
//        Log.e("---------------------->", String.valueOf(System.currentTimeMillis() - a1));
//        long a2 = System.currentTimeMillis();
//        initPushManager();
//        Log.e("---------------------->", String.valueOf(System.currentTimeMillis() - a2));
//        long a3 = System.currentTimeMillis();
//        initAccount();
//        Log.e("---------------------->", String.valueOf(System.currentTimeMillis() - a3));
//        Log.e("---------------------->", String.valueOf(System.currentTimeMillis() - a0));
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private void initDBFlow() {
        // 初始化数据库
        FlowManager.init(new FlowConfig.Builder(BoLuo.getApplicationContext())
                .openDatabasesOnInit(true) // 数据库初始化的时候就开始打开
                .build());
    }

    private void initBoluo() {
        BoLuo.initConfig(this)
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontBoluoModule())
                .withApiHost("http://172.20.10.2:6000/Gradle___boluo___boluo_1_0_SNAPSHOT_war/api/")
//                .withApiHost("http://192.168.31.210:6000/Gradle___boluo___boluo_1_0_SNAPSHOT_war/api/")
//                .withFragmentService(initService())
                .withInterceptor(new TokenInterceptor())
                .configure();
    }

    // 推送进行初始化
    private void initPushManager() {
        PushManager.getInstance().initialize(getApplicationContext(),null);
        PushManager.getInstance().registerPushIntentService(getApplicationContext(), PushIntentService.class);
    }

}
