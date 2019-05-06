package com.benboer.boluo.factory.utils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.grosner.dbflow.structure.ModelAdapter;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/6.
 */
public class DBFlowExclusionStrategy implements ExclusionStrategy {
    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        // 被跳过的字段
        // 只要是属于DBFlow数据的
        return f.getDeclaredClass().equals(ModelAdapter.class);
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        // 被跳过的Class
        return false;
    }
}
