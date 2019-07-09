package com.benboer.boluo.module_login.presenter.account;

import android.text.TextUtils;

import com.benboer.boluo.module_common.base.data.DataSource;
import com.benboer.boluo.module_common.base.presenter.BasePresenter;
import com.benboer.boluo.module_common.persistence.Account;
import com.benboer.boluo.module_login.R;
import com.benboer.boluo.module_login.helper.AccountHelper;
import com.benboer.boluo.module_login.model.RegisterModel;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.regex.Pattern;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/7.
 */
public class RegisterPresenter extends BasePresenter<RegisterContract.View>
        implements RegisterContract.Presenter, DataSource.Callback {

    // 手机号的正则,11位手机号
    String REGEX_MOBILE = "[1][3,4,5,7,8][0-9]{9}$";

    public RegisterPresenter(RegisterContract.View view) {
        super(view);
    }

    @Override
    public void onDataLoaded(Object o) {
        final RegisterContract.View view = getView();
        if (view == null) return;
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.registerSuccess();
            }
        });
    }

    @Override
    public void onDataNotAvailable(final int strRes) {
        final RegisterContract.View view = getView();
        if (view == null) return;
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.showError(strRes);
            }
        });
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
            RegisterModel model = new RegisterModel(phone, password, name, Account.getPushId());
            AccountHelper.register(model, this);
        }
    }

    @Override
    public boolean checkMobile(String phone) {
        // 手机号不为空，并且满足格式
        return !TextUtils.isEmpty(phone)
                && Pattern.matches(REGEX_MOBILE, phone);
    }

}
