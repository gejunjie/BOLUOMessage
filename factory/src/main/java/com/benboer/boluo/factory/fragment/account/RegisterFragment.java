package com.benboer.boluo.factory.fragment.account;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.benboer.boluo.factory.R2;
import com.benboer.boluo.factory.fragment.main.BottomFragment;
import com.benboer.boluo.core.fragment.PresenterFragment;
import com.benboer.boluo.factory.presenter.account.RegisterContract;
import com.benboer.boluo.factory.presenter.account.RegisterPresenter;

import net.qiujuer.genius.ui.widget.Loading;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/7.
 */
public class RegisterFragment extends PresenterFragment<RegisterContract.Presenter>
        implements RegisterContract.View{

    @BindView(R2.id.edit_phone)
    EditText mPhone;
    @BindView(R2.id.edit_name)
    EditText mName;
    @BindView(R2.id.edit_password)
    EditText mPassword;

    @BindView(R2.id.loading)
    Loading mLoading;

    @BindView(R2.id.btn_submit)
    Button mSubmit;

    @OnClick(R2.id.btn_submit)
    void onSubmitClick(){
        String phone = mPhone.getText().toString();
        String name = mName.getText().toString();
        String password = mPassword.getText().toString();
        mPresenter.register(phone, name, password);
    }

    @OnClick(R2.id.txt_go_login)
    void onShowLoginClick(){
        getSupportDelegate().startWithPop(new LoginFragment());
    }


    public RegisterFragment() {
    }

    @Override
    public Object setLayout() {
        return R2.layout.fragment_register;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View root) {

    }

    @Override
    protected RegisterContract.Presenter initPresenter() {
        return new RegisterPresenter(this);
    }

    @Override
    public void registerSuccess() {
        getSupportDelegate().startWithPop(new BottomFragment());
    }
}