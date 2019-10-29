package com.benboer.boluo.message.data.group;

import android.text.TextUtils;

import com.benboer.boluo.message.db.Group;
import com.benboer.boluo.message.db.Group_Table;
import com.benboer.boluo.message.db.view.MemberUserModel;
import com.benboer.boluo.message.data.BaseDbRepository;
import com.benboer.boluo.message.data.helper.GroupHelper;
import com.benboer.boluo.common.mvp.data.DataSource;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

/**
 * Created by BenBoerBoluojiushiwo on 2019/6/11.
 */
public class GroupsRepository extends BaseDbRepository<Group>
        implements GroupsDataSource {

    @Override
    public void load(DataSource.SucceedCallback<List<Group>> callback) {
        super.load(callback);
        SQLite.select()
                .from(Group.class)
                .orderBy( Group_Table.name, true)
                .limit(100)
                .async()
                .queryListResultCallback(this)
                .execute();
    }

    @Override
    protected boolean isRequired(Group group) {
        // 一个群的信息，只可能两种情况出现在数据库
        // 一个是你被别人加入群，第二是你直接建立一个群
        // 无论什么情况，拿到的都只是群的信息，没有成员的信息
        // 需要进行成员信息初始化操作
        if (GroupHelper.getGroupMemberCount(group.getId()) > 0) {
            // 以及初始化了成员的信息
            group.holder = buildGroupHolder(group);
        } else {
            // 待初始化的群的信息
            group.holder = null;
            GroupHelper.refreshGroupMember(group);
        }

        // 所有的群都需要关注显示
        return true;
    }

    // 初始化界面显示的成员信息
    private String buildGroupHolder(Group group) {
        List<MemberUserModel> userModels = GroupHelper.getLatelyGroupMembers(group.getId());
        if (userModels == null || userModels.size() == 0)
            return null;

        StringBuilder builder = new StringBuilder();
        for (MemberUserModel userModel : userModels) {
            builder.append(TextUtils.isEmpty(userModel.alias) ? userModel.name : userModel.alias);
            builder.append(", ");
        }

        builder.delete(builder.lastIndexOf(", "), builder.length());

        return builder.toString();
    }

}
