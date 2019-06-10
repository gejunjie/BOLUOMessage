package com.benboer.boluo.factory.presenter.group;

import com.benboer.boluo.factory.data.DataSource;
import com.benboer.boluo.factory.model.card.GroupCard;
import com.benboer.boluo.factory.presenter.BaseRecyclerPresenter;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by BenBoerBoluojiushiwo on 2019/6/10.
 */
public class GroupCreatePresenter extends BaseRecyclerPresenter<GroupCreateContract.ViewModel,
                                                                GroupCreateContract.View>
        implements GroupCreateContract.Presenter, DataSource.Callback<GroupCard> {

    private Set<String> users = new HashSet<>();

    public GroupCreatePresenter(GroupCreateContract.View view) {
        super(view);
    }

    @Override
    public void onDataLoaded(GroupCard groupCard) {

    }

    @Override
    public void onDataNotAvailable(int strRes) {

    }

    @Override
    public void create(String name, String desc, String picture) {

    }

    @Override
    public void changeSelect(GroupCreateContract.ViewModel model, boolean isSelected) {

    }
}
