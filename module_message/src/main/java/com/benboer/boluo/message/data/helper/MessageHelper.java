package com.benboer.boluo.message.data.helper;

import com.benboer.boluo.common.app.BoLuo;
import com.benboer.boluo.db.db.Message;
import com.benboer.boluo.db.db.Message_Table;
import com.benboer.boluo.message.data.message.MessageDispatcher;
import com.benboer.boluo.message.model.api.message.MsgCreateModel;
import com.benboer.boluo.message.model.card.MessageCard;
import com.benboer.boluo.message.net.RemoteService;
import com.benboer.boluo.common.net.model.RspModel;
import com.benboer.boluo.common.net.Network;
import com.benboer.boluo.common.net.RspCodeDecoder;
import com.raizlabs.android.dbflow.sql.language.OperatorGroup;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/24.
 */
public class MessageHelper {

    /**
     * 查询第一条消息
     * @param id
     * @return
     */
    public static Message findFromLocal(String id) {
        return SQLite.select()
                .from(Message.class)
                .where(Message_Table.id.eq(id))
                .querySingle();//只返回第一条数据
    }

    /**
     * 查询群中最后一条消息
     * @param groupId
     * @return
     */
    public static Message findLastWithGroup(String groupId){
        return SQLite.select()
                .from(Message.class)
                .where(Message_Table.group_id.eq(groupId))
                .orderBy(Message_Table.createAt, false)
                .querySingle();
    }

    /**
     * 查询和某人的最后一条消息
     * @param userId
     * @return
     */
    public static Message findLastWithUser(String userId){
        return SQLite.select()
                .from(Message.class)
                .where(OperatorGroup.clause()
                        .and(Message_Table.sender_id.eq(userId))
                        .and(Message_Table.group_id.isNull()))
                .orderBy(Message_Table.createAt, false)
                .querySingle();
    }

    public static void push(final MsgCreateModel model){
        BoLuo.runOnAsync(new Runnable() {
            @Override
            public void run() {
                // 成功状态：如果是一个已经发送过的消息，则不能重新发送
                // 正在发送状态：如果是一个消息正在发送，则不能重新发送
                final Message message = MessageHelper.findFromLocal(model.getId());
                if (message != null && message.getStatus() != Message.STATUS_FAILED)
                return;

                final MessageCard card = model.buildCard();
                MessageDispatcher.instance().dispatch(card);

                Network.getRetrofit().create(RemoteService.class)
                        .msgPush(model).enqueue(new Callback<RspModel<MessageCard>>() {
                    @Override
                    public void onResponse(Call<RspModel<MessageCard>> call,
                                           Response<RspModel<MessageCard>> response) {
                        RspModel<MessageCard> model1 = response.body();
                        if (model1 != null && model1.success()){
                            MessageCard card1 = model1.getResult();
                            if (card1 != null){
                                MessageDispatcher.instance().dispatch(card1);
                            }
                        }else {
                            RspCodeDecoder.decodeRspCode(model1, null);
                            onFailure(call, null);
                        }
                    }

                    @Override
                    public void onFailure(Call<RspModel<MessageCard>> call, Throwable t) {
                        card.setStatus(Message.STATUS_FAILED);
                        MessageDispatcher.instance().dispatch(card);
                    }
                });
            }
        });
    }
}
