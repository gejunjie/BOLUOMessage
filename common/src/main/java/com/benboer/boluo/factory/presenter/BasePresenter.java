package com.benboer.boluo.factory.presenter;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/6.
 */
public class BasePresenter<T extends BaseContract.View> implements BaseContract.Presenter {

    private T mView;

    public BasePresenter(T view){
        setView(view);
    }

    protected void setView(T view){
        mView = view;
        mView.setPresenter(this);
    }

    protected final T getView(){
        return mView;
    }

    @Override
    public void start() {
        // 开始的时候进行Loading调用
        T view = mView;
        if (view != null) {
            view.showLoading();
        }
    }

    @Override
    public void destory() {
        T view = mView;
        mView = null;
        if (view != null) {
            // 把Presenter设置为NULL
            view.setPresenter(null);
        }
    }
}
