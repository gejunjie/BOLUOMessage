package com.benboer.boluo.db.model;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/14.
 *
 * 基础用户接口
 */
public interface Author {
    String getId();

    void setId(String id);

    String getName();

    void setName(String name);

    String getPortrait();

    void setPortrait(String portrait);
}
