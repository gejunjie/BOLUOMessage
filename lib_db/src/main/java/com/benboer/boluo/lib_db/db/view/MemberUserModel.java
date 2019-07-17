package com.benboer.boluo.lib_db.db.view;

import com.benboer.boluo.lib_db.db.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.QueryModel;

/**
 * Created by BenBoerBoluojiushiwo on 2019/6/13.
 *
 * 群成员对应的用户的简单信息表
 */
@QueryModel(database = AppDatabase.class)
public class MemberUserModel {
    @Column
    public String userId; // User-id/Member-userId
    @Column
    public String name; // User-name
    @Column
    public String alias; // Member-alias
    @Column
    public String portrait; // User-portrait
}
