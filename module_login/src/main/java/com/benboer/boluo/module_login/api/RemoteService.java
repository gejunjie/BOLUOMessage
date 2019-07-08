package com.benboer.boluo.module_login.api;

import com.benboer.boluo.message.model.api.RspModel;
import com.benboer.boluo.message.model.api.account.AccountRspModel;
import com.benboer.boluo.message.model.api.group.GroupCreateModel;
import com.benboer.boluo.message.model.api.group.GroupMemberAddModel;
import com.benboer.boluo.message.model.api.message.MsgCreateModel;
import com.benboer.boluo.message.model.api.user.UserUpdateModel;
import com.benboer.boluo.message.model.card.GroupCard;
import com.benboer.boluo.message.model.card.GroupMemberCard;
import com.benboer.boluo.message.model.card.MessageCard;
import com.benboer.boluo.message.model.card.UserCard;
import com.benboer.boluo.module_login.model.LoginModel;
import com.benboer.boluo.module_login.model.RegisterModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/6.
 *
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

}
