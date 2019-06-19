package com.benboer.boluo.factory.net;

import com.benboer.boluo.factory.model.api.RspModel;
import com.benboer.boluo.factory.model.api.account.AccountRspModel;
import com.benboer.boluo.factory.model.api.account.LoginModel;
import com.benboer.boluo.factory.model.api.account.RegisterModel;
import com.benboer.boluo.factory.model.api.group.GroupCreateModel;
import com.benboer.boluo.factory.model.api.group.GroupMemberAddModel;
import com.benboer.boluo.factory.model.api.message.MsgCreateModel;
import com.benboer.boluo.factory.model.api.user.UserUpdateModel;
import com.benboer.boluo.factory.model.card.GroupCard;
import com.benboer.boluo.factory.model.card.GroupMemberCard;
import com.benboer.boluo.factory.model.card.MessageCard;
import com.benboer.boluo.factory.model.card.UserCard;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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

    /**
     * 用户更新
     * @param model
     * @return
     */
    @PUT("user")
    Call<RspModel<UserCard>> updateUser(@Body UserUpdateModel model);

    /**
     * 获取联系人列表
     */
    @GET("user/contact")
    Call<RspModel<List<UserCard>>> userContacts();

    /**
     * 搜索联系人
     * @return
     */
    @GET("user/search/{name}")
    Call<RspModel<List<UserCard>>> userSearch(@Path("name") String name);

    /**
     * 关注联系人
     * @return
     */
    @PUT("user/follow/{followId}")
    Call<RspModel<UserCard>> userFollow(@Path("followId") String id);

    /**
     * 获得联系人信息
     * @param userId
     * @return
     */
    @GET("user/{userId}")
    Call<RspModel<UserCard>> userFind(@Path("userId") String userId);

    /**
     *  发送消息的接口
     */
    @POST("msg")
    Call<RspModel<MessageCard>> msgPush(@Body MsgCreateModel model);


    /**
     *创建群
     */
    @POST("group")
    Call<RspModel<GroupCard>> groupCreate(@Body GroupCreateModel model);

    /**
     * 拉取我的群列表
     * @param date
     * @return
     */
    @GET("group/list/{date}")
    Call<RspModel<List<GroupCard>>> groups(@Path(value = "date", encoded = true) String date);


    /**
     * 我的群的成员列表
     */
    @GET("group/{groupId}/member")
    Call<RspModel<List<GroupMemberCard>>> groupMembers(@Path("groupId") String groupId);

    /**
     * 拉取群信息
     */
    @GET("group/{groupId}")
    Call<RspModel<GroupCard>> groupFind(@Path("groupId") String groupId);


    /**
     * 群添加成员
     */
    @POST("group/{groupId}/member")
    Call<RspModel<List<GroupMemberCard>>> groupMemberAdd(@Path("groupId") String groupId,
                                                         @Body GroupMemberAddModel model);

    /**
     * 群搜索的接口
     */

    @GET("group/search/{name}")
    Call<RspModel<List<GroupCard>>> groupSearch(@Path(value = "name", encoded = true) String name);
}
