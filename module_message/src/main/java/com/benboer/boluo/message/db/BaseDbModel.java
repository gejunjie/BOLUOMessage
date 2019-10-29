package com.benboer.boluo.message.db;

import com.benboer.boluo.message.db.DiffUiDataCallback;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/23.
 */
public abstract class BaseDbModel<Model> extends BaseModel
        implements DiffUiDataCallback.UiDataDiffer<Model> {
}
