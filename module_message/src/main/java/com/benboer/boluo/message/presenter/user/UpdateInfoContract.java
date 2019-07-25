package com.benboer.boluo.message.presenter.user;

import com.benboer.boluo.common.mvp.presenter.BaseContract;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/8.
 */
public interface UpdateInfoContract {

    interface View extends BaseContract.View<Presenter>{
        // 回调成功
        void updateSucceed();
    }

    interface Presenter extends BaseContract.Presenter{
        // 更新
        void update(String photoFilePath, String desc, boolean isMan);
    }
}
