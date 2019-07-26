package com.benboer.boluo.main.model;


import com.raizlabs.android.dbflow.annotation.Column;

import java.util.Date;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/5.
 */
public class AccountRspModel {

    // 用户基本信息
    private UserModel user;
    // 当前登录的账号
    private String account;
    // 当前登录成功后获取的Token,
    // 可以通过Token获取用户的所有信息
    private String token;
    // 标示是否已经绑定到了设备PushId
    private boolean isBind;

    private String id;

    private String name;

    private String phone;

    private String portrait;
    private String desc;
    private int sex = 0;

    //备注信息
    private String alias;

    // 用户关注人的数量
    private int follows;

    // 用户粉丝的数量
    private int following;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getFollows() {
        return follows;
    }

    public void setFollows(int follows) {
        this.follows = follows;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public boolean isFollow() {
        return isFollow;
    }

    public void setFollow(boolean follow) {
        isFollow = follow;
    }

    public Date getModifyAt() {
        return modifyAt;
    }

    public void setModifyAt(Date modifyAt) {
        this.modifyAt = modifyAt;
    }

    // 与当前User的关系状态，是否已经关注了这个人
    @Column
    private boolean isFollow;

    // 时间字段
    @Column
    private Date modifyAt;

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isBind() {
        return isBind;
    }

    public void setBind(boolean bind) {
        isBind = bind;
    }
}
