package com.benboer.boluo.factory.net;

import android.text.TextUtils;

import com.benboer.boluo.common.Common;
import com.benboer.boluo.factory.Factory;
import com.benboer.boluo.factory.persistence.Account;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/5.
 */
public class Network {

    private static Network instance;

    private Retrofit retrofit;

    static {
        instance = new Network();
    }

    private Network(){

    }

    public static Retrofit getRetrofit(){
        if (instance.retrofit != null){
            return instance.retrofit;
        }
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
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
                .build();

        Retrofit.Builder builder = new Retrofit.Builder();

        instance.retrofit = builder.baseUrl(Common.Constance.API_URL)
                // 设置client
                .client(client)
                // 设置Json解析器
                .addConverterFactory(GsonConverterFactory.create(Factory.getGson()))
                .build();

        return instance.retrofit;
    }
}
