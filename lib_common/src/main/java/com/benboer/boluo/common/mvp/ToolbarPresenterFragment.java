package com.benboer.boluo.common.mvp;

import android.content.Context;

import com.benboer.boluo.common.base.fragment.ToolbarSupportFragment;
import com.benboer.boluo.common.mvp.presenter.BaseContract;
import com.blankj.utilcode.util.ToastUtils;

/**
 * @ClassName: ToolbarPresenterFragment
 * @Description: java类作用描述
 * @Author:  BenBoerBoluojiushiwo
 * @CreateDate: 2019-12-15 09:44
 * @Version: 1.0
 */
public abstract class ToolbarPresenterFragment<Presenter extends BaseContract.Presenter>
        extends ToolbarSupportFragment implements BaseContract.View<Presenter>{

    protected Presenter mPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        initPresenter();
    }

    protected abstract Presenter initPresenter();

    @Override
    public void showError(int str) {
        // 显示错误, 优先使用占位布局
        if (mPlaceHolderView != null) {
            mPlaceHolderView.triggerError(str);
        } else {
            ToastUtils.showLong(str);
        }
    }

    @Override
    public void showLoading() {
        if (mPlaceHolderView != null) {
            mPlaceHolderView.triggerLoading();
        }
    }

    protected void hideLoading() {
        if (mPlaceHolderView != null) {
            mPlaceHolderView.triggerOk();
        }
    }

    @Override
    public void setPresenter(Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null){
            mPresenter.destroy();
        }
    }
}
