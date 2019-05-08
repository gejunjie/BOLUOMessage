package com.benboer.boluo.factory.presenter.user;

import com.benboer.boluo.factory.data.DataSource;
import com.benboer.boluo.factory.model.card.UserCard;
import com.benboer.boluo.factory.presenter.BasePresenter;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/8.
 */
public class UpdateInfoPresenter extends BasePresenter<UpdateInfoContract.View>
    implements UpdateInfoContract.Presenter, DataSource.Callback<UserCard> {

    public UpdateInfoPresenter(UpdateInfoContract.View view) {
        super(view);
    }

    @Override
    public void onDataLoaded(UserCard userCard) {

    }

    @Override
    public void onDataNotAvailable(int strRes) {

    }

    @Override
    public void update(String photoFilePath, String desc, boolean isMan) {

    }
}
