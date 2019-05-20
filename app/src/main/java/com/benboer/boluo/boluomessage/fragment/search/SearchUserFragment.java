package com.benboer.boluo.boluomessage.fragment.search;

import androidx.recyclerview.widget.RecyclerView;

import com.benboer.boluo.boluomessage.R;
import com.benboer.boluo.boluomessage.activity.SearchActivity;
import com.benboer.boluo.common.app.PresenterFragment;
import com.benboer.boluo.factory.model.card.UserCard;
import com.benboer.boluo.factory.presenter.search.SearchContract;
import com.benboer.boluo.factory.presenter.search.SearchUserPresenter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/20.
 */
public class SearchUserFragment extends PresenterFragment<SearchContract.Presenter>
        implements SearchContract.UserView, SearchActivity.SearchFragment {
    @BindView(R.id.recycler)
    private RecyclerView recyclerView;

    @Override
    public void search(String content) {

    }

    @Override
    protected SearchContract.Presenter initPresenter() {
        return new SearchUserPresenter(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_search_user;
    }

    @Override
    public void onSearchDone(List<UserCard> userCards) {

    }
}
