package com.benboer.boluo.factory.presenter.group;

import com.benboer.boluo.factory.Factory;
import com.benboer.boluo.factory.data.helper.GroupHelper;
import com.benboer.boluo.factory.model.db.view.MemberUserModel;
import com.benboer.boluo.factory.presenter.BaseRecyclerPresenter;

import java.util.List;

/**
 * Created by BenBoerBoluojiushiwo on 2019/6/18.
 */
public class GroupMembersPresenter extends BaseRecyclerPresenter<MemberUserModel, GroupMembersContract.View>
        implements GroupMembersContract.Presenter{
    public GroupMembersPresenter(GroupMembersContract.View view) {
        super(view);
    }

    @Override
    public void refresh() {
        start();
        Factory.runOnAsync(new Runnable() {
            @Override
            public void run() {
                GroupMembersContract.View view = getView();
                if (view == null)
                    return;

                String groupId = view.getGroupId();

                // 传递数量为-1 代表查询所有
//                GroupHelper.refreshGroupMember(groupId);
                List<MemberUserModel> models = GroupHelper.getMemberUsers(groupId, -1);
                refreshData(models);
            }
        });
    }
}
