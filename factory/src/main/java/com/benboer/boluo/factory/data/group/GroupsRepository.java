package com.benboer.boluo.factory.data.group;

import com.benboer.boluo.factory.data.BaseDbRepository;
import com.benboer.boluo.factory.model.db.Group;

/**
 * Created by BenBoerBoluojiushiwo on 2019/6/11.
 */
public class GroupsRepository extends BaseDbRepository<Group>
        implements GroupsDataSource {
    @Override
    protected boolean isRequired(Group group) {
        return false;
    }


}
