package com.benboer.boluo.boluomessage.fragment.account;

import com.benboer.boluo.common.app.PresenterFragment;
import com.benboer.boluo.factory.presenter.account.RegisterContract;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/7.
 */
public class RegisterFragment extends PresenterFragment<RegisterContract.Presenter>
        implements RegisterContract.View{

    @Override
    protected void initPresenter() {

    }

    @Override
    protected int getContentLayoutId() {
        return 0;
    }

    @Override
    public void registerSuccess() {

    }
}
