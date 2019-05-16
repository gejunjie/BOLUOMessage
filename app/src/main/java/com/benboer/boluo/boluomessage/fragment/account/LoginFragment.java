package com.benboer.boluo.boluomessage.fragment.account;

import android.content.Context;
import android.widget.Button;
import android.widget.EditText;

import com.benboer.boluo.boluomessage.R;
import com.benboer.boluo.boluomessage.activity.MainActivity;
import com.benboer.boluo.common.app.PresenterFragment;
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

    @OnClick
    void onSubmitClick(){
        String phone = mPhone.getText().toString();
        String passWord = mPassword.getText().toString();
        mPresenter.login(phone, passWord);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Activity的引用
        mAccountTrigger = (AccountTrigger) context;
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
