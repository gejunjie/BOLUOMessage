package com.benboer.boluo.message.fragment.group;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benboer.boluo.common.ui.recycler.RecyclerAdapter;
import com.benboer.boluo.message.R;
import com.benboer.boluo.message.R2;
import com.benboer.boluo.message.db.view.MemberUserModel;
import com.benboer.boluo.message.fragment.user.PersonalFragment;
import com.benboer.boluo.message.presenter.group.GroupMembersContract;
import com.benboer.boluo.message.presenter.group.GroupMembersPresenter;
import com.benboer.boluo.message.widget.PortraitView;
import com.benboer.boluo.common.mvp.PresenterFragment;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by BenBoerBoluojiushiwo on 2019/7/3.
 */
public class GroupMemberFragment extends PresenterFragment<GroupMembersContract.Presenter>
        implements GroupMembersContract.View, GroupMemberAddFragment.Callback {

    public static final String ARG_GROUP_ID = "GROUP_ID";
    public static final String ARG_ISADMIN = "IS_ADMIN";


    private String mGroupId;
    private boolean mIsAdmin;


    @BindView(R2.id.recycler)
    RecyclerView mRecycler;
    private RecyclerAdapter<MemberUserModel> mAdapter;

    public static GroupMemberFragment newInstance(String groupId, boolean isAdmin) {
        if (groupId == null) return null;
        final Bundle args = new Bundle();
        args.putString(ARG_GROUP_ID, groupId);
        args.putBoolean(ARG_ISADMIN, isAdmin);
        final GroupMemberFragment fragment = new GroupMemberFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        final Bundle args = getArguments();
        if (args != null) {
            mGroupId = args.getString(ARG_GROUP_ID);
            mIsAdmin =args.getBoolean(ARG_ISADMIN);
        }
        super.onAttach(context);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 开始数据刷新
        mPresenter.refresh();

        // 显示管理员界面，添加成员
        if (mIsAdmin) {
            new GroupMemberAddFragment()
                    .show(getFragmentManager(), GroupMemberAddFragment.class.getName());
        }
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
    }

    @Override
    public void refreshMembers() {
        if (mPresenter != null){
            mPresenter.refresh();
        }
    }

    @Override
    protected GroupMembersContract.Presenter initPresenter() {
        return new GroupMembersPresenter(this);
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_group_member;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View root) {
//        setTitle(R.string.title_member_list);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.setAdapter(mAdapter = new RecyclerAdapter<MemberUserModel>() {
            @Override
            protected int getItemViewType(int position, MemberUserModel memberUserModel) {
                return R.layout.cell_group_create_contact;
            }

            @Override
            protected ViewHolder<MemberUserModel> onCreateViewHolder(View root, int viewType) {
                return new GroupMemberFragment.ViewHolder(root);
            }
        });
    }

    @Override
    public String getGroupId() {
        return mGroupId;
    }

    @Override
    public RecyclerAdapter<MemberUserModel> getRecyclerAdapter() {
        return mAdapter;
    }

    @Override
    public void onAdapterDataChanged() {
        hideLoading();
    }
    class ViewHolder extends RecyclerAdapter.ViewHolder<MemberUserModel> {
        @BindView(R2.id.im_portrait)
        PortraitView mPortrait;
        @BindView(R2.id.txt_name)
        TextView mName;


        ViewHolder(View itemView) {
            super(itemView);
            itemView.findViewById(R.id.cb_select).setVisibility(View.GONE);
        }

        @Override
        protected void onBind(MemberUserModel model) {
            mPortrait.setup(Glide.with(GroupMemberFragment.this), model.portrait);
            mName.setText(model.name);
        }

        @OnClick(R2.id.im_portrait)
        void onPortraitClick() {
            getSupportDelegate().start(PersonalFragment.newInstance(data.userId));
        }
    }
}
