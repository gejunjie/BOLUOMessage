package com.benboer.boluo.factory.presenter.group;

import com.benboer.boluo.factory.data.DataSource;
import com.benboer.boluo.factory.model.card.GroupMemberCard;
import com.benboer.boluo.factory.presenter.BaseRecyclerPresenter;

import java.util.List;

/**
 * Created by BenBoerBoluojiushiwo on 2019/6/18.
 */
public class GroupMemberAddPresenter extends BaseRecyclerPresenter<GroupCreateContract.ViewModel,
        GroupMemberAddContract.View> implements GroupMemberAddContract.Presenter,
        DataSource.Callback<List<GroupMemberCard>> {
    public GroupMemberAddPresenter(GroupMemberAddContract.View view) {
        super(view);
    }

    @Override
    public void onDataLoaded(List<GroupMemberCard> groupMemberCards) {

    }

    @Override
    public void onDataNotAvailable(int strRes) {

    }

    @Override
    public void submit() {

    }

    @Override
    public void changeSelect(GroupCreateContract.ViewModel model, boolean isSelected) {

    }
}
