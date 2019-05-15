package com.benboer.boluo.common.app;

import com.benboer.boluo.factory.presenter.BaseContract;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/15.
 */
public abstract class PresenterToolbarActivity<Presenter extends BaseContract.Presenter>
                extends ToolbarActivity
                implements BaseContract.View<Presenter>{

    protected Presenter mPresenter;

    @Override
    protected void initBefore() {
        super.initBefore();
        initPresenter();
    }

    protected abstract Presenter initPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null){
            mPresenter.destory();
        }
    }

    @Override
    public void showError(int str) {
        if (mPlaceHolderView != null){
            mPlaceHolderView.triggerError(str);
        }else {
            Application.showToast(str);
        }
    }

    @Override
    public void showLoading() {
        if (mPlaceHolderView != null){
            mPlaceHolderView.triggerLoading();
        }
    }
    @Override
    public void setPresenter(Presenter presenter) {
        mPresenter = presenter;
    }
}
