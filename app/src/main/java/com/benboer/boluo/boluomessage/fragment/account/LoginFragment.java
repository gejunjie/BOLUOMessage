package com.benboer.boluo.boluomessage.fragment.account;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.benboer.boluo.boluomessage.R;
import com.benboer.boluo.boluomessage.activity.MainActivity;
import com.benboer.boluo.boluomessage.fragment.main.BottomFragment;
import com.benboer.boluo.core.app.AccountManager;
import com.benboer.boluo.core.fragment.PresenterFragment;
import com.benboer.boluo.factory.persistence.Account;
import com.benboer.boluo.factory.presenter.account.LoginContract;
import com.benboer.boluo.factory.presenter.account.LoginPresenter;

import net.qiujuer.genius.ui.widget.Loading;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/7.
 *
 * 用户登录
 */
public class LoginFragment extends PresenterFragment<LoginContract.Presenter>
            implements LoginContract.View{

    @BindView(R.id.edit_phone)
    EditText mPhone;

    @BindView(R.id.edit_password)
    EditText mPassword;

    @BindView(R.id.loading)
    Loading mLoading;

    @BindView(R.id.btn_submit)
    Button mSubmit;

    @OnClick(R.id.btn_submit)
    void onSubmitClick(){
        String phone = mPhone.getText().toString();
        String passWord = mPassword.getText().toString();
        mPresenter.login(phone, passWord);
    }

    @OnClick(R.id.txt_go_register)
    void onShowRegisterClick() {
        getSupportDelegate().startWithPop(new RegisterFragment());
    }


    @Override
    public Object setLayout() {
        return R.layout.fragment_login;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View root) {
        mPhone.setText(Account.getAccount());
    }

    @Override
    protected LoginContract.Presenter initPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public void loginSuccess() {
        AccountManager.setSignState(true);
        getSupportDelegate().startWithPop(new BottomFragment());
    }
}
