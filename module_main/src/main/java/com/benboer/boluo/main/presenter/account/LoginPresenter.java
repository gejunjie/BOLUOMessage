package com.benboer.boluo.main.presenter.account;

import android.text.TextUtils;

import com.benboer.boluo.common.mvp.data.DataSource;
import com.benboer.boluo.common.mvp.presenter.BasePresenter;
import com.benboer.boluo.main.R;
import com.benboer.boluo.main.helper.AccountHelper;
import com.benboer.boluo.main.model.LoginModel;
import com.benboer.boluo.main.serviceImpl.Account;
import com.benboer.boluo.message.db.User;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/6.
 */
public class LoginPresenter extends BasePresenter<LoginContract.View>
    implements LoginContract.Presenter, DataSource.Callback<User> {

    public LoginPresenter(LoginContract.View view) {
        super(view);
    }

    final LoginContract.View view = getView();

    //成功
    @Override
    public void onDataLoaded(User user) {
        if (view != null) {
            view.loginSuccess();
        }
    }

    //失败
    @Override
    public void onDataNotAvailable(int strRes) {
        if (view != null){
            view.showError(strRes);
        }
    }

    @Override
    public void login(String phone, String password) {
        start();
        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)){
            view.showError(R.string.data_account_login_invalid_parameter);
        }else{
            LoginModel loginModel = new LoginModel(phone, password, Account.getPushId());
            AccountHelper.login(loginModel, this);
        }
    }
}
