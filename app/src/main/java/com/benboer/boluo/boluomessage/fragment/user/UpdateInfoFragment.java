package com.benboer.boluo.boluomessage.fragment.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.benboer.boluo.boluomessage.App;
import com.benboer.boluo.boluomessage.R;
import com.benboer.boluo.boluomessage.fragment.media.GalleryFragment;
import com.benboer.boluo.common.app.Application;
import com.benboer.boluo.core.fragment.PresenterFragment;
import com.benboer.boluo.factory.presenter.user.UpdateInfoContract;
import com.benboer.boluo.factory.presenter.user.UpdateInfoPresenter;
import com.benboer.boluo.widget.PortraitView;
import com.bumptech.glide.Glide;
import com.yalantis.ucrop.UCrop;

import net.qiujuer.genius.ui.widget.Button;
import net.qiujuer.genius.ui.widget.Loading;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by BenBoerBoluojiushiwo on 2019/3/28.
 *
 *  用户更新界面
 */
public class UpdateInfoFragment extends PresenterFragment<UpdateInfoContract.Presenter>
    implements UpdateInfoContract.View{

    @BindView(R.id.im_sex)
    ImageView mSex;

    @BindView(R.id.edit_desc)
    EditText mDesc;

    @BindView(R.id.im_portrait)
    PortraitView mPortrait;

    @BindView(R.id.loading)
    Loading mLoading;

    @BindView(R.id.btn_submit)
    Button mSubmit;

    // 头像的本地路径
    private String mPortraitPath;
    private boolean isMan = true;

    public UpdateInfoFragment(){

    }

    @OnClick(R.id.im_portrait)
    void onPortraitClick() {
        new GalleryFragment().setOnSelectedListener(new GalleryFragment.OnSelectedListener() {
            @Override
            public void onSelectedImage(String path) {
                UCrop.Options options = new UCrop.Options();
                options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
                options.setCompressionQuality(96);
                File dPath = App.getPortraitTmpFile();
                // 发起剪切
                UCrop.of(Uri.fromFile(new File(path)), Uri.fromFile(dPath))
                        .withAspectRatio(1, 1) // 1比1比例
                        .withMaxResultSize(520, 520) // 返回最大的尺寸
                        .withOptions(options) // 相关参数
                        .start(getActivity());
            }
        }).show(getChildFragmentManager(), GalleryFragment.class.getName());
    }

    @OnClick(R.id.btn_submit)
    void onSubmitClick() {
        String desc = mDesc.getText().toString();
        mPresenter.update(mPortraitPath, desc, isMan);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP){
            // 通过UCrop得到对应的Uri
            final Uri resultUri = UCrop.getOutput(data);
            if (resultUri != null) {
                loadPortrait(resultUri);
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            Application.showToast(R.string.data_rsp_error_unknown);
        }
    }

    /**
     * 加载Uri到当前的头像中
     *
     * @param uri Uri
     */
    private void loadPortrait(Uri uri) {
        // 得到头像地址
        mPortraitPath = uri.getPath();

        Glide.with(this)
                .load(uri)
                .centerCrop()
                .into(mPortrait);
    }

    @Override
    protected UpdateInfoContract.Presenter initPresenter() {
        return new UpdateInfoPresenter(this);
    }

    @Override
    public void updateSucceed() {
//        MainActivity.show(getContext());
//        getActivity().finish();
        getSupportDelegate().pop();
    }

    @Override
    public void showLoading() {
        super.showLoading();
        // 正在进行时，正在进行注册，界面不可操作
        // 开始Loading
        mLoading.start();
        // 让控件不可以输入
        mDesc.setEnabled(false);
        mPortrait.setEnabled(false);
        mSex.setEnabled(false);
        // 提交按钮不可以继续点击
        mSubmit.setEnabled(false);
    }

    @Override
    public void showError(int str) {
        super.showError(str);
        // 当需要显示错误的时候触发，一定是结束了

        // 停止Loading
        mLoading.stop();
        mDesc.setEnabled(true);
        mPortrait.setEnabled(true);
        mSex.setEnabled(true);
        mSubmit.setEnabled(true);
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_update_info;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View root) {

    }
}
