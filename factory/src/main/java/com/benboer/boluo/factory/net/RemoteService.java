package com.benboer.boluo.factory.net;

import com.benboer.boluo.factory.model.api.RspModel;
import com.benboer.boluo.factory.model.api.account.AccountRspModel;
import com.benboer.boluo.factory.model.api.account.LoginModel;
import com.benboer.boluo.factory.model.api.account.RegisterModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/6.
 * 网络请求的所有的接口
 */
public interface RemoteService {
    /**
     * 用户注册接口
     * @param model
     * @return
     */
    @POST("account/register")
    Call<RspModel<AccountRspModel>> accountRegister(@Body RegisterModel model);

    /**
     * 用户登录
     * @param model
     * @return
     */
    @POST("account/login")
    Call<RspModel<AccountRspModel>> accountLogin(@Body LoginModel model);

    /**
     * 绑定设备id
     * @param pushId
     * @return
     */
    @POST("account/bind/{pushId}")
    Call<RspModel<AccountRspModel>> accountBind(@Path(encoded = true, value = "pushId") String pushId);

}
