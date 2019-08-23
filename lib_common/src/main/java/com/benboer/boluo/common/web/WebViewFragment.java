package com.benboer.boluo.common.web;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.benboer.boluo.common.R;
import com.benboer.boluo.common.base.fragment.SupportFragment;
import com.benboer.boluo.common.web.route.RouteKeys;

import static com.blankj.utilcode.util.BarUtils.getStatusBarHeight;

/**
 * Created by BenBoerBoluojiushiwo on 2019/8/23.
 */
public class WebViewFragment extends SupportFragment {
    private String mUrl;

    public static WebViewFragment newInstance(String url) {
        Bundle args = new Bundle();
        args.putString(RouteKeys.URL.name(), url);
        WebViewFragment fragment = new WebViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args != null) {
            mUrl = args.getString(RouteKeys.URL.name());
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_web;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View root) {
        Toolbar mToolbar = bind(R.id.tb_discover);
        mToolbar.setPadding(0, getStatusBarHeight(),0,0);
    }


    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        final WebFragmentImpl fragment = WebFragmentImpl.create(mUrl);
//        final WebFragmentImpl fragment = WebFragmentImpl.create("https://www.csdn.net/");
        fragment.setTopFragment(this);
        getSupportDelegate().loadRootFragment(R.id.web_discovery_container,fragment);
    }

}
