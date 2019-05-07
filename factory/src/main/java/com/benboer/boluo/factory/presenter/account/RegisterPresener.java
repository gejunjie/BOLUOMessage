package com.benboer.boluo.factory.presenter.account;

import com.benboer.boluo.factory.data.DataSource;
import com.benboer.boluo.factory.model.db.User;
import com.benboer.boluo.factory.presenter.BasePresenter;
/**
 * Created by BenBoerBoluojiushiwo on 2019/5/7.
 */
public class RegisterPresener extends BasePresenter<RegisterContract.View>
        implements RegisterContract.Presenter, DataSource.Callback<User> {

    public RegisterPresener(RegisterContract.View view) {
        super(view);
    }

    @Override
    public void onDataLoaded(User user) {

    }

    @Override
    public void onDataNotAvailable(int strRes) {

    }

    @Override
    public void register(String phone, String name, String password) {

    }

    @Override
    public boolean checkMobile(String phone) {
        return false;
    }
}
