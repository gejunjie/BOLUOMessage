package com.benboer.boluo.message.presenter.contact;

import com.benboer.boluo.common.app.BoLuo;
import com.benboer.boluo.common.mvp.presenter.BasePresenter;
import com.benboer.boluo.common.persistence.Account;
import com.benboer.boluo.common.util.HandlerUtil;
import com.benboer.boluo.db.db.User;
import com.benboer.boluo.message.data.helper.UserHelper;

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
        BoLuo.runOnAsync(new Runnable() {
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
        if (view != null){
            HandlerUtil.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    view.onLoadDone(user);
                    view.setFollowStatus(isFollow);
                    view.allowSayHello(allowSayHello);
                }
            });
        }
    }


    @Override
    public User getUserPersonal() {
        return user;
    }
}
