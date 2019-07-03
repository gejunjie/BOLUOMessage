package com.benboer.boluo.boluomessage.fragment.group;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.benboer.boluo.core.fragment.PresenterFragment;
import com.benboer.boluo.factory.model.db.view.MemberUserModel;
import com.benboer.boluo.factory.presenter.group.GroupMembersContract;
import com.benboer.boluo.widget.recycler.RecyclerAdapter;

/**
 * Created by BenBoerBoluojiushiwo on 2019/7/3.
 */
public class GroupMemberFragment extends PresenterFragment<GroupMembersContract.Presenter>
        implements GroupMembersContract.View, GroupMemberAddFragment.Callback {
    @Override
    public void hideLoading() {

    }

    @Override
    public void refreshMembers() {

    }

    @Override
    protected GroupMembersContract.Presenter initPresenter() {
        return null;
    }

    @Override
    public Object setLayout() {
        return null;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View root) {

    }

    @Override
    public String getGroupId() {
        return null;
    }

    @Override
    public RecyclerAdapter<MemberUserModel> getRecyclerAdapter() {
        return null;
    }

    @Override
    public void onAdapterDataChanged() {

    }
}
