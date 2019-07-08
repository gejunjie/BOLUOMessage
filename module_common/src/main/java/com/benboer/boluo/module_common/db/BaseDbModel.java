package com.benboer.boluo.module_common.db;

import com.benboer.boluo.module_common.utils.DiffUiDataCallback;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/23.
 */
public abstract class BaseDbModel<Model> extends BaseModel
        implements DiffUiDataCallback.UiDataDiffer<Model> {
}
