package com.benboer.boluo.message.data.helper;

import com.benboer.boluo.message.R;
import com.benboer.boluo.db.db.Group;
import com.benboer.boluo.db.db.GroupMember;
import com.benboer.boluo.db.db.GroupMember_Table;
import com.benboer.boluo.db.db.Group_Table;
import com.benboer.boluo.db.db.User;
import com.benboer.boluo.db.db.User_Table;
import com.benboer.boluo.db.db.view.MemberUserModel;
import com.benboer.boluo.message.data.group.GroupDispatcher;
import com.benboer.boluo.message.model.api.group.GroupCreateModel;
import com.benboer.boluo.message.model.card.GroupCard;
import com.benboer.boluo.message.model.card.GroupMemberCard;
import com.benboer.boluo.message.net.RemoteService;
import com.benboer.boluo.common.net.model.RspModel;
import com.benboer.boluo.common.mvp.data.DataSource;
import com.benboer.boluo.common.net.Network;
import com.benboer.boluo.common.net.RspCodeDecoder;
import com.raizlabs.android.dbflow.sql.language.Join;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/24.
 */
public class GroupHelper {

    static RemoteService service = Network.getRetrofit().create(RemoteService.class);

    /**
     * 创建群
     * @param model
     * @param callback
     */
    public static void create(GroupCreateModel model, final DataSource.Callback<GroupCard> callback) {
        service.groupCreate(model)
                .enqueue(new Callback<RspModel<GroupCard>>() {
                    @Override
                    public void onResponse(Call<RspModel<GroupCard>> call, Response<RspModel<GroupCard>> response) {
                        RspModel<GroupCard> rspModel = response.body();
                        if (rspModel.success()) {
                            GroupCard groupCard = rspModel.getResult();
                            // 唤起进行保存的操作
                            GroupDispatcher.instance().dispatch(groupCard);
                            // 返回数据
                            callback.onDataLoaded(groupCard);
                        } else {
                            RspCodeDecoder.decodeRspCode(rspModel, callback);
                        }
                    }

                    @Override
                    public void onFailure(Call<RspModel<GroupCard>> call, Throwable t) {
                        callback.onDataNotAvailable(R.string.data_network_error);
                    }
                });
    }

    /**
     * 搜索群
     * @param name
     * @param callback
     * @return
     */
    public static Call groupSearch(String name, final DataSource.Callback<List<GroupCard>> callback) {
        Call<RspModel<List<GroupCard>>> call = service.groupSearch(name);

        call.enqueue(new Callback<RspModel<List<GroupCard>>>() {
            @Override
            public void onResponse(Call<RspModel<List<GroupCard>>> call, Response<RspModel<List<GroupCard>>> response) {
                RspModel<List<GroupCard>> rspModel = response.body();
                if (rspModel.success()){
                    callback.onDataLoaded(rspModel.getResult());
                }else {
                    RspCodeDecoder.decodeRspCode(rspModel, callback);
                }
            }

            @Override
            public void onFailure(Call<RspModel<List<GroupCard>>> call, Throwable t) {

            }
        });

        return call;
    }

    /**
     * 查询群的信息，先本地，后网络
     * @param groupId
     * @return
     */
    public static Group find(String groupId) {
        Group group = findFromLocal(groupId);
        if (group == null)
            group = findFormNet(groupId);
        return group;
    }

    private static Group findFormNet(String groupId) {
        try {
            Response<RspModel<GroupCard>> response = service.groupFind(groupId).execute();//同步发起请求
            GroupCard card = response.body().getResult();
            if (card != null) {
                // 数据库的存储并通知
                GroupDispatcher.instance().dispatch(card);

                User user = UserHelper.search(card.getOwnerId());
                if (user != null) {
                    return card.build(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 本地查询群组
     * @param groupId
     * @return
     */
    public static Group findFromLocal(String groupId) {
        return SQLite.select()
                .from(Group.class)
                .where(Group_Table.id.eq(groupId))
                .querySingle();
    }

    /**
     *  刷新我的群组列表
     */
    public static void refreshGroups() {
        service.groups("").enqueue(new Callback<RspModel<List<GroupCard>>>() {
            @Override
            public void onResponse(Call<RspModel<List<GroupCard>>> call, Response<RspModel<List<GroupCard>>> response) {
                RspModel<List<GroupCard>> rspModel = response.body();
                if (rspModel.success()) {
                    List<GroupCard> groupCards = rspModel.getResult();
                    if (groupCards != null && groupCards.size() > 0) {
                        // 进行调度显示
                        GroupDispatcher.instance().dispatch(groupCards.toArray(new GroupCard[0]));
                    }
                } else {
                    RspCodeDecoder.decodeRspCode(rspModel, null);
                }
            }

            @Override
            public void onFailure(Call<RspModel<List<GroupCard>>> call, Throwable t) {

            }
        });
    }

    /**
     *  获取一个群的成员数量
     */
    public static long getMemberCount(String id) {
        return SQLite.selectCountOf()
                .from(GroupMember.class)
                .where(GroupMember_Table.group_id.eq(id))
                .count();
    }

    /**
     * 关联查询一个用户和群成员的表，返回一个MemberUserModel表的集合
     */
    public static List<MemberUserModel> getMemberUsers(String groupId, int size) {
        return SQLite.select(GroupMember_Table.alias.withTable().as("alias"),
                User_Table.id.withTable().as("userId"),
                User_Table.name.withTable().as("name"),
                User_Table.portrait.withTable().as("portrait"))
                .from(GroupMember.class)
                .join(User.class, Join.JoinType.INNER)
                .on(GroupMember_Table.user_id.withTable().eq(User_Table.id.withTable()))
                .where(GroupMember_Table.group_id.withTable().eq(groupId))
                .orderBy(GroupMember_Table.user_id, true)
                .limit(size)
                .queryCustomList(MemberUserModel.class);
    }

    /**
     *  从网络去刷新一个群的成员信息
     */
    public static void refreshGroupMember(Group group) {
        service.groupMembers(group.getId()).enqueue(new Callback<RspModel<List<GroupMemberCard>>>() {
            @Override
            public void onResponse(Call<RspModel<List<GroupMemberCard>>> call, Response<RspModel<List<GroupMemberCard>>> response) {
                RspModel<List<GroupMemberCard>> rspModel = response.body();
                if (rspModel == null) return;
                if (rspModel.success()) {
                    List<GroupMemberCard> memberCards = rspModel.getResult();
                    if (memberCards != null && memberCards.size() > 0) {
                        // 进行调度显示
                        GroupDispatcher.instance().dispatch(memberCards.toArray(new GroupMemberCard[0]));
                    }
                } else {
                    RspCodeDecoder.decodeRspCode(rspModel, null);
                }
            }

            @Override
            public void onFailure(Call<RspModel<List<GroupMemberCard>>> call, Throwable t) {

            }
        });
    }



    private static long groupMemberCount = -1;

    /**
     *  获取当前群的成员数量，使用内存缓存
     */
    public static long getGroupMemberCount(String id) {
        if (groupMemberCount == -1) {
            // -1 没有初始化
            groupMemberCount = getMemberCount(id);
        }
        return groupMemberCount;
    }

    private static List<MemberUserModel> groupLatelyMembers;
    /**
     * 获取当前群对应的成员的信息，只加载4个信息
     */
    public static List<MemberUserModel> getLatelyGroupMembers(String id) {
        if (groupLatelyMembers == null || groupLatelyMembers.isEmpty()) {
            // 加载简单的用户信息，返回4条，至多
            groupLatelyMembers = getMemberUsers(id, 4);
        }

        return groupLatelyMembers;
    }

}
