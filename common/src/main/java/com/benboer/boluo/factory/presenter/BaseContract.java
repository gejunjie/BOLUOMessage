package com.benboer.boluo.factory.presenter;

import androidx.annotation.StringRes;

import com.benboer.boluo.common.widget.recycler.RecyclerAdapter;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/6.
 *
 * MVP模式中公共的基本协议
 */
public interface BaseContract {

    //基本的界面职责
    interface View<T extends Presenter>{
        // 公共的：显示一个字符串错误
        void showError(@StringRes int str);

        // 公共的：显示进度条
        void showLoading();

        // 设置一个Presenter
        void setPresenter(T presenter);
    }

    // 基本的Presenter职责
    interface Presenter{

        void start();

        void destroy();
    }

    // 基本的列表的View的职责
    interface RecyclerView<T extends Presenter, ViewMode> extends View<T>{
        // 拿到一个适配器，然后自己自主的进行刷新
        RecyclerAdapter<ViewMode> getRecyclerAdapter();

        // 当适配器数据更改了的时候触发
        void onAdapterDataChanged();
    }
}
