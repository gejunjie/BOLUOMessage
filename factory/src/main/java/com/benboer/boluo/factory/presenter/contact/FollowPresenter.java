package com.benboer.boluo.factory.presenter.contact;

import com.benboer.boluo.factory.data.DataSource;
import com.benboer.boluo.factory.model.card.UserCard;
import com.benboer.boluo.factory.presenter.BasePresenter;

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

    }

    @Override
    public void onDataNotAvailable(int strRes) {

    }

    @Override
    public void onFollow() {

    }
}
