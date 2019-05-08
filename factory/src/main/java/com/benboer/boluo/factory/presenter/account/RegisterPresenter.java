package com.benboer.boluo.factory.presenter.account;

import android.text.TextUtils;

import com.benboer.boluo.common.Common;
import com.benboer.boluo.factory.R;
import com.benboer.boluo.factory.data.DataSource;
import com.benboer.boluo.factory.data.helper.AccountHelper;
import com.benboer.boluo.factory.model.api.account.RegisterModel;
import com.benboer.boluo.factory.model.db.User;
import com.benboer.boluo.factory.persistence.Account;
import com.benboer.boluo.factory.presenter.BasePresenter;

import java.util.regex.Pattern;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/7.
 */
public class RegisterPresenter extends BasePresenter<RegisterContract.View>
        implements RegisterContract.Presenter, DataSource.Callback<User> {

    public RegisterPresenter(RegisterContract.View view) {
        super(view);
    }

    @Override
    public void onDataLoaded(User user) {
        final RegisterContract.View view = getView();
        if (view == null) return;

    }

    @Override
    public void onDataNotAvailable(int strRes) {
        final RegisterContract.View view = getView();
        if (view == null) return;

    }

    @Override
    public void register(String phone, String name, String password) {
        start();
        final RegisterContract.View view = getView();
        // 校验
        if (!checkMobile(phone)) {
            // 提示
            view.showError(R.string.data_account_register_invalid_parameter_mobile);
        } else if (name.length() < 2) {
            // 姓名需要大于2位
            view.showError(R.string.data_account_register_invalid_parameter_name);
        } else if (password.length() < 6) {
            // 密码需要大于6位
            view.showError(R.string.data_account_register_invalid_parameter_password);
        } else {
            // 进行网络请求
            // 构造Model，进行请求调用
            RegisterModel model = new RegisterModel(phone, password, name, Account.getPushId());
            // 进行网络请求，并设置回送接口为自己
            AccountHelper.register(model, this);
        }
    }

    @Override
    public boolean checkMobile(String phone) {
        // 手机号不为空，并且满足格式
        return !TextUtils.isEmpty(phone)
                && Pattern.matches(Common.Constance.REGEX_MOBILE, phone);
    }
}
