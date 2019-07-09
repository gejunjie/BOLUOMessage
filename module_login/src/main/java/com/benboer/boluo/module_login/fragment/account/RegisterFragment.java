package com.benboer.boluo.module_login.fragment.account;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.benboer.boluo.module_common.base.PresenterFragment;
import com.benboer.boluo.module_login.R;
import com.benboer.boluo.module_login.presenter.account.RegisterContract;
import com.benboer.boluo.module_login.presenter.account.RegisterPresenter;

import net.qiujuer.genius.ui.widget.Loading;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/7.
 */
public class RegisterFragment extends PresenterFragment<RegisterContract.Presenter>
        implements RegisterContract.View{

//    @BindView(R2.id.edit_phone)
//    EditText mPhone;
//    @BindView(R2.id.edit_name)
//    EditText mName;
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
//        String name = mName.getText().toString();
//        String password = mPassword.getText().toString();
//        mPresenter.register(phone, name, password);
//    }
//
//    @OnClick(R2.id.txt_go_login)
//    void onShowLoginClick(){
//        getSupportDelegate().startWithPop(new LoginFragment());
//    }

    private EditText mPhone;
    private EditText mPassword;
    private Loading mLoading;
    private Button mSubmit;

    public RegisterFragment() {
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_register;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View root) {
        mPhone = bind(R.id.edit_phone);
        mPassword = bind(R.id.edit_password);
        mLoading = bind(R.id.loading);
    }

    @Override
    protected RegisterContract.Presenter initPresenter() {
        return new RegisterPresenter(this);
    }

    @Override
    public void registerSuccess() {
        getSupportDelegate().startWithPop(null
//                new BottomFragment()
        );
    }
}
