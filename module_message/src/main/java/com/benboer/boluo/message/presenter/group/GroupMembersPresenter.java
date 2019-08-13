package com.benboer.boluo.message.presenter.group;

import com.benboer.boluo.common.app.BoLuo;
import com.benboer.boluo.db.db.view.MemberUserModel;
import com.benboer.boluo.message.data.helper.GroupHelper;
import com.benboer.boluo.common.mvp.presenter.BaseRecyclerPresenter;

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
        BoLuo.runOnAsync(new Runnable() {
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
