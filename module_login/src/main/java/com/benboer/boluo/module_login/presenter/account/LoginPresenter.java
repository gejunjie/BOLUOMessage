package com.benboer.boluo.module_login.presenter.account;

import android.text.TextUtils;

import com.benboer.boluo.lib_db.db.User;
import com.benboer.boluo.module_common.mvp.data.DataSource;
import com.benboer.boluo.module_common.mvp.presenter.BasePresenter;
import com.benboer.boluo.module_common.persistence.Account;
import com.benboer.boluo.module_login.R;
import com.benboer.boluo.module_login.helper.AccountHelper;
import com.benboer.boluo.module_login.model.LoginModel;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/6.
 */
public class LoginPresenter extends BasePresenter<LoginContract.View>
    implements LoginContract.Presenter, DataSource.Callback<User> {

    public LoginPresenter(LoginContract.View view) {
        super(view);
    }
    final LoginContract.View view = getView();
    //成功
    @Override
    public void onDataLoaded(User user) {
        if (view != null) {
            view.loginSuccess();
        }
    }

    //失败
    @Override
    public void onDataNotAvailable(int strRes) {
        if (view != null){
            view.showError(strRes);
        }
    }

    @Override
    public void login(String phone, String password) {
        start();
        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)){
            view.showError(R.string.data_account_login_invalid_parameter);
        }else{
            LoginModel loginModel = new LoginModel(phone, password, Account.getPushId());
            AccountHelper.login(loginModel, this);
//            RestClient.builder()
//                    .url(BoLuo.getConfiguration(ConfigKeys.API_HOST) + "account/login")
//                    .raw(new Gson().toJson(loginModel))
//                    .success(new ISuccess() {
////                        {"code":1,"message":"ok","time":"2019-07-10T16:48:55.517","result":{"user":{"id":"0ad140b4-2aa1-4248-82fd-bb4c1e74895e","name":"aaa","phone":"13122267963","portrait":"http://italker-new.oss-cn-hongkong.aliyuncs.com/portrait/201906/97f0227c6535182da43f3ff2f4ea0200.jpg","desc":"111\n","sex":1,"follows":5,"following":5,"isFollow":false,"modifyAt":"2019-07-10T16:48:55.492"},"account":"13122267963","token":"MWFkOGZiMmItMThmZi00MmRkLWI4YzMtNjE0MDJlMDgzNWFh","isBind":true}}
//                        @Override
//                        public void onSuccess(String response) {
//                            LogUtils.json("USER_PROFILE", response);
////                            SignHandler.onSignIn(response, mISignListener);
////                            getSupportDelegate().startWithPop(new EcBottomFragment());
//                            ServiceFactory.getInstance().getAccountService().saveUser();
//                            // 同步到XML持久化中
////                            Account.login(accountRspModel);
//
////                            // 判断绑定状态，是否绑定设备
////                            if (accountRspModel.isBind()) {
////                                // 设置绑定状态为True
////                                Account.setBind(true);
////                                // 然后返回
////                                onDataLoaded(null);
////                            } else {
////                                // 进行绑定的唤起
////                                bindPush(callback);
////                            }
//
//                        }
//                    })
////                    .loader(getContext())
//                    .build()
//                    .post();


        }
    }
}
