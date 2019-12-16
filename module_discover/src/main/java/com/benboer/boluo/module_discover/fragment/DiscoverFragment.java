package com.benboer.boluo.module_discover.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.benboer.boluo.common.base.fragment.SupportFragment;
import com.benboer.boluo.common.comm_ui.recyclerview.wrapper.LoadMoreWrapper;
import com.benboer.boluo.module_discover.R;

/**
 * 发现页面
 */
@Route(path = "/discover/discoverFragment")
public class DiscoverFragment extends SupportFragment
        implements SwipeRefreshLayout.OnRefreshListener, LoadMoreWrapper.OnLoadMoreListener {

    public DiscoverFragment() {
        ARouter.getInstance().inject(this);
    }

    @Override
    protected void onFirstInit() {

    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_discover;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View root) {

    }

    @Override
    public void onLoadMoreRequested() {

    }

    @Override
    public void onRefresh() {

    }
}
