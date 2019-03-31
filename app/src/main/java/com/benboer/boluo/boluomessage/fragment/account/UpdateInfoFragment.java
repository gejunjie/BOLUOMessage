package com.benboer.boluo.boluomessage.fragment.account;

import android.view.View;

import com.benboer.boluo.boluomessage.R;
import com.benboer.boluo.boluomessage.fragment.media.GalleryFragment;
import com.benboer.boluo.common.app.BaseFragment;
import com.benboer.boluo.common.widget.PortraitView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 用户更新界面
 *
 * Created by BenBoerBoluojiushiwo on 2019/3/28.
 */
public class UpdateInfoFragment extends BaseFragment {
    @BindView(R.id.im_portrait)
    PortraitView mPortraitView;

    public UpdateInfoFragment(){

    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_update_info;
    }

    @OnClick(R.id.im_portrait)
    void onPortraitViewClick(){
        new GalleryFragment().setOnSelectedListener(new GalleryFragment.OnSelectedListener() {
            @Override
            public void onSelectedImage(String path) {

            }
        }).show(getChildFragmentManager(), GalleryFragment.class.getName());
    }

}
