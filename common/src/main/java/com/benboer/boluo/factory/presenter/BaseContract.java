package com.benboer.boluo.factory.presenter;

import androidx.annotation.StringRes;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/6.
 *
 * MVP模式中公共的基本协议
 */
public interface BaseContract {

    interface View<T extends Presenter>{
        // 公共的：显示一个字符串错误
        void showError(@StringRes int str);

        // 公共的：显示进度条
        void showLoading();

        // 设置一个Presenter
        void setPresenter(T presenter);
    }

    interface Presenter{

        void start();

        void destory();
    }
}
