package com.benboer.boluo.core.net;

import android.content.Context;

import com.benboer.boluo.core.net.callback.IError;
import com.benboer.boluo.core.net.callback.IFailure;
import com.benboer.boluo.core.net.callback.IRequest;
import com.benboer.boluo.core.net.callback.ISuccess;
import com.benboer.boluo.core.ui.loader.LoaderStyle;

import java.io.File;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by BenBoerBoluojiushiwo on 2019/6/27.
 */
public class RestClientBuilder {
    private Context mContext = null;
    private String mUrl = null;
    private String mName = null;
    private String mExtension = null;
    private String mDownloadDir = null;
    private final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private RequestBody mBody = null;
    private LoaderStyle mLoaderStyle = null;
    private File mFile = null;

    private IRequest mIRequest = null;
    private ISuccess mISuccess = null;
    private IFailure mIFailure = null;
    private IError mIError = null;

    RestClientBuilder() {
        PARAMS.clear();
    }

    public final RestClientBuilder url(String url) {
        this.mUrl= url;
        return this;
    }

    public final RestClientBuilder params(WeakHashMap<String, Object> params) {
        PARAMS.putAll(params);
        return this;
    }

    public final RestClientBuilder params(String key, Object value) {
        PARAMS.put(key, value);
        return this;
    }

    public final RestClientBuilder file(File file) {
        this.mFile = file;
        return this;
    }

    public final RestClientBuilder file(String file) {
        this.mFile = new File(file);
        return this;
    }

    public final RestClientBuilder request(IRequest iRequest) {
        this.mIRequest = iRequest;
        return this;
    }

    public final RestClientBuilder raw(String raw) {
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }

    public final RestClientBuilder dir(String dir) {
        this.mDownloadDir = dir;
        return this;
    }

    public final RestClientBuilder extension(String extension) {
        this.mExtension = extension;
        return this;
    }

    public final RestClientBuilder success(ISuccess iSuccess) {
        this.mISuccess = iSuccess;
        return this;
    }

    public final RestClientBuilder failure(IFailure iFailure) {
        this.mIFailure = iFailure;
        return this;
    }

    public final RestClientBuilder error(IError iError) {
        this.mIError = iError;
        return this;
    }

    public final RestClientBuilder loader(Context context, LoaderStyle loaderStyle) {
        this.mContext = context;
        this.mLoaderStyle = loaderStyle;
        return this;
    }

    //默认使用的方法
    public final RestClientBuilder loader(Context context) {
        this.mContext = context;
        this.mLoaderStyle = LoaderStyle.BallSpinFadeLoaderIndicator;
        return this;
    }

    public final RestClient build() {
        return new RestClient(mUrl, PARAMS, mDownloadDir, mExtension, mName, mIRequest, mISuccess,
                mIFailure, mIError, mBody, mFile, mContext, mLoaderStyle);
    }


}
