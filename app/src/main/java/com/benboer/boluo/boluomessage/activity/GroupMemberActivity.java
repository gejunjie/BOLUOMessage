package com.benboer.boluo.boluomessage.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.recyclerview.widget.RecyclerView;

import com.benboer.boluo.boluomessage.R;
import com.benboer.boluo.common.app.PresenterToolbarActivity;
import com.benboer.boluo.common.widget.recycler.RecyclerAdapter;
import com.benboer.boluo.factory.model.db.view.MemberUserModel;
import com.benboer.boluo.factory.presenter.group.GroupMembersContract;
import com.benboer.boluo.factory.presenter.group.GroupMembersPresenter;

import butterknife.BindView;

/**
 * Created by BenBoerBoluojiushiwo on 2019/6/14.
 */
public class GroupMemberActivity extends PresenterToolbarActivity<GroupMembersContract.Presenter>
        implements GroupMembersContract.View {

    private static final String KEY_GROUP_ID = "KEY_GROUP_ID";
    private static final String KEY_GROUP_ADMIN = "KEY_GROUP_ADMIN";

    @BindView(R.id.recycler)
    RecyclerView mRecycler;

    private String mGroupId;
    private boolean mIsAdmin;
    private RecyclerAdapter<MemberUserModel> mAdapter;

    public static void show(Context context, String groupId) {
        show(context, groupId, false);
    }

    public static void showAdmin(Context context, String groupId) {
        show(context, groupId, true);
    }

    private static void show(Context context, String groupId, boolean isAdmin) {
        if (TextUtils.isEmpty(groupId))
            return;

        Intent intent = new Intent(context, GroupMemberActivity.class);
        intent.putExtra(KEY_GROUP_ID, groupId);
        intent.putExtra(KEY_GROUP_ADMIN, isAdmin);
        context.startActivity(intent);
    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        mGroupId = bundle.getString(KEY_GROUP_ID);
        mIsAdmin = bundle.getBoolean(KEY_GROUP_ADMIN);
        return !TextUtils.isEmpty(mGroupId);
    }

    @Override
    protected GroupMembersContract.Presenter initPresenter() {
        return new GroupMembersPresenter(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_group_member;
    }

    @Override
    public String getGroupId() {
        return null;
    }

    @Override
    public RecyclerAdapter<MemberUserModel> getRecyclerAdapter() {
        return null;
    }

    @Override
    public void onAdapterDataChanged() {

    }
}
