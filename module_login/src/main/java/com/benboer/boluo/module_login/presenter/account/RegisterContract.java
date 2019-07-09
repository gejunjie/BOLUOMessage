package com.benboer.boluo.module_login.presenter.account;

import com.benboer.boluo.module_common.base.presenter.BaseContract;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/7.
 */
public interface RegisterContract {

    interface View extends BaseContract.View<Presenter>{
        // 注册成功
        void registerSuccess();
    }

    interface Presenter extends BaseContract.Presenter{
        // 发起一个注册
        void register(String phone, String name, String password);

        // 检查手机号是否正确
        boolean checkMobile(String phone);
    }
}
