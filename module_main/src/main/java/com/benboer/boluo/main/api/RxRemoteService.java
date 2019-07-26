package com.benboer.boluo.main.api;

import com.benboer.boluo.common.net.model.RspModel;
import com.benboer.boluo.main.model.AccountRspModel;
import com.benboer.boluo.main.model.LoginModel;
import com.benboer.boluo.main.model.RegisterModel;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/6.
 *
 * 网络请求的所有的接口
 */
public interface RxRemoteService {

    /**
     * 用户注册接口
     * @param model
     * @return
     */
    @POST("account/register")
    Observable<RspModel<AccountRspModel>> accountRegister(@Body RegisterModel model);

    /**
     * 用户登录
     * @param model
     * @return
     */
    @POST("account/login")
    Observable<RspModel<AccountRspModel>> accountLogin(@Body LoginModel model);

    /**
     * 绑定设备id
     * @param pushId
     * @return
     */
    @POST("account/bind/{pushId}")
    Observable<RspModel<AccountRspModel>> accountBind(@Path(encoded = true, value = "pushId") String pushId);

}
