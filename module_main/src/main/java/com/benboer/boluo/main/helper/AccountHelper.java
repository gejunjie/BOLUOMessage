package com.benboer.boluo.main.helper;

import android.text.TextUtils;

import com.benboer.boluo.common.mvp.data.DataSource;
import com.benboer.boluo.common.net.Network;
import com.benboer.boluo.common.net.model.RspModel;
import com.benboer.boluo.main.R;
import com.benboer.boluo.main.api.RxRemoteService;
import com.benboer.boluo.main.model.AccountRspModel;
import com.benboer.boluo.main.model.LoginModel;
import com.benboer.boluo.main.model.RegisterModel;
import com.benboer.boluo.main.model.UserModel;
import com.benboer.boluo.main.serviceImpl.Account;
import com.benboer.boluo.message.db.User;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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

        Network.getRetrofit()
                .create(RxRemoteService.class)
                .accountRegister(model)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RspModel<AccountRspModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RspModel<AccountRspModel> rspModel) {
                        onResponse(rspModel, callback);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(callback != null){
                            callback.onDataNotAvailable(R.string.data_network_error);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    /**
     * 登录的调用
     *
     * @param model    登录的Model
     * @param callback 成功与失败的接口回送
     */
    public static void login(final LoginModel model, final DataSource.Callback<User> callback) {
        Network.getRetrofit()
                .create(RxRemoteService.class)
                .accountLogin(model)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RspModel<AccountRspModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RspModel<AccountRspModel> rspModel) {
                        onResponse(rspModel, callback);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(callback != null){
                            callback.onDataNotAvailable(R.string.data_network_error);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 退出登录
     *
     */
    public static void logout() {
//        Account.logout();
    }

    /**
     * 对设备Id进行绑定的操作
     *
     * @param callback Callback
     */
    public static void bindPush(final DataSource.Callback<User> callback) {
        String pushId = Account.getPushId();
        if (TextUtils.isEmpty(pushId)) return;

        Network.getRetrofit()
                .create(RxRemoteService.class)
                .accountBind(pushId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RspModel<AccountRspModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RspModel<AccountRspModel> rspModel) {
                        onResponse(rspModel, callback);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(callback != null){
                            callback.onDataNotAvailable(R.string.data_network_error);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private static void onResponse(RspModel<AccountRspModel> rspModel, DataSource.Callback<User> callback){
        if (rspModel != null && rspModel.success()) {
            AccountRspModel accountRspModel = rspModel.getResult();
            UserModel user = accountRspModel.getUser();
            // 同步到XML持久化中
            Account.login(accountRspModel.getToken(),
                    accountRspModel.getAccount(),
                    user.getId());

            // 判断绑定状态，是否绑定设备
            if (accountRspModel.isBind()) {
                Account.setBind(true);
                if (callback != null)
                    callback.onDataLoaded(null);
            } else {
                // 进行绑定的唤起
                bindPush(callback);
            }
        }else {
//                Factory.decodeRspCode(rspModel, callback);
        }
    }
}
