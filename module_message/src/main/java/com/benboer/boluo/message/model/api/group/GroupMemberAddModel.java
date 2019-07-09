package com.benboer.boluo.message.model.api.group;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by BenBoerBoluojiushiwo on 2019/6/18.
 */
public class GroupMemberAddModel {
    private Set<String> users = new HashSet<>();

    public GroupMemberAddModel(Set<String> users) {
        this.users = users;
    }

    public Set<String> getUsers() {
        return users;
    }

    public void setUsers(Set<String> users) {
        this.users = users;
    }
}
