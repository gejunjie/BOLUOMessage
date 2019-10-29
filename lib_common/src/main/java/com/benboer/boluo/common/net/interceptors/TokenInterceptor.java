package com.benboer.boluo.common.net.interceptors;

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
        Request original = chain.request();
        Request.Builder builder = original.newBuilder();
        //todo
//        if (!TextUtils.isEmpty(Account.getToken())){
//            builder.addHeader("token", Account.getToken());
//        }
        builder.addHeader("Content-Type", "application/json");
        Request newRequest = builder.build();
        return chain.proceed(newRequest);
    }
}
