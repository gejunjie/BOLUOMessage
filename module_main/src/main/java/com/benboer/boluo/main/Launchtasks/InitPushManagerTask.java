package com.benboer.boluo.main.Launchtasks;

import com.benboer.boluo.common.app.BoLuo;
import com.benboer.boluo.common.util.launchstarter.Task;
import com.benboer.boluo.message.PushIntentService;
import com.igexin.sdk.PushManager;

/**
 * Created by BenBoerBoluojiushiwo on 2019/8/26.
 *
 * 推送模块初始化
 */
public class InitPushManagerTask extends Task {
    @Override
    public void run() {
        PushManager.getInstance().initialize(BoLuo.getApplicationContext(), null);
        PushManager.getInstance().registerPushIntentService(BoLuo.getApplicationContext(), PushIntentService.class);
    }
}
