package com.benboer.boluo.message.presenter.contact;

import com.benboer.boluo.message.model.card.UserCard;
import com.benboer.boluo.module_common.mvp.presenter.BaseContract;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/21.
 */
public interface FollowContract {

    interface Presenter extends BaseContract.Presenter{
        void follow(String id);
    }

    interface View extends BaseContract.View<Presenter>{
        void onFollowSuccess(UserCard userCard);
    }
}
