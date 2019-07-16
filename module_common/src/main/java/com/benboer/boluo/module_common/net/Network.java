package com.benboer.boluo.module_common.net;

import com.benboer.boluo.core.app.BoLuo;
import com.benboer.boluo.core.app.ConfigKeys;
import com.benboer.boluo.module_common.utils.DBFlowExclusionStrategy;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.benboer.boluo.core.app.ConfigKeys.API_HOST;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/5.
 */
public class Network {

    private static Network instance;

    private Retrofit retrofit;

    private static final String URL = BoLuo.getConfiguration(API_HOST);
    private static final ArrayList<Interceptor> INTERCEPTORS = BoLuo.getConfiguration(ConfigKeys.INTERCEPTOR);

    static {
        instance = new Network();
    }

    private Network(){

    }

    public static Retrofit getRetrofit(){
        if (instance.retrofit != null){
            return instance.retrofit;
        }
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        for (int i = 0; i<INTERCEPTORS.size(); i++){
            clientBuilder.addInterceptor(INTERCEPTORS.get(i));
        }
        OkHttpClient client = clientBuilder.build();

        Retrofit.Builder builder = new Retrofit.Builder();
        instance.retrofit = builder.baseUrl(URL)
                // 设置client
                .client(client)
                // 设置Json解析器
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        // 设置时间格式
                        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
                        // 设置一个过滤器，数据库级别的Model不进行Json转换
                        .setExclusionStrategies(new DBFlowExclusionStrategy())
                        .create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava2
                .build();

        return instance.retrofit;
    }
//    /**
//     * 返回一个请求代理
//     *
//     * @return RemoteService
//     */
//    public static RxRemoteService remote() {
//        return Network.getRetrofit().create(RxRemoteService.class);
//    }

}
