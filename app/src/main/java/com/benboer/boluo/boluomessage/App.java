package com.benboer.boluo.boluomessage;


import com.benboer.boluo.common.app.Application;
import com.benboer.boluo.factory.Factory;
import com.igexin.sdk.PushManager;

/**
 * Created by BenBoerBoluojiushiwo on 2019/3/28.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // 调用Factory进行初始化
        Factory.setup();
        // 推送进行初始化
        PushManager.getInstance().initialize(this);
    }
}
