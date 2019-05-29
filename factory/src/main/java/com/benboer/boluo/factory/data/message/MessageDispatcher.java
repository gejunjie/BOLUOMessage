package com.benboer.boluo.factory.data.message;

import com.benboer.boluo.factory.model.card.MessageCard;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @ClassName: MessageDispatcher
 * @Description: 消息中心分发
 * @Author:  BenBoerBoluojiushiwo
 * @CreateDate: 2019-05-26 20:17
 * @Version: 1.0
 */
public class MessageDispatcher implements MessageCenter {

    private static MessageCenter instance;

    private final Executor executor = Executors.newSingleThreadExecutor();

    public static MessageDispatcher instance(){
        if (instance == null){
            synchronized (MessageDispatcher.class){
                if (instance == null){
                    instance = new MessageDispatcher();
                }
            }
        }
        return instance();
    }

    @Override
    public void dispatch(MessageCard... cards) {
        if (cards == null || cards.length == 0) return;
        executor.execute(new MessageCardHandler(cards));
    }

    private class MessageCardHandler implements Runnable{

        private final MessageCard[] cards;

        MessageCardHandler(MessageCard[] cards){
            this.cards = cards;
        }

        @Override
        public void run() {

        }
    }
}
