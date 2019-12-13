package com.benboer.boluo.main.serviceImpl;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.benboer.boluo.common.service.AccountService;


/**
 * Created by BenBoerBoluojiushiwo on 2019/5/5.
 */
@Route(path = "/main/account_service")
public class AccountServiceImpl implements AccountService {

    @Override
    public void init(Context context) {

    }

    @Override
    public boolean isLogin() {
        return Account.isLogin();
    }

    @Override
    public boolean isComplete() {
//        return Account.isComplete();
        return false;
    }

    @Override
    public void setPushId(String pushId) {
        Account.setPushId( pushId );
    }

    @Override
    public String getToken() {
        return Account.getToken();
    }

    @Override
    public String getUserId() {
        return Account.getUserId();
    }
}
