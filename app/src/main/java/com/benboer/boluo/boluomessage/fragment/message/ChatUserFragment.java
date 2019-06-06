package com.benboer.boluo.boluomessage.fragment.message;

import com.benboer.boluo.boluomessage.R;
import com.benboer.boluo.common.widget.recycler.RecyclerAdapter;
import com.benboer.boluo.factory.model.db.Message;
import com.benboer.boluo.factory.model.db.User;
import com.benboer.boluo.factory.presenter.message.ChatContract;
import com.benboer.boluo.factory.presenter.message.ChatUserPresenter;
import com.google.android.material.appbar.AppBarLayout;

/**
 * Created by BenBoerBoluojiushiwo on 2019/6/6.
 */
public class ChatUserFragment extends ChatFragment<User> implements ChatContract.UserView {


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_chat_user;
    }

    @Override
    public void onInit(User user) {

    }

    @Override
    protected ChatContract.Presenter initPresenter() {
        return new ChatUserPresenter(this, mReceiverId);
    }

    @Override
    public void setPresenter(ChatContract.Presenter presenter) {

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
