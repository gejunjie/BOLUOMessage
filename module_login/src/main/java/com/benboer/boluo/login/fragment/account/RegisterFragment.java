package com.benboer.boluo.login.fragment.account;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.benboer.boluo.common.mvp.PresenterFragment;
import com.benboer.boluo.login.R;
import com.benboer.boluo.login.presenter.account.RegisterContract;
import com.benboer.boluo.login.presenter.account.RegisterPresenter;

import net.qiujuer.genius.ui.widget.Loading;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/7.
 */
public class RegisterFragment extends PresenterFragment<RegisterContract.Presenter>
        implements RegisterContract.View{

    private EditText mPhone;
    private EditText mPassword;
    private EditText mName;
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
        mName = bind(R.id.edit_name);
        mPassword = bind(R.id.edit_password);
        mLoading = bind(R.id.loading);
        bind(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = mPhone.getText().toString();
                String name = mName.getText().toString();
                String password = mPassword.getText().toString();
                // 调用P层进行注册
                mPresenter.register(phone, name, password);
            }
        });
    }


    @Override
    public void showError(int str) {
        super.showError(str);
        // 当需要显示错误的时候触发，一定是结束了

        // 停止Loading
        mLoading.stop();
        // 让控件可以输入
        mPhone.setEnabled(true);
        mName.setEnabled(true);
        mPassword.setEnabled(true);
        // 提交按钮可以继续点击
        mSubmit.setEnabled(true);
    }

    @Override
    public void showLoading() {
        super.showLoading();

        // 正在进行时，正在进行注册，界面不可操作
        // 开始Loading
        mLoading.start();
        // 让控件不可以输入
        mPhone.setEnabled(false);
        mName.setEnabled(false);
        mPassword.setEnabled(false);
        // 提交按钮不可以继续点击
        mSubmit.setEnabled(false);
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
