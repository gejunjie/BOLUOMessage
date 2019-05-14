package com.benboer.boluo.boluomessage.fragment.account;

import android.content.Context;
import android.widget.Button;
import android.widget.EditText;

import com.benboer.boluo.boluomessage.R;
import com.benboer.boluo.common.app.PresenterFragment;
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

    private AccountTrigger mAccountTrigger;

    @BindView(R.id.edit_phone)
    EditText mPhone;
    @BindView(R.id.edit_name)
    EditText mName;
    @BindView(R.id.edit_password)
    EditText mPassword;


    @BindView(R.id.loading)
    Loading mLoading;

    @BindView(R.id.btn_submit)
    Button mSubmit;

    @OnClick(R.id.btn_submit)
    void onSubmitClick(){
        String phone = mPhone.getText().toString();
        String name = mName.getText().toString();
        String password = mPassword.getText().toString();
        mPresenter.register(phone, name, password);
    }

    @OnClick(R.id.txt_go_login)
    void onShowLoginClick(){
        mAccountTrigger.triggerView();
    }


    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAccountTrigger = (AccountTrigger) context;
    }

    @Override
    protected RegisterContract.Presenter initPresenter() {

        return new RegisterPresenter(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    public void registerSuccess() {

    }
}
