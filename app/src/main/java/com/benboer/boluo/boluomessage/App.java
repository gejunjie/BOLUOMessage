package com.benboer.boluo.boluomessage;


import android.content.Context;
import android.text.TextUtils;

import androidx.multidex.MultiDex;

import com.benboer.boluo.common.BaseApplication;
import com.benboer.boluo.common.Factory;
import com.benboer.boluo.common.app.BoLuo;
import com.benboer.boluo.common.persistence.Account;
import com.benboer.boluo.componentbase.ServiceFactory;
import com.benboer.boluo.message.PushIntentService;
import com.benboer.boluo.message.service.AccountService;
import com.benboer.boluo.message.service.BottomFragmentService;
import com.igexin.sdk.PushManager;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by BenBoerBoluojiushiwo on 2019/3/28.
 */
public class App extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        BoLuo.init(this)
                .withIcon(new FontAwesomeModule())
//                .withApiHost("http://172.20.10.2:6000/Gradle___boluo___boluo_1_0_SNAPSHOT_war/api/")
                .withApiHost("http://192.168.31.210:6000/Gradle___boluo___boluo_1_0_SNAPSHOT_war/api/")
                .withInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request.Builder builder = original.newBuilder();
                        if (!TextUtils.isEmpty(Account.getToken())){
                            builder.addHeader("token", Account.getToken());
                        }
                        builder.addHeader("Content-Type", "application/json");
                        Request newRequest = builder.build();
                        // 返回
                        return chain.proceed(newRequest);
                    }
                })
                .configure();

        // 调用Factory进行初始化
        Factory.setup();
        // 持久化的数据进行初始化
        Account.load();//TODO 这个不应该在这里初始化
        // 推送进行初始化
        PushManager.getInstance().initialize(getApplicationContext(),null);
        PushManager.getInstance().registerPushIntentService(getApplicationContext(), PushIntentService.class);
        ServiceFactory.getInstance().setAccountService(new AccountService());
        ServiceFactory.getInstance().setFragmentService(new BottomFragmentService());

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
