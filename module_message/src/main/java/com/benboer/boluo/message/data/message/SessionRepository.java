package com.benboer.boluo.message.data.message;

import androidx.annotation.NonNull;

import com.benboer.boluo.lib_db.db.Session;
import com.benboer.boluo.lib_db.db.Session_Table;
import com.benboer.boluo.message.data.BaseDbRepository;
import com.benboer.boluo.module_common.mvp.data.DataSource;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;

import java.util.Collections;
import java.util.List;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/31.
 */
public class SessionRepository extends BaseDbRepository<Session> implements SessionDataSource {

    @Override
    public void load(DataSource.SucceedCallback<List<Session>> callback) {
        super.load(callback);
        SQLite.select()
                .from(Session.class)
                .orderBy(Session_Table.modifyAt, false)
                .limit(100)
                .async()
                .queryListResultCallback(this)
                .execute();
    }

    @Override
    protected boolean isRequired(Session session) {
        return true;
    }

    @Override
    protected void insert(Session session) {
        // 复写方法，让新的数据加到头部
        dataList.addFirst(session);
    }

    @Override
    public void onListQueryResult(QueryTransaction transaction, @NonNull List<Session> tResult) {
        Collections.reverse(tResult);
        super.onListQueryResult(transaction, tResult);
    }
}
