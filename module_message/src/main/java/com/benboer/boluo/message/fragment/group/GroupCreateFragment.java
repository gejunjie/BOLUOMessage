package com.benboer.boluo.message.fragment.group;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benboer.boluo.common.comm_ui.recycler.RecyclerAdapter;
import com.benboer.boluo.common.mvp.ToolbarPresenterFragment;
import com.benboer.boluo.common.util.callback.CallbackManager;
import com.benboer.boluo.common.util.callback.CallbackType;
import com.benboer.boluo.common.util.callback.IGlobalCallback;
import com.benboer.boluo.message.R;
import com.benboer.boluo.message.R2;
import com.benboer.boluo.message.presenter.group.GroupCreateContract;
import com.benboer.boluo.message.presenter.group.GroupCreatePresenter;
import com.benboer.boluo.message.widget.PortraitView;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;

import net.qiujuer.genius.ui.widget.EditText;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by BenBoerBoluojiushiwo on 2019/7/4.
 *
 * 群组创建界面
 */
public class GroupCreateFragment extends ToolbarPresenterFragment<GroupCreateContract.Presenter>
        implements GroupCreateContract.View{

    @BindView(R2.id.recycler)
    RecyclerView mRecycler;
    @BindView(R2.id.edit_desc)
    EditText mDesc;
    @BindView(R2.id.edit_name)
    EditText mName;
    @BindView(R2.id.im_portrait)
    PortraitView mPortrait;
    private String mPortraitPath;

    private RecyclerAdapter<GroupCreateContract.ViewModel> mAdapter;

    @Override
    protected GroupCreateContract.Presenter initPresenter() {
        return new GroupCreatePresenter(this);
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_group_create;
    }

    @Override
    protected void onFirstInit() {
        super.onFirstInit();
        mPresenter.start();
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View root) {
        super.onBindView(savedInstanceState,root);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.setAdapter(mAdapter = new GroupCreateFragment.Adapter());
    }

    @Override
    public void onCreateSucceed() {
        hideLoading();
        ToastUtils.showLong(R.string.label_group_create_succeed);
        pop();
    }

    @Override
    public void initToolbar(Toolbar toolbar) {
        super.initToolbar(toolbar);
        mToolbar.inflateMenu(R.menu.group_create);
        mToolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_create) {
                hideSoftKeyboard();
                String name = mName.getText().toString().trim();
                String desc = mDesc.getText().toString().trim();
                mPresenter.create(name, desc, mPortraitPath);
            }
            return false;
        });
    }

    @Override
    public RecyclerAdapter<GroupCreateContract.ViewModel> getRecyclerAdapter() {
        return mAdapter;
    }

    @Override
    public void onAdapterDataChanged() {
        hideLoading();
    }

    @OnClick(R2.id.im_portrait)
    void onPortraitClick() {
        CallbackManager.getInstance().addCallback(CallbackType.ON_CROP,
                (IGlobalCallback<Uri>) uri -> {
            if (uri != null) {
                loadPortrait(uri);
            }
        });
        startCameraWithCheck();
    }

    private void loadPortrait(Uri uri) {
        mPortraitPath = uri.getPath();
        Glide.with(this)
                .load(uri)
                .centerCrop()
                .into(mPortrait);
    }

    private void hideSoftKeyboard() {
        // 当前焦点的View
        View view = getActivity().getCurrentFocus();
        if (view == null) return;

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private class Adapter extends RecyclerAdapter<GroupCreateContract.ViewModel>{

        @Override
        protected int getItemViewType(int postion, GroupCreateContract.ViewModel data) {
            return R.layout.cell_group_create_contact;
        }

        @Override
        protected ViewHolder<GroupCreateContract.ViewModel> onCreateViewHolder(View root, int viewType) {
            return new GroupCreateFragment.ViewHolder(root);
        }
    }

    class ViewHolder extends RecyclerAdapter.ViewHolder<GroupCreateContract.ViewModel>{

        @BindView(R2.id.im_portrait)
        PortraitView mPortrait;
        @BindView(R2.id.txt_name)
        TextView mName;
        @BindView(R2.id.cb_select)
        CheckBox mSelect;
        ViewHolder(View itemView) {
            super(itemView);
        }

        @OnCheckedChanged(R2.id.cb_select)
        void onCheckedChanged(boolean checked) {
            // 进行状态更改
            mPresenter.changeSelect(data, checked);
        }

        @Override
        protected void onBind(GroupCreateContract.ViewModel viewModel) {
            mPortrait.setup(Glide.with(GroupCreateFragment.this), viewModel.author);
            mName.setText(viewModel.author.getName());
            mSelect.setChecked(viewModel.isSelected);
        }
    }
}
