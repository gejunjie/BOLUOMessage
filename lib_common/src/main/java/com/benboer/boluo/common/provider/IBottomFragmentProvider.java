package com.benboer.boluo.common.provider;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * Created by BenBoerBoluojiushiwo on 2019/7/25.
 */
public interface IBottomFragmentProvider extends IProvider {
    Fragment getMainFragment();
}
