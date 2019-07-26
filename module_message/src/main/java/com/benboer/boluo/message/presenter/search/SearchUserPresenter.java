package com.benboer.boluo.message.presenter.search;

import com.benboer.boluo.common.mvp.data.DataSource;
import com.benboer.boluo.common.mvp.presenter.BasePresenter;
import com.benboer.boluo.common.util.HandlerUtil;
import com.benboer.boluo.message.data.helper.UserHelper;
import com.benboer.boluo.message.model.card.UserCard;

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
            HandlerUtil.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    view.onSearchDone(userCardList);
                }
            });
        }

    }

    @Override
    public void onDataNotAvailable(final int strRes) {
        final SearchContract.UserView view = getView();
        if(view!=null){
            HandlerUtil.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    view.showError(strRes);
                }
            });
        }
    }
}
