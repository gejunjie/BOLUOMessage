package com.benboer.boluo.common.service;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * 用户账户信息，需要暴露给各个业务组件调用
 */
public interface AccountService extends IProvider {

    /**
     * 返回当前账户是否登录
     *
     * @return True已登录
     */
    boolean isLogin();

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

}
