package com.benboer.boluo.message.base.data;

import java.util.List;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/24.
 *
 * 基础的数据库数据源接口定义
 *
 */
public interface DbDataSource<Data> extends DataSource {

    /**
     * 有基本的数据源加载方法
     *
     * @param callback 传递一个callback回调，一般回调到Presenter
     */
    void load(SucceedCallback<List<Data>> callback);
}
