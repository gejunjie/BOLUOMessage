package com.benboer.boluo.message.presenter.contact;

import com.benboer.boluo.common.mvp.data.DataSource;
import com.benboer.boluo.message.data.helper.UserHelper;
import com.benboer.boluo.message.model.card.UserCard;
import com.benboer.boluo.common.mvp.presenter.BasePresenter;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/21.
 */
public class FollowPresenter extends BasePresenter<FollowContract.View>
        implements FollowContract.Presenter, DataSource.Callback<UserCard>{

    public FollowPresenter(FollowContract.View view) {
        super(view);
    }

    @Override
    public void onDataLoaded(UserCard userCard) {
        FollowContract.View view = getView();
        view.onFollowSuccess(userCard);
    }

    @Override
    public void onDataNotAvailable(int strRes) {

    }

    @Override
    public void follow(String id) {
        UserHelper.follow(id, this);
    }
}
