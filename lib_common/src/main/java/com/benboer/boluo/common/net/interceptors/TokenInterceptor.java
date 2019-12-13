package com.benboer.boluo.common.net.interceptors;

import android.text.TextUtils;

import com.benboer.boluo.common.util.storage.PreferenceUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by BenBoerBoluojiushiwo on 2019/8/13.
 */
public class TokenInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {

        String token = PreferenceUtil.getCustomAppProfile("KEY_TOKEN");

        Request original = chain.request();
        Request.Builder builder = original.newBuilder();
        if (!TextUtils.isEmpty(token)) {
            builder.addHeader("token", token);
        }
        builder.addHeader("Content-Type", "application/json");
        Request newRequest = builder.build();
        return chain.proceed(newRequest);
    }
}
