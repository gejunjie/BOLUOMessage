package com.benboer.boluo.boluomessage.fragment.message;

import com.benboer.boluo.boluomessage.R;
import com.benboer.boluo.common.widget.recycler.RecyclerAdapter;
import com.benboer.boluo.factory.model.db.Group;
import com.benboer.boluo.factory.model.db.Message;
import com.benboer.boluo.factory.presenter.message.ChatContract;
import com.google.android.material.appbar.AppBarLayout;

/**
 * Created by BenBoerBoluojiushiwo on 2019/6/6.
 */
public class ChatGroupFragment extends ChatFragment<Group>
        implements ChatContract.GroupView {

    @Override
    protected ChatContract.Presenter initPresenter() {
        return null;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_chat_group;
    }

    @Override
    public void onInit(Group group) {

    }

    @Override
    public RecyclerAdapter<Message> getRecyclerAdapter() {
        return null;
    }

    @Override
    public void onAdapterDataChanged() {

    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {

    }
}
