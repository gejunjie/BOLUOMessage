package com.benboer.boluo.boluomessage.fragment.main;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benboer.boluo.boluomessage.R;
import com.benboer.boluo.boluomessage.activity.MessageActivity;
import com.benboer.boluo.common.app.PresenterFragment;
import com.benboer.boluo.common.widget.EmptyView;
import com.benboer.boluo.common.widget.PortraitView;
import com.benboer.boluo.common.widget.recycler.RecyclerAdapter;
import com.benboer.boluo.factory.model.db.Group;
import com.benboer.boluo.factory.presenter.group.GroupsContract;
import com.benboer.boluo.factory.presenter.group.GroupsPresenter;
import com.bumptech.glide.Glide;

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

    private RecyclerAdapter<Group> mAdapter;

    public GroupFragment() {

    }

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
        return mAdapter;
    }

    @Override
    public void onAdapterDataChanged() {
        mPlaceHolderView.triggerOkOrEmpty(mAdapter.getItemCount() > 0);
    }

    @Override
    protected void onFirstInit() {
        super.onFirstInit();
        mPresenter.start();
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRecycler.setAdapter(mAdapter = new RecyclerAdapter<Group>() {
            @Override
            protected int getItemViewType(int postion, Group data) {
                return R.layout.cell_group_list;
            }

            @Override
            protected ViewHolder<Group> onCreateViewHolder(View root, int viewType) {
                return new GroupFragment.ViewHolder(root);
            }
        });

        mAdapter.setAdapterListener(new RecyclerAdapter.AdapterListenerImpl<Group>() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder holder, Group data) {
                super.onItemClick(holder, data);
                MessageActivity.show(getContext(), data);
            }
        });

        mEmptyView.bind(mRecycler);
        setPlaceHolderView(mEmptyView);
    }

    class ViewHolder extends RecyclerAdapter.ViewHolder<Group>{

        @BindView(R.id.im_portrait)
        PortraitView mPortraitView;

        @BindView(R.id.txt_name)
        TextView mName;

        @BindView(R.id.txt_desc)
        TextView mDesc;

        @BindView(R.id.txt_member)
        TextView mMember;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(Group group) {
            mPortraitView.setup(Glide.with(GroupFragment.this), group.getPicture());
            mName.setText(group.getName());
            mDesc.setText(group.getDesc());

            if (group.holder != null && group.holder instanceof String) {
                mMember.setText((String) group.holder);
            } else {
                mMember.setText("");
            }
        }
    }
}
