package com.benboer.boluo.boluomessage.activity;

import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benboer.boluo.boluomessage.R;
import com.benboer.boluo.common.app.PresenterToolbarActivity;
import com.benboer.boluo.common.widget.PortraitView;
import com.benboer.boluo.common.widget.recycler.RecyclerAdapter;
import com.benboer.boluo.factory.presenter.group.GroupCreateContract;
import com.benboer.boluo.factory.presenter.group.GroupCreatePresenter;

import net.qiujuer.genius.ui.widget.EditText;

import butterknife.BindView;

/**
 * @ClassName: GroupCreateActivity
 * @Description: 群组界面
 * @Author:  BenBoerBoluojiushiwo
 * @CreateDate: 2019-06-09 13:04
 * @Version: 1.0
 */
public class GroupCreateActivity extends PresenterToolbarActivity<GroupCreateContract.Presenter>
        implements GroupCreateContract.View{
    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.edit_desc)
    EditText mDesc;
    @BindView(R.id.edit_name)
    EditText mName;
    @BindView(R.id.im_portrait)
    PortraitView mPortrait;
    private String mPortraitPath;

    private RecyclerAdapter<GroupCreateContract.ViewModel> mAdapter;

    public static void show(Context context){
        context.startActivity(new Intent(context, GroupCreateActivity.class));
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_group_create;
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.start();
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitle("");
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.group_create, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_create){
            onCreateClick();
        }
        return super.onOptionsItemSelected(item);
    }

    private void onCreateClick() {
        hideSoftKeyboard();
        String name = mName.getText().toString().trim();
        String desc = mDesc.getText().toString().trim();
        mPresenter.create(name, desc, mPortraitPath);
    }

    // 隐藏软件盘
    private void hideSoftKeyboard() {
        // 当前焦点的View
        View view = getCurrentFocus();
        if (view == null)
            return;

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    @Override
    protected GroupCreateContract.Presenter initPresenter() {
        return new GroupCreatePresenter(this);
    }

    @Override
    public void onCreateSucceed() {

    }

    @Override
    public RecyclerAdapter<GroupCreateContract.ViewModel> getRecyclerAdapter() {
        return mAdapter;
    }

    @Override
    public void onAdapterDataChanged() {

    }

    private class Adapter extends RecyclerAdapter<GroupCreateContract.ViewModel>{

        @Override
        protected int getItemViewType(int postion, GroupCreateContract.ViewModel data) {
            return R.layout.cell_group_create_contact;
        }

        @Override
        protected ViewHolder<GroupCreateContract.ViewModel> onCreateViewHolder(View root, int viewType) {
            return new GroupCreateActivity.ViewHolder(root);
        }
    }

    class ViewHolder extends RecyclerAdapter.ViewHolder<GroupCreateContract.ViewModel>{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(GroupCreateContract.ViewModel data) {

        }
    }
}
