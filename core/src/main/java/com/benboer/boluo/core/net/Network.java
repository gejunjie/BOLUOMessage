package com.benboer.boluo.core.net;

import androidx.annotation.StringRes;

import com.benboer.boluo.core.app.BoLuo;
import com.benboer.boluo.core.app.ConfigKeys;
import com.google.gson.GsonBuilder;

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
                .addInterceptor(BoLuo.getConfiguration(ConfigKeys.INTERCEPTOR))
                .build();

        Retrofit.Builder builder = new Retrofit.Builder();
        instance.retrofit = builder.baseUrl(URL)
                // 设置client
                .client(client)
                // 设置Json解析器
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        // 设置时间格式
                        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
                        // 设置一个过滤器，数据库级别的Model不进行Json转换
//                        .setExclusionStrategies(new DBFlowExclusionStrategy())
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

//    /**
//     * 进行错误Code的解析，
//     * 把网络返回的Code值进行统一的规划并返回为一个String资源
//     *
//     * @param model    RspModel
//     * @param callback DataSource.FailedCallback 用于返回一个错误的资源Id
//     */
//    public static void decodeRspCode(RspModel model, DataSource.FailedCallback callback) {
//        if (model == null)
//            return;
//
//        // 进行Code区分
//        switch (model.getCode()) {
//            case RspModel.SUCCEED:
//                return;
//            case RspModel.ERROR_SERVICE:
//                decodeRspCode(R.string.data_rsp_error_service, callback);
//                break;
//            case RspModel.ERROR_NOT_FOUND_USER:
//                decodeRspCode(R.string.data_rsp_error_not_found_user, callback);
//                break;
//            case RspModel.ERROR_NOT_FOUND_GROUP:
//                decodeRspCode(R.string.data_rsp_error_not_found_group, callback);
//                break;
//            case RspModel.ERROR_NOT_FOUND_GROUP_MEMBER:
//                decodeRspCode(R.string.data_rsp_error_not_found_group_member, callback);
//                break;
//            case RspModel.ERROR_CREATE_USER:
//                decodeRspCode(R.string.data_rsp_error_create_user, callback);
//                break;
//            case RspModel.ERROR_CREATE_GROUP:
//                decodeRspCode(R.string.data_rsp_error_create_group, callback);
//                break;
//            case RspModel.ERROR_CREATE_MESSAGE:
//                decodeRspCode(R.string.data_rsp_error_create_message, callback);
//                break;
//            case RspModel.ERROR_PARAMETERS:
//                decodeRspCode(R.string.data_rsp_error_parameters, callback);
//                break;
//            case RspModel.ERROR_PARAMETERS_EXIST_ACCOUNT:
//                decodeRspCode(R.string.data_rsp_error_parameters_exist_account, callback);
//                break;
//            case RspModel.ERROR_PARAMETERS_EXIST_NAME:
//                decodeRspCode(R.string.data_rsp_error_parameters_exist_name, callback);
//                break;
//            case RspModel.ERROR_ACCOUNT_TOKEN:
////                Application.showToast(R.string.data_rsp_error_account_token);
////                instance.logout();
//                break;
//            case RspModel.ERROR_ACCOUNT_LOGIN:
//                decodeRspCode(R.string.data_rsp_error_account_login, callback);
//                break;
//            case RspModel.ERROR_ACCOUNT_REGISTER:
//                decodeRspCode(R.string.data_rsp_error_account_register, callback);
//                break;
//            case RspModel.ERROR_ACCOUNT_NO_PERMISSION:
//                decodeRspCode(R.string.data_rsp_error_account_no_permission, callback);
//                break;
//            case RspModel.ERROR_UNKNOWN:
//            default:
//                decodeRspCode(R.string.data_rsp_error_unknown, callback);
//                break;
//        }
//    }
//
//    private static void decodeRspCode(@StringRes final int resId,
//                                      final DataSource.FailedCallback callback) {
//        if (callback != null)
//            callback.onDataNotAvailable(resId);
//    }

}