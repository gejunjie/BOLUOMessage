package com.benboer.boluo.factory.data.user;

import android.text.TextUtils;

import com.benboer.boluo.factory.data.helper.DbHelper;
import com.benboer.boluo.factory.model.card.UserCard;
import com.benboer.boluo.factory.model.db.User;

import net.qiujuer.genius.kit.handler.Run;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @ClassName: UserDispatcher
 * @Description: java类作用描述
 * @Author:  BenBoerBoluojiushiwo
 * @CreateDate: 2019-05-26 17:15
 * @Version: 1.0
 */
public class UserDispatcher implements UserCenter {

    private static UserCenter instance;

    private final Executor executor = Executors.newSingleThreadExecutor();

    public static UserCenter instance(){
        if (instance == null){
            synchronized (UserDispatcher.class){
                if (instance == null){
                    instance = new UserDispatcher();
                }
            }
        }
        return instance;
    }

    @Override
    public void dispatch(UserCard... cards) {
        if (cards == null || cards.length == 0) return;
        executor.execute(new UserCardHandler(cards));
    }

    private class UserCardHandler implements Runnable{

        private final UserCard[] cards;

        UserCardHandler(UserCard[] cards){
            this.cards = cards;
        }

        @Override
        public void run() {
            List<User> users = new ArrayList<>();
            for (UserCard card : cards){
                if (card == null || TextUtils.isEmpty(card.getId())) continue;
                users.add(card.build());
            }

            DbHelper.save(User.class, users.toArray(new User[0]));
        }
    }
}
