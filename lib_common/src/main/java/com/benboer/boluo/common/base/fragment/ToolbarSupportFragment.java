package com.benboer.boluo.common.base.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.benboer.boluo.common.R;

/**
 * @ClassName: ToolbarSupportFragment
 * @Description: 支持Toolbar的Fragment
 * @Author:  BenBoerBoluojiushiwo
 * @CreateDate: 2019-12-15 09:31
 * @Version: 1.0
 */
public abstract class ToolbarSupportFragment extends SupportFragment {

    protected Toolbar mToolbar;

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View root) {
        initToolbar(bind(R.id.toolbar));
    }

    /**
     * 初始化toolbar
     *
     * @param toolbar Toolbar
     */
    public void initToolbar(Toolbar toolbar) {
        mToolbar = toolbar;
        if (toolbar != null) {
//            getProxyActivity().setSupportActionBar(toolbar);
        }
        initTitleNeedBack();
    }

    protected void initTitleNeedBack() {
        // 设置左上角的返回按钮为实际的返回效果
        ActionBar actionBar = getProxyActivity().getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolbar.setNavigationOnClickListener(v -> getSupportDelegate().pop());
    }

}
