package com.benboer.boluo.boluomessage;


import com.benboer.boluo.componentbase.ServiceFactory;
import com.benboer.boluo.core.Application;
import com.benboer.boluo.core.app.BoLuo;

import com.benboer.boluo.message.PushIntentService;
import com.benboer.boluo.message.service.AccountService;
import com.benboer.boluo.message.service.BottomFragmentService;
import com.benboer.boluo.module_common.Factory;
import com.benboer.boluo.module_common.persistence.Account;
import com.igexin.sdk.PushManager;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

/**
 * Created by BenBoerBoluojiushiwo on 2019/3/28.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        BoLuo.init(this)
                .withIcon(new FontAwesomeModule())
                .withApiHost("http://172.20.10.2:6000/Gradle___boluo___boluo_1_0_SNAPSHOT_war/api/")
                .configure();

        // 调用Factory进行初始化
        Factory.setup();
        // 持久化的数据进行初始化
        Account.load();//TODO 这个不应该在这里初始化
        // 推送进行初始化
        PushManager.getInstance().initialize(getApplicationContext(),null);
        PushManager.getInstance().registerPushIntentService(getApplicationContext(), PushIntentService.class);
        ServiceFactory.getInstance().setAccountService(new AccountService());
        ServiceFactory.getInstance().setFragmentService(new BottomFragmentService());

    }

}
