package com.benboer.boluo.module_discover;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.benboer.boluo.common.base.fragment.SupportFragment;

/**
 * Created by BenBoerBoluojiushiwo on 2019/8/26.
 */
@Route(path = "/discover/discoverFragment")
public class DiscoverFragment extends SupportFragment {
    @Override
    public Object setLayout() {
        return R.layout.fragment_discover;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View root) {

    }
}
