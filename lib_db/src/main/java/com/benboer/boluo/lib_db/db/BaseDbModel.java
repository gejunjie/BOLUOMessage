package com.benboer.boluo.lib_db.db;

import com.benboer.boluo.lib_db.DiffUiDataCallback;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/23.
 */
public abstract class BaseDbModel<Model> extends BaseModel
        implements DiffUiDataCallback.UiDataDiffer<Model> {
}
