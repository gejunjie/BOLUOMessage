package com.benboer.boluo.message.provider;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.benboer.boluo.common.provider.IMessageProvider;
import com.benboer.boluo.message.fragment.main.BottomFragment;

@Route(path = "/message/main",name = "")
public class BottomFragmentProvider implements IMessageProvider {

    @Override
    public Fragment getMainFragment() {
        return new BottomFragment();

    }

    @Override
    public void init(Context context) {

    }
}
