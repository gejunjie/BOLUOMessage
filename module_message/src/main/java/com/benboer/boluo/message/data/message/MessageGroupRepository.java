package com.benboer.boluo.message.data.message;

import androidx.annotation.NonNull;

import com.benboer.boluo.message.db.Message;
import com.benboer.boluo.message.data.BaseDbRepository;
import com.benboer.boluo.common.mvp.data.DataSource;
import com.benboer.boluo.message.db.Message_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;

import java.util.Collections;
import java.util.List;

/**
 * Created by BenBoerBoluojiushiwo on 2019/6/14.
 */
public class MessageGroupRepository extends BaseDbRepository<Message>
    implements MessageDataSource{

    // 聊天的群Id
    private String receiverId;

    public MessageGroupRepository(String receiverId) {
        super();
        this.receiverId = receiverId;
    }

    @Override
    public void load(DataSource.SucceedCallback<List<Message>> callback) {
        super.load(callback);

        // 无论是直接发还是别人发，只要是发到这个群的，
        // 那个这个group_id就是receiverId
        SQLite.select()
                .from(Message.class)
                .where( Message_Table.group_id.eq(receiverId))
                .orderBy(Message_Table.createAt, false)
                .limit(30)
                .async()
                .queryListResultCallback(this)
                .execute();
    }

    @Override
    protected boolean isRequired(Message message) {
        // 如果消息的Group不为空，则一定是发送到一个群的
        // 如果群Id等于我们需要的，那就是通过
        return message.getGroup() != null
                && receiverId.equalsIgnoreCase(message.getGroup().getId());
    }

    @Override
    public void onListQueryResult(QueryTransaction transaction, @NonNull List<Message> tResult) {
        // 反转返回的集合
        Collections.reverse(tResult);
        // 然后再调度
        super.onListQueryResult(transaction, tResult);
    }
}
