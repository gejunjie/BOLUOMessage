package com.benboer.boluo.factory.presenter.account;

import android.text.TextUtils;

import com.benboer.boluo.factory.R;
import com.benboer.boluo.factory.data.DataSource;
import com.benboer.boluo.factory.data.helper.AccountHelper;
import com.benboer.boluo.factory.model.api.account.LoginModel;
import com.benboer.boluo.factory.model.db.User;
import com.benboer.boluo.factory.persistence.Account;
import com.benboer.boluo.factory.presenter.BasePresenter;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/6.
 */
public class LoginPresenter extends BasePresenter<LoginContract.View>
    implements LoginContract.Presenter, DataSource.Callback<User> {

    public LoginPresenter(LoginContract.View view) {
        super(view);
    }

    //成功
    @Override
    public void onDataLoaded(User user) {

    }

    //失败
    @Override
    public void onDataNotAvailable(int strRes) {

    }

    @Override
    public void login(String phone, String password) {
        start();
        final LoginContract.View view = getView();

        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)){
            view.showError(R.string.data_account_login_invalid_parameter);
        }else{
            LoginModel loginModel = new LoginModel(phone, password, Account.getPushId());
            AccountHelper.login(loginModel, this);
        }
    }
}
