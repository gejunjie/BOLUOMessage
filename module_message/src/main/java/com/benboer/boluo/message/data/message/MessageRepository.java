package com.benboer.boluo.message.data.message;

import androidx.annotation.NonNull;

import com.benboer.boluo.message.data.BaseDbRepository;
import com.benboer.boluo.message.model.db.Message;
import com.benboer.boluo.message.model.db.Message_Table;
import com.benboer.boluo.module_common.base.mvp.data.DataSource;
import com.raizlabs.android.dbflow.sql.language.OperatorGroup;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;

import java.util.Collections;
import java.util.List;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/31.
 */
public class MessageRepository extends BaseDbRepository<Message> implements MessageDataSource {

    private String receiverId;

    public MessageRepository(String receiverId){
        super();
        this.receiverId = receiverId;
    }

    @Override
    public void load(DataSource.SucceedCallback<List<Message>> callback) {
        super.load(callback);
        SQLite.select()
                .from(Message.class)
                .where(OperatorGroup.clause()
                    .and(Message_Table.sender_id.eq(receiverId))
                    .and(Message_Table.group_id.isNull()))
                .or(Message_Table.receiver_id.eq(receiverId))
                .orderBy(Message_Table.createAt, false)
                .limit(300)
                .async()
                .queryListResultCallback(this)
                .execute();
    }

    @Override
    protected boolean isRequired(Message message) {
        return (receiverId.equalsIgnoreCase(message.getSender().getId())
                && message.getGroup() == null)
                || (message.getReceiver() != null
                && receiverId.equalsIgnoreCase(message.getReceiver().getId())
        );
    }

    @Override
    public void onListQueryResult(QueryTransaction transaction, @NonNull List<Message> tResult) {
        // 反转返回的集合
        Collections.reverse(tResult);
        super.onListQueryResult(transaction, tResult);
    }
}
