package com.benboer.boluo.factory.presenter.account;

import com.benboer.boluo.factory.presenter.BaseContract;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/6.
 */
public interface LoginContract {

    interface View extends BaseContract.View<Presenter>{

        void loginSuccess();
    }

    interface Presenter extends BaseContract.Presenter{

        void login(String phone, String password);
    }
}
