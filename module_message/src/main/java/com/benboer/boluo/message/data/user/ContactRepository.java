package com.benboer.boluo.message.data.user;

import com.benboer.boluo.lib_db.db.User;
import com.benboer.boluo.lib_db.db.User_Table;
import com.benboer.boluo.message.data.BaseDbRepository;
import com.benboer.boluo.module_common.mvp.data.DataSource;
import com.benboer.boluo.module_common.persistence.Account;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/30.
 */
public class ContactRepository extends BaseDbRepository<User> implements ContactDataSource {

    @Override
    public void load(DataSource.SucceedCallback<List<User>> callback) {
        super.load(callback);
        // 加载本地数据库数据
        SQLite.select()
                .from(User.class)
                .where(User_Table.isFollow.eq(true))
                .and(User_Table.id.notEq(Account.getUserId()))
                .orderBy(User_Table.name, true)
                .limit(100)
                .async()
                .queryListResultCallback(this)
                .execute();
    }

    @Override
    protected boolean isRequired(User user) {
        return user.isFollow() && !user.getId().equals(Account.getUserId());
    }
}
