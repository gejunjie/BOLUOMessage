package com.benboer.boluo.common.web.client;

import android.graphics.Bitmap;
import android.os.Handler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.benboer.boluo.common.app.BoLuo;
import com.benboer.boluo.common.comm_ui.loader.FragmentLoader;
import com.benboer.boluo.common.util.log.LogUtils;
import com.benboer.boluo.common.web.IPageLoadListener;
import com.benboer.boluo.common.web.WebFragment;
import com.benboer.boluo.common.web.route.Router;

/**
 * Created by BenBoerBoluojiushiwo on 2019/8/23.
 */
public class WebViewClientImpl extends WebViewClient {

    private final WebFragment FRAGMENT;
    private IPageLoadListener mIPageLoadListener = null;
    private static final Handler HANDLER = BoLuo.getHandler();

    public void setPageLoadListener(IPageLoadListener listener) {
        this.mIPageLoadListener = listener;
    }

    public WebViewClientImpl(WebFragment fragment) {
        this.FRAGMENT = fragment;
    }

    //拦截关键方法，当点击WebView链接时，打开新的fragment加载
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        LogUtils.e("shouldOverrideUrlLoading", url);
        return Router.getInstance().handleWebUrl(FRAGMENT,url);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if (mIPageLoadListener != null) {
            mIPageLoadListener.onLoadStart();
        }
        FragmentLoader.showLoading(view.getContext());
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if (mIPageLoadListener != null) {
            mIPageLoadListener.onLoadEnd();
        }
        HANDLER.postDelayed(new Runnable() {
            @Override
            public void run() {
                FragmentLoader.stopLoading();
            }
        }, 500);
    }


}
