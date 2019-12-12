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
        //ARouter初始化
        ARouter.openLog();     // 打印日志
        ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        ARouter.init(this);
        TaskDispatcher.init(this);
        TaskDispatcher dispatcher = TaskDispatcher.createInstance();
        dispatcher
                .addTask(new InitBoluoTask())
                .addTask(new InitDbFlowTask())
                .addTask(new InitAccountTask())
//                .addTask(new InitPushManagerTask())
                .start();

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

//    private void initDBFlow() {
//        // 初始化数据库
//        FlowManager.init(new FlowConfig.Builder(BoLuo.getApplicationContext())
//                .openDatabasesOnInit(true) // 数据库初始化的时候就开始打开
//                .build());
//    }
//
//    private void initBoluo() {
//        BoLuo.initConfig(this)
//                .withIcon(new FontAwesomeModule())
//                .withIcon(new FontBoluoModule())
//                .withApiHost("http://172.20.10.2:6000/Gradle___boluo___boluo_1_0_SNAPSHOT_war/api/")
////                .withApiHost("http://192.168.31.210:6000/Gradle___boluo___boluo_1_0_SNAPSHOT_war/api/")
//                .withInterceptor(new TokenInterceptor())
//                .configure();
//    }
}
