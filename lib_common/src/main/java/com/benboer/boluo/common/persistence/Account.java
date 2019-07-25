package com.benboer.boluo.common.persistence;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.benboer.boluo.common.app.BoLuo;
import com.benboer.boluo.db.db.Group;
import com.benboer.boluo.db.db.GroupMember;
import com.benboer.boluo.db.db.Message;
import com.benboer.boluo.db.db.Session;
import com.benboer.boluo.db.db.User;
import com.raizlabs.android.dbflow.sql.language.SQLite;


/**
 * Created by BenBoerBoluojiushiwo on 2019/5/5.
 */
public class Account {

    private static final String KEY_TOKEN   = "KEY_TOKEN";
    private static final String KEY_PUSH_ID = "KEY_PUSH_ID";
    private static final String KEY_IS_BIND = "KEY_IS_BIND";
    private static final String KEY_USER_ID = "KEY_USER_ID";
    private static final String KEY_ACCOUNT = "KEY_ACCOUNT";

    // 设备的推送Id
    private static String pushId;
    // 设备Id是否已经绑定到了服务器
    private static boolean isBind;
    // 登录状态的Token，用来接口请求
    private static String token;
    // 登录的用户ID
    private static String userId;
    // 登录的账户
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
        // 存储当前登录的账户, token, 用户Id
        Account.token = token;
        Account.account = account;
        Account.userId = userId;
        Message.ACCOUNT_ID = userId;
        save();
    }

    /**
     * 清除用户信息
     */
    public static void logout() {
        SharedPreferences sp = BoLuo.getApplicationContext()
                .getSharedPreferences(Account.class.getName(), Context.MODE_PRIVATE);
        sp.edit()
                .remove(KEY_PUSH_ID)
                .remove(KEY_IS_BIND)
                .remove(KEY_TOKEN)
                .remove(KEY_USER_ID)
                .apply();
        SQLite.delete().tables(new Class[]{GroupMember.class, Message.class,
                Group.class, Session.class, User.class});
    }

    /**
     * 进行数据加载
     */
    public static void load() {
        SharedPreferences sp = BoLuo.getApplicationContext().getSharedPreferences(Account.class.getName(),
                Context.MODE_PRIVATE);
        pushId = sp.getString(KEY_PUSH_ID, "");
        isBind = sp.getBoolean(KEY_IS_BIND, false);
        token = sp.getString(KEY_TOKEN, "");
        userId = sp.getString(KEY_USER_ID, "");
        account = sp.getString(KEY_ACCOUNT, "");
    }

    /**
     * 返回当前账户是否登录
     *
     * @return True已登录
     */
    public static boolean isLogin() {
        // 用户Id 和 Token 不为空
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
        // 获取数据持久化的SP
        SharedPreferences sp = BoLuo.getApplicationContext()
                .getSharedPreferences(Account.class.getName(),
                Context.MODE_PRIVATE);
        // 存储数据
        sp.edit()
                .putString(KEY_PUSH_ID, pushId)
                .putBoolean(KEY_IS_BIND, isBind)
                .putString(KEY_TOKEN, token)
                .putString(KEY_USER_ID, userId)
                .putString(KEY_ACCOUNT, account)
                .apply();
    }

//    /**
//     * 是否已经完善了用户信息
//     *
//     * @return True 是完成了
//     */
//    public static boolean isComplete() {
//
//        if (isLogin()) {
//            User self = getUser();
//            return !TextUtils.isEmpty(self.getDesc())
//                    && !TextUtils.isEmpty(self.getPortrait())
//                    && self.getSex() != 0;
//        }
//        // 未登录返回信息不完全
//        return false;
//    }
//
//    /**
//     * 获取当前登录的用户信息
//     *
//     * @return User
//     */
//    public static User getUser() {
//        // 如果为null返回一个new的User，其次从数据库查询
//        return TextUtils.isEmpty(userId) ? new User() : SQLite.select()
//                .from(User.class)
//                .where(User_Table.id.eq(userId))
//                .querySingle();
//    }


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
