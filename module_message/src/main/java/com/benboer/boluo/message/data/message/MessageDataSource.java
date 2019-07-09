package com.benboer.boluo.message.data.message;

import com.benboer.boluo.module_common.base.data.DbDataSource;
import com.benboer.boluo.message.model.db.Message;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/31.
 *
 * 消息的数据源定义，由MessageRepository实现
 * 关注的对象是Message表
 */
public interface MessageDataSource extends DbDataSource<Message> {
}
