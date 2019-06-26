package com.benboer.boluo.core.app;

import com.benboer.boluo.core.util.storage.PreferenceUtil;

/**
 * Created by BenBoerBoluojiushiwo on 2019/6/26.
 */
public class AccountManager {
    private enum SignTag {
        SIGN_TAG
    }

    //保存用户登录状态，登录后调用
    public static void setSignState(boolean state) {
        PreferenceUtil.setAppFlag(SignTag.SIGN_TAG.name(), state);
    }

    private static boolean isSignIn() {
        return PreferenceUtil.getAppFlag(SignTag.SIGN_TAG.name());
    }

    public static void checkAccount(IUserLoginChecker checker) {
        if (isSignIn()) {
            checker.onSignIn();
        } else {
            checker.onNotSignIn();
        }
    }
}
