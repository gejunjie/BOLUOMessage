package com.benboer.boluo.boluomessage;


import com.benboer.boluo.core.Application;
import com.benboer.boluo.core.app.BoLuo;

import com.benboer.boluo.message.Factory;
import com.igexin.sdk.PushManager;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import static com.benboer.boluo.core.app.ConfigKeys.API_HOST;

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
        // 推送进行初始化
        PushManager.getInstance().initialize(getApplicationContext(),null);
        PushManager.getInstance().registerPushIntentService(getApplicationContext(), PushIntentService.class);


    }

}
