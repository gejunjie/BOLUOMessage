package com.benboer.boluo.module_login.presenter.account;

import com.benboer.boluo.module_common.base.mvp.presenter.BaseContract;

/**
 * Created by BenBoerBoluojiushiwo on 2019/6/21.
 */
public interface LogoutContract {

    interface View extends BaseContract.View<LogoutContract.Presenter>{

        void logoutSuccess();
    }

    interface Presenter extends BaseContract.Presenter{

        void logout();
    }
}
