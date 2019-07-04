package com.benboer.boluo.boluomessage.fragment.main;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benboer.boluo.boluomessage.R;
import com.benboer.boluo.boluomessage.fragment.user.PersonalFragment;
import com.benboer.boluo.boluomessage.fragment.message.ChatUserFragment;
import com.benboer.boluo.core.fragment.PresenterFragment;
import com.benboer.boluo.widget.EmptyView;
import com.benboer.boluo.widget.PortraitView;
import com.benboer.boluo.widget.recycler.RecyclerAdapter;
import com.benboer.boluo.factory.model.db.User;
import com.benboer.boluo.factory.presenter.contact.ContactContract;
import com.benboer.boluo.factory.presenter.contact.ContactPresenter;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by BenBoerBoluojiushiwo on 2019/3/28.
 *
 * 联系人列表界面
 */
public class ContactFragment extends PresenterFragment<ContactContract.Presenter>
        implements ContactContract.View{
    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    @BindView(R.id.empty)
    EmptyView emptyView;

    private RecyclerAdapter<User> mAdapter;

    public ContactFragment() {

    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_contact;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View root) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter = new RecyclerAdapter() {
            @Override
            protected int getItemViewType(int postion, Object data) {
                return R.layout.cell_contact_list;
            }

            @Override
            protected ViewHolder onCreateViewHolder(View root, int viewType) {
                return new ContactFragment.ViewHolder(root);
            }
        });

        mAdapter.setAdapterListener(new RecyclerAdapter.AdapterListenerImpl<User>() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder holder, User user) {
                getParentFragments().getSupportDelegate().start(ChatUserFragment.newInstance(user));
            }
        });
        // 初始化占位布局
        emptyView.bind(recyclerView);
        setPlaceHolderView(emptyView);
    }

    @Override
    protected void onFirstInit() {
        super.onFirstInit();
        // 进行一次数据加载
        mPresenter.start();
    }

    @Override
    protected ContactContract.Presenter initPresenter() {
        return new ContactPresenter(this);
    }

    @Override
    public RecyclerAdapter<User> getRecyclerAdapter() {
        return mAdapter;
    }

    @Override
    public void onAdapterDataChanged() {
        mPlaceHolderView.triggerOkOrEmpty(mAdapter.getItemCount() > 0);
    }

    class ViewHolder extends RecyclerAdapter.ViewHolder<User>{

        @BindView(R.id.im_portrait)
        PortraitView mPortraitView;

        @BindView(R.id.txt_name)
        TextView mName;

        @BindView(R.id.txt_desc)
        TextView mDesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(User data) {
            mPortraitView.setup(Glide.with(ContactFragment.this), data);
            mName.setText(data.getName());
            mDesc.setText(data.getDesc());
        }

        @OnClick(R.id.im_portrait)
        void onPortraitClick() {
            getParentFragments().getSupportDelegate().start(PersonalFragment.newInstance(data.getId()));
        }
    }
}
