package com.benboer.boluo.main.Launchtasks;

import com.benboer.boluo.common.util.launchstarter.MainTask;
import com.benboer.boluo.common.util.launchstarter.TaskDispatcher;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by BenBoerBoluojiushiwo on 2019/8/26.
 */
public class InitDbFlowTask extends MainTask {

    @Override
    public void run() {
        FlowManager.init(new FlowConfig.Builder(TaskDispatcher.getContext())
                .openDatabasesOnInit(true) // 数据库初始化的时候就开始打开
                .build());
    }
}
