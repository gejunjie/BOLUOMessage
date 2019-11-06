package com.benboer.boluo.main.fragment.account;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.benboer.boluo.common.app.AccountManager;
import com.benboer.boluo.common.app.BoLuo;
import com.benboer.boluo.common.app.ConfigKeys;
import com.benboer.boluo.common.base.fragment.SupportFragment;
import com.benboer.boluo.common.mvp.PresenterFragment;
import com.benboer.boluo.main.fragment.bottom.BottomFragment;
import com.benboer.boluo.main.serviceImpl.Account;
import com.benboer.boluo.main.serviceImpl.AccountServiceImpl;
import com.benboer.boluo.main.R;
import com.benboer.boluo.main.presenter.account.LoginContract;
import com.benboer.boluo.main.presenter.account.LoginPresenter;

import net.qiujuer.genius.ui.widget.Loading;

import java.util.HashMap;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/7.
 *
 * 用户登录
 */
public class LoginFragment extends PresenterFragment<LoginContract.Presenter>
        implements LoginContract.View{

    private EditText mPhone;
    private EditText mPassword;
    private Loading mLoading;
    private Button mSubmit;

    @Override
    public Object setLayout() {
        return R.layout.fragment_login;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View root) {
        mPhone = bind(R.id.edit_phone);
        mPassword = bind(R.id.edit_password);
        mLoading = bind(R.id.loading);
        mSubmit = bind(R.id.btn_submit);
        bind(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = mPhone.getText().toString();
                String passWord = mPassword.getText().toString();
                mPresenter.login(phone, passWord);
            }
        });
        bind(R.id.txt_go_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportDelegate().start(new RegisterFragment());
            }
        });
        mPhone.setText( Account.getAccount());
    }

    @Override
    protected LoginContract.Presenter initPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public void showError(int str) {
        super.showError(str);
        mLoading.stop();
        mPhone.setEnabled(true);
        mPassword.setEnabled(true);
        mSubmit.setEnabled(true);
    }

    @Override
    public void showLoading() {
        super.showLoading();
        mLoading.start();
        mPhone.setEnabled(false);
        mPassword.setEnabled(false);
        mSubmit.setEnabled(false);
    }

    @Override
    public void loginSuccess() {
        AccountManager.setSignState(true);
//        getSupportDelegate().startWithPop((SupportFragment) ARouter.getInstance()
//                .build("/main/bottomFragmentt").navigation());
        getSupportDelegate().startWithPop(new BottomFragment());
    }

}
