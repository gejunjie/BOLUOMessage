package com.benboer.boluo.factory.data.helper;

import android.os.strictmode.SqliteObjectLeakedViolation;
import android.text.TextUtils;

import com.benboer.boluo.factory.Factory;
import com.benboer.boluo.factory.R;
import com.benboer.boluo.factory.data.DataSource;
import com.benboer.boluo.factory.model.api.RspModel;
import com.benboer.boluo.factory.model.api.account.AccountRspModel;
import com.benboer.boluo.factory.model.api.account.LoginModel;
import com.benboer.boluo.factory.model.api.account.RegisterModel;
import com.benboer.boluo.factory.model.db.Group;
import com.benboer.boluo.factory.model.db.GroupMember;
import com.benboer.boluo.factory.model.db.Message;
import com.benboer.boluo.factory.model.db.Session;
import com.benboer.boluo.factory.model.db.User;
import com.benboer.boluo.factory.net.Network;
import com.benboer.boluo.factory.net.RemoteService;
import com.benboer.boluo.factory.persistence.Account;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/6.
 *
 * account网络请求的接口
 */
public class AccountHelper {

    /**
     * 注册接口
     *
     * @param model    注册的Model
     * @param callback 成功与失败的接口回送
     */
    public static void register(final RegisterModel model, final DataSource.Callback<User> callback) {
        RemoteService service = Network.remote();

        Call<RspModel<AccountRspModel>> call = service.accountRegister(model);

        call.enqueue(new AccountRspCallback(callback));
    }

    /**
     * 登录的调用
     *
     * @param model    登录的Model
     * @param callback 成功与失败的接口回送
     */
    public static void login(final LoginModel model, final DataSource.Callback<User> callback) {
        RemoteService service = Network.remote();
        Call<RspModel<AccountRspModel>> call = service.accountLogin(model);
        call.enqueue(new AccountRspCallback(callback));
    }

    /**
     * 退出登录
     *
     */
    public static void logout(final DataSource.Callback<User> callback) {
        Account.logout();
        SQLite.delete().tables(new Class[]{GroupMember.class, Message.class,
                Group.class, Session.class, User.class});
        callback.onDataLoaded(null);
    }

    /**
     * 对设备Id进行绑定的操作
     *
     * @param callback Callback
     */
    public static void bindPush(final DataSource.Callback<User> callback) {
        String pushId = Account.getPushId();
        if (TextUtils.isEmpty(pushId)) return;
        RemoteService service = Network.remote();
        Call<RspModel<AccountRspModel>> call = service.accountBind(pushId);
        call.enqueue(new AccountRspCallback(callback));
    }

    /**
     * 请求的回调部分封装
     */
    private static class AccountRspCallback implements Callback<RspModel<AccountRspModel>> {

        final DataSource.Callback<User> callback;

        AccountRspCallback(DataSource.Callback<User> callback) {
            this.callback = callback;
        }

        @Override
        public void onResponse(Call<RspModel<AccountRspModel>> call,
                               Response<RspModel<AccountRspModel>> response) {
            RspModel<AccountRspModel> rspModel = response.body();
            if (rspModel == null) return;
            if (rspModel.success()){
                AccountRspModel accountRspModel = rspModel.getResult();
                User user = accountRspModel.getUser();
                // 1.直接保存
                user.save();
                // 同步到XML持久化中
                Account.login(accountRspModel);

                // 判断绑定状态，是否绑定设备
                if (accountRspModel.isBind()) {
                    // 设置绑定状态为True
                    Account.setBind(true);
                    // 然后返回
                    if (callback != null)
                        callback.onDataLoaded(user);
                } else {
                    // 进行绑定的唤起
                    bindPush(callback);
                }
            }else {
                Factory.decodeRspCode(rspModel, callback);
            }
        }

        @Override
        public void onFailure(Call<RspModel<AccountRspModel>> call, Throwable t) {
            if(callback != null){
                callback.onDataNotAvailable(R.string.data_network_error);
            }
        }
    }

}
