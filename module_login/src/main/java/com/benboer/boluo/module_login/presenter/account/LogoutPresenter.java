package com.benboer.boluo.module_login.presenter.account;

import com.benboer.boluo.module_common.base.mvp.data.DataSource;
import com.benboer.boluo.module_common.base.model.Author;
import com.benboer.boluo.module_common.base.mvp.presenter.BasePresenter;
import com.benboer.boluo.module_login.helper.AccountHelper;

/**
 * Created by BenBoerBoluojiushiwo on 2019/6/21.
 */
public class LogoutPresenter extends BasePresenter<LogoutContract.View>
        implements LogoutContract.Presenter, DataSource.Callback<Author> {

    public LogoutPresenter(LogoutContract.View view) {
        super(view);
    }

    @Override
    public void logout() {
        start();
        AccountHelper.logout();
    }

    @Override
    public void onDataLoaded(Author user) {
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
