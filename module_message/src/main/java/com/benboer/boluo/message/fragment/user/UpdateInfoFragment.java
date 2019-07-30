package com.benboer.boluo.message.fragment.user;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.benboer.boluo.common.mvp.PresenterFragment;
import com.benboer.boluo.common.util.callback.CallbackManager;
import com.benboer.boluo.common.util.callback.CallbackType;
import com.benboer.boluo.common.util.callback.IGlobalCallback;
import com.benboer.boluo.message.R;
import com.benboer.boluo.message.R2;
import com.benboer.boluo.message.presenter.user.UpdateInfoContract;
import com.benboer.boluo.message.presenter.user.UpdateInfoPresenter;
import com.benboer.boluo.message.widget.PortraitView;
import com.bumptech.glide.Glide;

import net.qiujuer.genius.ui.widget.Button;
import net.qiujuer.genius.ui.widget.Loading;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by BenBoerBoluojiushiwo on 2019/3/28.
 *
 *  用户更新界面
 */
public class UpdateInfoFragment extends PresenterFragment<UpdateInfoContract.Presenter>
    implements UpdateInfoContract.View{

    @BindView(R2.id.im_sex)
    ImageView mSex;

    @BindView(R2.id.edit_desc)
    EditText mDesc;

    @BindView(R2.id.im_portrait)
    PortraitView mPortrait;

    @BindView(R2.id.loading)
    Loading mLoading;

    @BindView(R2.id.btn_submit)
    Button mSubmit;

    // 头像的本地路径
    private String mPortraitPath;
    private boolean isMan = true;

    public UpdateInfoFragment(){

    }

    @OnClick(R2.id.im_portrait)
    void onPortraitClick() {
        CallbackManager.getInstance().addCallback(CallbackType.ON_CROP, new IGlobalCallback<Uri>() {

            @Override
            public void executeCallback(@NonNull Uri uri) {
                if (uri != null) {
                    loadPortrait(uri);
                }
            }
        });
        startCameraWithCheck();
    }

    @OnClick(R2.id.btn_submit)
    void onSubmitClick() {
        String desc = mDesc.getText().toString();
        mPresenter.update(mPortraitPath, desc, isMan);
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
