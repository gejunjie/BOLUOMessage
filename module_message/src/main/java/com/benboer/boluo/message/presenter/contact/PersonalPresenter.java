package com.benboer.boluo.message.presenter.contact;

import com.benboer.boluo.module_common.Factory;
import com.benboer.boluo.message.data.helper.UserHelper;
import com.benboer.boluo.message.model.db.User;
import com.benboer.boluo.module_common.base.presenter.BasePresenter;
import com.benboer.boluo.module_common.persistence.Account;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/15.
 */
public class PersonalPresenter extends BasePresenter<PersonalContract.View>
        implements PersonalContract.Presenter{

    private User user;

    public PersonalPresenter(PersonalContract.View view) {
        super(view);
    }

    @Override
    public void start() {
        super.start();
        Factory.runOnAsync(new Runnable() {
            @Override
            public void run() {
                PersonalContract.View view = getView();
                User user = UserHelper.searchFirstOfNet(view.getUserId());
                if (user != null){
                    onLoaded(view, user);
                }
            }
        });
}


    private void onLoaded(final PersonalContract.View view, final User user) {
        this.user = user;
        // 是否就是我自己
        final boolean isSelf = user.getId().equalsIgnoreCase(Account.getUserId());
        // 是否已经关注
        final boolean isFollow = isSelf || user.isFollow();
        // 已经关注同时不是自己才能聊天
        final boolean allowSayHello = isFollow && !isSelf;

        // 切换到Ui线程
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.onLoadDone(user);
                view.setFollowStatus(isFollow);
                view.allowSayHello(allowSayHello);
            }
        });
    }


    @Override
    public User getUserPersonal() {
        return user;
    }
}
