package com.benboer.boluo.factory.presenter.contact;

import com.benboer.boluo.factory.model.card.UserCard;
import com.benboer.boluo.factory.presenter.BaseContract;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/21.
 */
public interface FollowContract {

    interface Presenter extends BaseContract.Presenter{
        void onFollow();
    }

    interface View extends BaseContract.View<Presenter>{
        void onFollowSuccess(UserCard userCard);
    }
}
