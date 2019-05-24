package com.benboer.boluo.factory.data.helper;

import com.benboer.boluo.factory.model.db.Session;
import com.benboer.boluo.factory.model.db.Session_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/24.
 */
public class SessionHelper {
    // 从本地查询Session
    public static Session findFromLocal(String id) {
        return SQLite.select()
                .from(Session.class)
                .where(Session_Table.id.eq(id))
                .querySingle();
    }
}
