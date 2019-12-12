package com.benboer.boluo.common.net.interceptors;

import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.benboer.boluo.common.service.AccountService;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by BenBoerBoluojiushiwo on 2019/8/13.
 */
public class TokenInterceptor implements Interceptor {

    @Autowired(name = "/main/account_service")
    protected AccountService mAccountService;

    public TokenInterceptor(){
        ARouter.getInstance().inject( this);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder builder = original.newBuilder();
        if (mAccountService != null && !TextUtils.isEmpty(mAccountService.getToken())){
            builder.addHeader("token", mAccountService.getToken());
        }
        builder.addHeader("Content-Type", "application/json");
        Request newRequest = builder.build();
        return chain.proceed(newRequest);
    }
}
