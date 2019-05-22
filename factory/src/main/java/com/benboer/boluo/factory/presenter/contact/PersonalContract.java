package com.benboer.boluo.factory.presenter.contact;

import com.benboer.boluo.factory.model.db.User;
import com.benboer.boluo.factory.presenter.BaseContract;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/15.
 */
public interface PersonalContract {
    interface Presenter extends BaseContract.Presenter{
        // 获取用户信息
        User getUserPersonal();
    }

    interface View extends BaseContract.View<Presenter>{
        String getUserId();

        // 加载数据完成
        void onLoadDone(User user);

        // 是否发起聊天
        void allowSayHello(boolean isAllow);

        // 设置关注状态
        void setFollowStatus(boolean isFollow);
    }
}
