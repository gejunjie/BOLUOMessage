package com.benboer.boluo.message.presenter.search;

import com.benboer.boluo.common.mvp.data.DbDataSource;
import com.benboer.boluo.message.data.helper.GroupHelper;
import com.benboer.boluo.message.model.card.GroupCard;
import com.benboer.boluo.common.mvp.presenter.BasePresenter;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.List;

import retrofit2.Call;

/**
 * Created by BenBoerBoluojiushiwo on 2019/6/20.
 */
public class SearchGroupPresenter extends BasePresenter<SearchContract.GroupView>
        implements SearchContract.Presenter, DbDataSource.Callback<List<GroupCard>> {

    private Call searchCall;
    public SearchGroupPresenter(SearchContract.GroupView view) {
        super(view);
    }

    @Override
    public void onSearch(String content) {
        start();

        Call call = searchCall;
        if (call != null && !call.isCanceled()) {
            // 如果有上一次的请求，并且没有取消，
            // 则调用取消请求操作
            call.cancel();
        }

        searchCall = GroupHelper.groupSearch(content, this);
    }

    @Override
    public void onDataLoaded(final List<GroupCard> groupCards) {
        final SearchContract.GroupView view = getView();
        if (view != null){
            Run.onUiAsync(new Action() {
                @Override
                public void call() {
                    view.onSearchDone(groupCards);
                }
            });
        }
    }

    @Override
    public void onDataNotAvailable(final int strRes) {
        final SearchContract.GroupView view = getView();
        if (view != null){
            Run.onUiAsync(new Action() {
                @Override
                public void call() {
                    view.showError(strRes);
                }
            });
        }
    }


}
