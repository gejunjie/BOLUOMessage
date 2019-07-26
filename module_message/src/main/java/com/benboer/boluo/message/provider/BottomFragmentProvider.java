package com.benboer.boluo.message.provider;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.benboer.boluo.common.provider.IBottomFragmentProvider;
import com.benboer.boluo.message.fragment.main.BottomFragment;

@Route(path = "/message/bottomfragment")
public class BottomFragmentProvider implements IBottomFragmentProvider {

    @Override
    public Fragment getMainFragment() {
        return new BottomFragment();

    }

    @Override
    public void init(Context context) {

    }
}
