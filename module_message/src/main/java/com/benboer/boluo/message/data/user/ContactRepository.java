package com.benboer.boluo.message.data.user;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.benboer.boluo.common.mvp.data.DataSource;
import com.benboer.boluo.common.service.AccountService;
import com.benboer.boluo.message.data.BaseDbRepository;
import com.benboer.boluo.message.db.User;
import com.benboer.boluo.message.db.User_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/30.
 */
public class ContactRepository extends BaseDbRepository<User> implements ContactDataSource {

    @Autowired(name = "/main/account_service")
    protected AccountService mAccountService;

    @Override
    public void load(DataSource.SucceedCallback<List<User>> callback) {
        super.load(callback);
        // 加载本地数据库数据
        SQLite.select()
                .from(User.class)
                .where( User_Table.isFollow.eq(true))
                .and(User_Table.id.notEq(mAccountService.getUserId()))
                .orderBy(User_Table.name, true)
                .limit(100)
                .async()
                .queryListResultCallback(this)
                .execute();
    }

    @Override
    protected boolean isRequired(User user) {
        return user.isFollow() && !user.getId().equals(mAccountService.getUserId());
    }
}
