package com.benboer.boluo.boluomessage.fragment.main;

import android.view.View;

import com.benboer.boluo.boluomessage.R;
import com.benboer.boluo.common.app.BaseFragment;
import com.benboer.boluo.common.widget.recycler.GalleyView;

import butterknife.BindView;

/**
 * Created by BenBoerBoluojiushiwo on 2019/3/28.
 */
public class ActiveFragment extends BaseFragment {
//    @BindView(R.id.galleryView)
//    GalleyView galleyView;

    public ActiveFragment(){

    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_active;
    }

    @Override
    protected void initData() {
        super.initData();
//        galleyView.setUp(getLoaderManager(), new GalleyView.SelectedChangeListener() {
//            @Override
//            public void onSelectedCountChanged(int count) {
//
//            }
//        });
    }
}
