package com.benboer.boluo.message.data.message;

import com.benboer.boluo.message.model.card.MessageCard;

/**
 * @ClassName: MessageCenter
 * @Description: 消息卡片消费
 * @Author:  BenBoerBoluojiushiwo
 * @CreateDate: 2019-05-26 20:16
 * @Version: 1.0
 */
public interface MessageCenter {
    void dispatch(MessageCard... cards);
}
