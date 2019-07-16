package com.benboer.boluo.message.presenter.search;

import com.benboer.boluo.message.model.card.GroupCard;
import com.benboer.boluo.message.model.card.UserCard;
import com.benboer.boluo.module_common.base.mvp.presenter.BaseContract;

import java.util.List;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/20.
 */
public interface SearchContract {

    interface Presenter extends BaseContract.Presenter{
        void onSearch(String content);
    }

    interface UserView extends BaseContract.View<Presenter>{
        void onSearchDone(List<UserCard> userCards);
    }

    interface GroupView extends BaseContract.View<Presenter>{
        void onSearchDone(List<GroupCard> groupCards);
    }
}
