package com.benboer.boluo.message.fragment.group;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benboer.boluo.common.Application;
import com.benboer.boluo.common.app.BoLuo;
import com.benboer.boluo.common.ui.recycler.RecyclerAdapter;
import com.benboer.boluo.common.util.file.FileUtil;
import com.benboer.boluo.factory.R;
import com.benboer.boluo.factory.R2;
import com.benboer.boluo.message.fragment.media.GalleryFragment;
import com.benboer.boluo.message.presenter.group.GroupCreateContract;
import com.benboer.boluo.message.presenter.group.GroupCreatePresenter;
import com.benboer.boluo.message.widget.PortraitView;
import com.benboer.boluo.common.mvp.PresenterFragment;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.yalantis.ucrop.UCrop;

import net.qiujuer.genius.ui.widget.EditText;

import java.io.File;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by BenBoerBoluojiushiwo on 2019/7/4.
 */
public class GroupCreateFragment extends PresenterFragment<GroupCreateContract.Presenter>
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.start();
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View root) {
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
    public RecyclerAdapter<GroupCreateContract.ViewModel> getRecyclerAdapter() {
        return mAdapter;
    }

    @Override
    public void onAdapterDataChanged() {
        hideLoading();
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
            ToastUtils.showLong(R.string.data_rsp_error_unknown);
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

    @OnClick(R2.id.im_portrait)
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
                        File dPath = FileUtil.getPortraitTmpFile(BoLuo.getApplicationContext());
                        // 发起剪切
                        UCrop.of(Uri.fromFile(new File(path)), Uri.fromFile(dPath))
                                .withAspectRatio(1, 1) // 1比1比例
                                .withMaxResultSize(520, 520) // 返回最大的尺寸
                                .withOptions(options) // 相关参数
                                .start(getProxyActivity());
                    }
                }).show(getFragmentManager(), GalleryFragment.class.getName());
    }

    // 隐藏软件盘
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
