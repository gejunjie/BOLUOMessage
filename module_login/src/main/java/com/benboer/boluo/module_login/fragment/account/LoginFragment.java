package com.benboer.boluo.module_login.fragment.account;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.benboer.boluo.core.app.AccountManager;
import com.benboer.boluo.module_common.base.PresenterFragment;
import com.benboer.boluo.module_common.persistence.Account;
import com.benboer.boluo.module_login.R;
import com.benboer.boluo.module_login.presenter.account.LoginContract;
import com.benboer.boluo.module_login.presenter.account.LoginPresenter;

import net.qiujuer.genius.ui.widget.Loading;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/7.
 *
 * 用户登录
 */
public class LoginFragment extends PresenterFragment<LoginContract.Presenter>
        implements LoginContract.View{
//
//    @BindView(R2.id.edit_phone)
//    EditText mPhone;
//
//    @BindView(R2.id.edit_password)
//    EditText mPassword;
//
//    @BindView(R2.id.loading)
//    Loading mLoading;
//
//    @BindView(R2.id.btn_submit)
//    Button mSubmit;
//
//    @OnClick(R2.id.btn_submit)
//    void onSubmitClick(){
//        String phone = mPhone.getText().toString();
//        String passWord = mPassword.getText().toString();
//        mPresenter.login(phone, passWord);
//    }
//
//    @OnClick(R2.id.txt_go_register)
//    void onShowRegisterClick() {
//        getSupportDelegate().startWithPop(null
////                new RegisterFragment()
//                );
//    }

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

            }
        });
        mPhone.setText(Account.getAccount());
    }

    @Override
    protected LoginContract.Presenter initPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public void loginSuccess() {
        AccountManager.setSignState(true);
        getSupportDelegate().startWithPop(null
//                new BottomFragment()
        );
    }


}
