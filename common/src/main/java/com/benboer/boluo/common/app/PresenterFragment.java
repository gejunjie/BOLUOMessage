package com.benboer.boluo.common.app;

import android.content.Context;

import com.benboer.boluo.factory.presenter.BaseContract;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/7.
 */
public abstract class PresenterFragment<Presenter extends BaseContract.Presenter>
        extends BaseFragment
        implements BaseContract.View<Presenter>{

    protected Presenter mPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        initPresenter();
    }

    protected abstract Presenter initPresenter();

    @Override
    public void showError(int str) {
        Application.showToast(str);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void setPresenter(Presenter presenter) {
        mPresenter = presenter;
    }
}
