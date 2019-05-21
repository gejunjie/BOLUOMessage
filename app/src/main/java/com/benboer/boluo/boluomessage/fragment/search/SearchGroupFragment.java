package com.benboer.boluo.boluomessage.fragment.search;

import com.benboer.boluo.boluomessage.activity.SearchActivity;
import com.benboer.boluo.common.app.PresenterFragment;
import com.benboer.boluo.factory.presenter.BaseContract;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/21.
 */
public class SearchGroupFragment extends PresenterFragment
        implements SearchActivity.SearchCallback {
    @Override
    protected BaseContract.Presenter initPresenter() {
        return null;
    }

    @Override
    protected int getContentLayoutId() {
        return 0;
    }

    @Override
    public void search(String content) {

    }
}
