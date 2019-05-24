package com.benboer.boluo.factory.data;

import com.benboer.boluo.factory.data.helper.DbHelper;
import com.benboer.boluo.factory.model.db.BaseDbModel;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/23.
 */
public abstract class BaseDbRepository<Data extends BaseDbModel<Data>> implements DbDataSource<Data>,
        DbHelper.ChangedListener<Data>,
        QueryTransaction.QueryResultListCallback<Data> {

}
