package com.benboer.boluo.main.Launchtasks;

import com.benboer.boluo.main.serviceImpl.Account;
import com.benboer.boluo.main.serviceImpl.AccountServiceImpl;
import com.benboer.boluo.common.util.launchstarter.MainTask;

/**
 * Created by BenBoerBoluojiushiwo on 2019/8/26.
 */
public class InitAccountTask extends MainTask {

    @Override
    public void run() {
        Account.load();
    }
}
