package com.benboer.boluo.boluomessage.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benboer.boluo.boluomessage.R;
import com.benboer.boluo.factory.fragment.media.GalleryFragment;
import com.benboer.boluo.common.app.Application;
import com.benboer.boluo.common.app.PresenterToolbarActivity;
import com.benboer.boluo.widget.PortraitView;
import com.benboer.boluo.widget.recycler.RecyclerAdapter;
import com.benboer.boluo.factory.presenter.group.GroupCreateContract;
import com.benboer.boluo.factory.presenter.group.GroupCreatePresenter;
import com.bumptech.glide.Glide;
import com.yalantis.ucrop.UCrop;

import net.qiujuer.genius.ui.widget.EditText;

import java.io.File;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * @ClassName: GroupCreateActivity
 * @Description: 群组创建界面
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

//    public static void show(Context context){
//        context.startActivity(new Intent(context, GroupCreateActivity.class));
//    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_group_create;
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
        mRecycler.setAdapter(mAdapter = new Adapter());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.group_create, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @OnClick(R.id.im_portrait)
    void onPortraitClick() {
        hideSoftKeyboard();
        new GalleryFragment()
                .setOnSelectedListener(new GalleryFragment.OnSelectedListener() {
                    @Override
                    public void onSelectedImage(String path) {
                        UCrop.Options options = new UCrop.Options();
                        // 设置图片处理的格式JPEG
                        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
                        // 设置压缩后的图片精度
                        options.setCompressionQuality(96);

                        // 得到头像的缓存地址
                        File dPath = Application.getPortraitTmpFile();

                        // 发起剪切
                        UCrop.of(Uri.fromFile(new File(path)), Uri.fromFile(dPath))
                                .withAspectRatio(1, 1) // 1比1比例
                                .withMaxResultSize(520, 520) // 返回最大的尺寸
                                .withOptions(options) // 相关参数
                                .start(GroupCreateActivity.this);
                    }
                }).show(getSupportFragmentManager(), GalleryFragment.class.getName());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 收到从Activity传递过来的回调，然后取出其中的值进行图片加载
        // 如果是我能够处理的类型
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            // 通过UCrop得到对应的Uri
            final Uri resultUri = UCrop.getOutput(data);
            if (resultUri != null) {
                loadPortrait(resultUri);
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            Application.showToast(R.string.data_rsp_error_unknown);
        }
    }

    private void loadPortrait(Uri uri) {
        // 得到头像地址
        mPortraitPath = uri.getPath();

        Glide.with(this)
                .load(uri)
                .centerCrop()
                .into(mPortrait);
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
        if (view == null) return;

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    protected GroupCreateContract.Presenter initPresenter() {
        return new GroupCreatePresenter(this);
    }

    @Override
    public void onCreateSucceed() {
        hideLoading();
        Application.showToast(R.string.label_group_create_succeed);
        finish();
    }

    @Override
    public RecyclerAdapter<GroupCreateContract.ViewModel> getRecyclerAdapter() {
        return mAdapter;
    }

    @Override
    public void onAdapterDataChanged() {
        hideLoading();
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

        @BindView(R.id.im_portrait)
        PortraitView mPortrait;
        @BindView(R.id.txt_name)
        TextView mName;
        @BindView(R.id.cb_select)
        CheckBox mSelect;
        ViewHolder(View itemView) {
            super(itemView);
        }

        @OnCheckedChanged(R.id.cb_select)
        void onCheckedChanged(boolean checked) {
            // 进行状态更改
            mPresenter.changeSelect(data, checked);
        }

        @Override
        protected void onBind(GroupCreateContract.ViewModel viewModel) {
            mPortrait.setup(Glide.with(GroupCreateActivity.this), viewModel.author);
            mName.setText(viewModel.author.getName());
            mSelect.setChecked(viewModel.isSelected);
        }
    }
}
