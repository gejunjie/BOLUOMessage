package com.benboer.boluo.boluomessage.fragment.user;

import android.content.Intent;
import android.widget.EditText;
import android.widget.ImageView;

import com.benboer.boluo.boluomessage.R;
import com.benboer.boluo.boluomessage.activity.MainActivity;
import com.benboer.boluo.common.app.PresenterFragment;
import com.benboer.boluo.common.widget.PortraitView;
import com.benboer.boluo.factory.presenter.user.UpdateInfoContract;
import com.benboer.boluo.factory.presenter.user.UpdateInfoPresenter;

import net.qiujuer.genius.ui.widget.Button;
import net.qiujuer.genius.ui.widget.Loading;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 用户更新界面
 *
 * Created by BenBoerBoluojiushiwo on 2019/3/28.
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


    }

    @OnClick(R.id.btn_submit)
    void onSubmitClick() {
        String desc = mDesc.getText().toString();
        mPresenter.update(mPortraitPath, desc, isMan);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected UpdateInfoContract.Presenter initPresenter() {
        return new UpdateInfoPresenter(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_update_info;
    }

    @Override
    public void updateSucceed() {
        MainActivity.show(getContext());
        getActivity().finish();
    }
}
