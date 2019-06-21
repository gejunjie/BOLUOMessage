package com.benboer.boluo.factory.presenter.account;

import com.benboer.boluo.factory.data.DataSource;
import com.benboer.boluo.factory.data.helper.AccountHelper;
import com.benboer.boluo.factory.model.db.User;
import com.benboer.boluo.factory.presenter.BasePresenter;

/**
 * Created by BenBoerBoluojiushiwo on 2019/6/21.
 */
public class LogoutPresenter extends BasePresenter<LogoutContract.View>
        implements LogoutContract.Presenter, DataSource.Callback<User> {

    public LogoutPresenter(LogoutContract.View view) {
        super(view);
    }

    @Override
    public void logout() {
        start();
        AccountHelper.logout(this);
    }

    @Override
    public void onDataLoaded(User user) {
        LogoutContract.View view = getView();
        if (view != null){
            view.logoutSuccess();
        }
    }

    @Override
    public void onDataNotAvailable(int strRes) {
        LogoutContract.View view = getView();
        if (view != null){
            view.logoutSuccess();
        }
    }
}
