package com.benboer.boluo.main.model;

import com.benboer.boluo.db.model.Author;

import java.util.Date;


/**
 * Created by BenBoerBoluojiushiwo on 2019/5/6.
 */
public class UserModel implements Author {
    public static final int SEX_MAN = 1;
    public static final int SEX_WOMAN = 2;


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

    // 与当前User的关系状态，是否已经关注了这个人
    private boolean isFollow;

    // 时间字段
    private Date modifyAt;

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


    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", portrait='" + portrait + '\'' +
                ", desc='" + desc + '\'' +
                ", sex=" + sex +
                ", alias='" + alias + '\'' +
                ", follows=" + follows +
                ", following=" + following +
                ", isFollow=" + isFollow +
                ", modifyAt=" + modifyAt +
                '}';
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (portrait != null ? portrait.hashCode() : 0);
        result = 31 * result + (desc != null ? desc.hashCode() : 0);
        result = 31 * result + sex;
        result = 31 * result + (alias != null ? alias.hashCode() : 0);
        result = 31 * result + follows;
        result = 31 * result + following;
        result = 31 * result + (isFollow ? 1 : 0);
        result = 31 * result + (modifyAt != null ? modifyAt.hashCode() : 0);
        return result;
    }
}
