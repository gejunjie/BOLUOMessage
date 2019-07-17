package com.benboer.boluo.message.fragment.main;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benboer.boluo.core.ui.recycler.RecyclerAdapter;
import com.benboer.boluo.factory.R;
import com.benboer.boluo.factory.R2;
import com.benboer.boluo.lib_db.db.User;
import com.benboer.boluo.message.fragment.message.ChatUserFragment;
import com.benboer.boluo.message.fragment.user.PersonalFragment;
import com.benboer.boluo.message.presenter.contact.ContactContract;
import com.benboer.boluo.message.presenter.contact.ContactPresenter;
import com.benboer.boluo.message.widget.EmptyView;
import com.benboer.boluo.message.widget.PortraitView;
import com.benboer.boluo.module_common.mvp.PresenterFragment;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.OnClick;

;

/**
 * Created by BenBoerBoluojiushiwo on 2019/3/28.
 *
 * 联系人列表界面
 */
public class ContactFragment extends PresenterFragment<ContactContract.Presenter>
        implements ContactContract.View{
    @BindView(R2.id.recycler)
    RecyclerView recyclerView;

    @BindView(R2.id.empty)
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

        @BindView(R2.id.im_portrait)
        PortraitView mPortraitView;

        @BindView(R2.id.txt_name)
        TextView mName;

        @BindView(R2.id.txt_desc)
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

        @OnClick(R2.id.im_portrait)
        void onPortraitClick() {
            getParentFragments().getSupportDelegate().start(PersonalFragment.newInstance(data.getId()));
        }
    }
}
