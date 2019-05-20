package com.benboer.boluo.factory.presenter.search;

import com.benboer.boluo.factory.data.DataSource;
import com.benboer.boluo.factory.data.helper.UserHelper;
import com.benboer.boluo.factory.model.card.UserCard;
import com.benboer.boluo.factory.presenter.BasePresenter;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.List;

import retrofit2.Call;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/20.
 */
public class SearchUserPresenter extends BasePresenter<SearchContract.UserView>
        implements SearchContract.Presenter, DataSource.Callback<List<UserCard>> {

    private Call searchCall;

    public SearchUserPresenter(SearchContract.UserView view) {
        super(view);
    }

    @Override
    public void onSearch(String content) {
        start();
        if (searchCall != null && !searchCall.isCanceled()){
            searchCall.cancel();
        }
        searchCall = UserHelper.userSearch(content, this);
    }

    @Override
    public void onDataLoaded(final List<UserCard> userCardList) {
        final SearchContract.UserView view = getView();
        if (view != null){
            Run.onUiAsync(new Action() {
                @Override
                public void call() {
                    view.onSearchDone(userCardList);
                }
            });
        }

    }

    @Override
    public void onDataNotAvailable(final int strRes) {
        final SearchContract.UserView view = getView();
        if(view!=null){
            Run.onUiAsync(new Action() {
                @Override
                public void call() {
                    view.showError(strRes);
                }
            });
        }
    }
}
