package com.benboer.boluo.main.Launchtasks;

import com.benboer.boluo.common.app.BoLuo;
import com.benboer.boluo.common.icon.FontBoluoModule;
import com.benboer.boluo.common.net.interceptors.TokenInterceptor;
import com.benboer.boluo.common.util.launchstarter.Task;
import com.benboer.boluo.common.util.launchstarter.TaskDispatcher;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

/**
 * Created by BenBoerBoluojiushiwo on 2019/8/26.
 */
public class InitBoluoTask extends Task {

    @Override
    public void run() {
        BoLuo.initConfig(TaskDispatcher.getContext())
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontBoluoModule())
                .withApiHost("http://172.20.10.2:6000/Gradle___boluo___boluo_1_0_SNAPSHOT_war/api/")
                .withInterceptor(new TokenInterceptor())
                .configure();
    }
}
