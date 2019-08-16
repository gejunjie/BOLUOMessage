package com.benboer.boluo.boluomessage;


import android.content.Context;
import android.util.ArrayMap;

import androidx.multidex.MultiDex;

import com.benboer.boluo.common.BaseApplication;
import com.benboer.boluo.common.app.BoLuo;
import com.benboer.boluo.common.icon.FontBoluoModule;
import com.benboer.boluo.common.net.interceptors.TokenInterceptor;
import com.benboer.boluo.common.persistence.Account;
import com.benboer.boluo.componentbase.service.IAccountService;
import com.benboer.boluo.componentbase.service.IBottomFragmentService;
import com.benboer.boluo.componentbase.service.IMessageModuleFragmentService;
import com.benboer.boluo.componentbase.service.IPersonalFragmentService;
import com.benboer.boluo.main.service.BottomFragmentService;
import com.benboer.boluo.main.service.PersonalFragmentService;
import com.benboer.boluo.message.PushIntentService;
import com.benboer.boluo.message.service.AccountService;
import com.benboer.boluo.message.service.MessageModuleFragmentService;
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
        initBoluo();
        initDBFlow();
        initPushManager();
        initAccount();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private ArrayMap initService(){
        ArrayMap map = new ArrayMap();
        map.put(IAccountService.class, new AccountService());
        map.put(IBottomFragmentService.class, new BottomFragmentService());
        map.put(IPersonalFragmentService.class, new PersonalFragmentService());
        map.put(IMessageModuleFragmentService.class, new MessageModuleFragmentService());
        return map;
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
                .withFragmentService(initService())
                .withInterceptor(new TokenInterceptor())
                .configure();
    }

    // 推送进行初始化
    private void initPushManager() {
        PushManager.getInstance().initialize(getApplicationContext(),null);
        PushManager.getInstance().registerPushIntentService(getApplicationContext(), PushIntentService.class);
    }


    private void initAccount() {
        Account.load();
    }


}
