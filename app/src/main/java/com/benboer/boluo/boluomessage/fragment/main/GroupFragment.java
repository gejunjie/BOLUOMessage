package com.benboer.boluo.boluomessage.fragment.main;

import androidx.recyclerview.widget.RecyclerView;

import com.benboer.boluo.boluomessage.R;
import com.benboer.boluo.common.app.PresenterFragment;
import com.benboer.boluo.common.widget.EmptyView;
import com.benboer.boluo.common.widget.recycler.RecyclerAdapter;
import com.benboer.boluo.factory.model.db.Group;
import com.benboer.boluo.factory.presenter.group.GroupsContract;
import com.benboer.boluo.factory.presenter.group.GroupsPresenter;

import butterknife.BindView;

/**
 * Created by BenBoerBoluojiushiwo on 2019/3/28.
 */
public class GroupFragment extends PresenterFragment<GroupsContract.Presenter>
        implements GroupsContract.View{

    @BindView(R.id.empty)
    EmptyView mEmptyView;

    @BindView(R.id.recycler)
    RecyclerView mRecycler;

    @Override
    protected GroupsContract.Presenter initPresenter() {
        return new GroupsPresenter(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_group;
    }

    @Override
    public RecyclerAdapter<Group> getRecyclerAdapter() {
        return null;
    }

    @Override
    public void onAdapterDataChanged() {

    }
}
