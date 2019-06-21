package com.benboer.boluo.boluomessage.fragment.account;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.benboer.boluo.boluomessage.R;
import com.benboer.boluo.boluomessage.activity.MainActivity;
import com.benboer.boluo.common.app.PresenterFragment;
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

    private AccountTrigger mAccountTrigger;

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
        // 让AccountActivity进行界面切换
        mAccountTrigger.triggerView();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Activity的引用
        mAccountTrigger = (AccountTrigger) context;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mPhone.setText(Account.getAccount());
    }

    @Override
    protected LoginContract.Presenter initPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void loginSuccess() {
        MainActivity.show(getContext());
        getActivity().finish();
    }
}
