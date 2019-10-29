package com.benboer.boluo.message.data.group;

import com.benboer.boluo.message.db.Group;
import com.benboer.boluo.message.db.GroupMember;
import com.benboer.boluo.message.db.User;
import com.benboer.boluo.message.data.helper.DbHelper;
import com.benboer.boluo.message.data.helper.GroupHelper;
import com.benboer.boluo.message.data.helper.UserHelper;
import com.benboer.boluo.message.model.card.GroupCard;
import com.benboer.boluo.message.model.card.GroupMemberCard;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/27.
 */
public class GroupDispatcher implements GroupCenter {

    private static volatile GroupCenter instance;

    private final Executor executor = Executors.newSingleThreadExecutor();

    public static GroupCenter instance (){
        if (instance == null){
            synchronized (GroupDispatcher.class){
                if (instance == null){
                    instance = new GroupDispatcher();
                }
            }
        }
        return instance;
    }

    @Override
    public void dispatch(GroupCard... cards) {
        if (cards == null || cards.length == 0) return;
        executor.execute(new GroupHandler(cards));
    }

    @Override
    public void dispatch(GroupMemberCard... cards) {
        if (cards == null || cards.length == 0) return;
        executor.execute(new GroupMemberRspHandler(cards));
    }

    private class GroupMemberRspHandler implements Runnable{

        private final GroupMemberCard[] cards;

        public GroupMemberRspHandler(GroupMemberCard[] cards){
            this.cards = cards;
        }

        @Override
        public void run() {
            List<GroupMember> members = new ArrayList<>();
            for (GroupMemberCard card : cards){
                User user = UserHelper.search(card.getUserId());
                Group group = GroupHelper.find(card.getGroupId());
                if (user != null && group != null) {
                    GroupMember member = card.build(group, user);
                    members.add(member);
                }
            }
            if (members.size() > 0){
                DbHelper.save(GroupMember.class, members.toArray(new GroupMember[0]));
            }
        }
    }

    private class GroupHandler implements Runnable{

        private final GroupCard[] cards;

        public GroupHandler(GroupCard[] cards){
            this.cards = cards;
        }

        @Override
        public void run() {
            List<Group> groups = new ArrayList<>();
            for (GroupCard card : cards) {
                // 搜索管理员
                User owner = UserHelper.search(card.getOwnerId());
                if (owner != null) {
                    Group group = card.build(owner);
                    groups.add(group);
                }
            }
            if (groups.size() > 0)
                DbHelper.save(Group.class, groups.toArray(new Group[0]));
        }
    }
}
