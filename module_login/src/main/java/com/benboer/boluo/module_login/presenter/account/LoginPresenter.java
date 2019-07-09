package com.benboer.boluo.module_login.presenter.account;

import android.text.TextUtils;

import com.benboer.boluo.module_common.base.data.DataSource;
import com.benboer.boluo.module_common.base.model.Author;
import com.benboer.boluo.module_common.base.presenter.BasePresenter;
import com.benboer.boluo.module_common.persistence.Account;
import com.benboer.boluo.module_login.helper.AccountHelper;
import com.benboer.boluo.module_login.model.LoginModel;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/6.
 */
public class LoginPresenter extends BasePresenter<LoginContract.View>
    implements LoginContract.Presenter, DataSource.Callback<Author> {

    public LoginPresenter(LoginContract.View view) {
        super(view);
    }

    //成功
    @Override
    public void onDataLoaded(Author user) {
        final LoginContract.View view = getView();
        if (view == null) return;

        view.loginSuccess();
    }

    //失败
    @Override
    public void onDataNotAvailable(int strRes) {
        final LoginContract.View view = getView();
        if (view == null) return;

        view.showError(strRes);
    }

    @Override
    public void login(String phone, String password) {
        start();
        final LoginContract.View view = getView();

        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)){
//            view.showError(R.string.data_account_login_invalid_parameter);
        }else{
            LoginModel loginModel = new LoginModel(phone, password, Account.getPushId());
            AccountHelper.login(loginModel, this);
        }
    }
}
