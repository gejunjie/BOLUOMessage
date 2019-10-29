package com.benboer.boluo.common.service;

import com.alibaba.android.arouter.facade.template.IProvider;

public interface AccountService extends IProvider {
//    /**
//     * 获取推送Id
//     *
//     * @return 推送Id
//     */
//    String getPushId();

//    /**
//     * 保存我自己的信息到持久化XML中
//     */
//    void login(String token, String account, String userId);

//    /**
//     * 清除用户信息
//     */
//    void logout();

//    /**
//     * 进行数据加载
//     */
//    void load();

    /**
     * 返回当前账户是否登录
     *
     * @return True已登录
     */
    boolean isLogin();

//    /**
//     * 是否已经绑定到了服务器
//     *
//     * @return True已绑定
//     */
//    boolean isBind();

    /**
     * 是否已经完善了用户信息（描述信息和头像）
     *
     * @return True 是完成了
     */
    boolean isComplete();

    /**
     * 设置并存储设备的Id
     *
     * @param pushId 设备的推送ID
     */
    void setPushId(String pushId);

//    /**
//     * 设置绑定状态
//     */
//    void setBind(boolean isBind);

    /**
     * 获取当前登录的Token
     *
     * @return Token
     */
    String getToken();

    /**
     * 获取userId
     * @return
     */
    String getUserId();

//    /**
//     * 获取账户名
//     * @return
//     */
//    String getAccount();
}
