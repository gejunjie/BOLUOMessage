package com.benboer.boluo.main.serviceImpl;

import android.text.TextUtils;

import com.benboer.boluo.common.util.storage.PreferenceUtil;
import com.benboer.boluo.message.db.Message;
import com.benboer.boluo.message.db.User;
import com.benboer.boluo.message.db.User_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;


/**
 * Created by BenBoerBoluojiushiwo on 2019/5/5.
 */
public class Account{

    private static final String KEY_TOKEN   = "KEY_TOKEN";
    private static final String KEY_PUSH_ID = "KEY_PUSH_ID";
    private static final String KEY_IS_BIND = "KEY_IS_BIND";
    private static final String KEY_USER_ID = "KEY_USER_ID";
    private static final String KEY_ACCOUNT = "KEY_ACCOUNT";

    /**
     * 设备的推送Id
     */
    private static String pushId;
    /** 设备Id是否已经绑定到了服务器*/
    private static boolean isBind;
    /** 登录状态的Token，用来接口请求*/
    private static String token;
    /** 登录的用户ID*/
    private static String userId;
    /** 登录的账户*/
    private static String account;

    /**
     * 获取推送Id
     *
     * @return 推送Id
     */
    public static String getPushId() {
        return pushId;
    }

    /**
     * 保存我自己的信息到持久化XML中
     */
    public static void login(String token, String account, String userId) {
        Account.token = token;
        Account.account = account;
        Account.userId = userId;
        Message.ACCOUNT_ID = userId;
        save();
    }

//    /**
//     * 清除用户信息
//     */
//    public static void logout() {
//        SharedPreferences sp = BoLuo.getApplicationContext()
//                .getSharedPreferences( Application.class.getName(), Context.MODE_PRIVATE);
//        sp.edit()
//                .remove(KEY_PUSH_ID)
//                .remove(KEY_IS_BIND)
//                .remove(KEY_TOKEN)
//                .remove(KEY_USER_ID)
//                .apply();
//        SQLite.delete().tables(new Class[]{GroupMember.class, Message.class,
//                Group.class, Session.class, User.class});
//    }

    /**
     * 进行数据加载
     */
    public static void load() {
        pushId = PreferenceUtil.getCustomAppProfile(KEY_PUSH_ID);
        isBind = Boolean.valueOf(PreferenceUtil.getCustomAppProfile(KEY_IS_BIND));
        token = PreferenceUtil.getCustomAppProfile(KEY_TOKEN);
        userId = PreferenceUtil.getCustomAppProfile(KEY_USER_ID);
        account = PreferenceUtil.getCustomAppProfile(KEY_ACCOUNT);
    }

    /**
     * 返回当前账户是否登录
     *
     * @return True已登录
     */
    public static boolean isLogin() {
        return !TextUtils.isEmpty(userId)
                && !TextUtils.isEmpty(token);
    }

    /**
     * 是否已经绑定到了服务器
     *
     * @return True已绑定
     */
    public static boolean isBind() {
        return isBind;
    }

    /**
     * 存储数据到XML文件，持久化
     */
    private static void save() {
        PreferenceUtil.addCustomAppProfile(KEY_PUSH_ID, pushId);
        PreferenceUtil.addCustomAppProfile(KEY_IS_BIND, String.valueOf(isBind));
        PreferenceUtil.addCustomAppProfile(KEY_TOKEN, token);
        PreferenceUtil.addCustomAppProfile(KEY_USER_ID, userId);
        PreferenceUtil.addCustomAppProfile(KEY_ACCOUNT, account);
    }

    /**
     * 获取当前登录的用户信息
     *
     * @return User
     */
    public static User getUser() {
        // 如果为null返回一个new的User，其次从数据库查询
        return TextUtils.isEmpty(userId) ? new User() : SQLite.select()
                .from(User.class)
                .where( User_Table.id.eq(userId))
                .querySingle();
    }


    /**
     * 设置并存储设备的Id
     *
     * @param pushId 设备的推送ID
     */
    public static void setPushId(String pushId) {
        Account.pushId = pushId;
        Account.save();
    }

    /**
     * 设置绑定状态
     */
    public static void setBind(boolean isBind) {
        Account.isBind = isBind;
        Account.save();
    }

    /**
     * 获取当前登录的Token
     *
     * @return Token
     */
    public static String getToken() {
        return token;
    }

    /**
     * 获取userId
     * @return
     */
    public static String getUserId(){
        return userId;
    }

    /**
     * 获取账户名
     * @return
     */
    public static String getAccount(){
        return account;
    }


}
