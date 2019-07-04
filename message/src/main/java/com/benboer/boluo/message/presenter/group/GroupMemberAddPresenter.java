package com.benboer.boluo.message.presenter.group;

import com.benboer.boluo.message.base.data.DataSource;
import com.benboer.boluo.message.model.card.GroupMemberCard;
import com.benboer.boluo.message.base.presenter.BaseRecyclerPresenter;

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
