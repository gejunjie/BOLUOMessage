package com.benboer.boluo.factory.model.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

import java.util.Date;
import java.util.Objects;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/23.
 */
@Table(database = AppDatabase.class)
public class Session extends BaseDbModel<Session> {
    @PrimaryKey
    private String id; // Id, 是Message中的接收者User的Id或者群的Id
    @Column
    private String picture; // 图片，接收者用户的头像，或者群的图片
    @Column
    private String title; // 标题，用户的名称，或者群的名称
    @Column
    private String content; // 显示在界面上的简单内容，是Message的一个描述
    @Column
    private int receiverType = Message.RECEIVER_TYPE_NONE; // 类型，对应人，或者群消息
    @Column
    private int unReadCount; // 未读数量，当没有在当前界面时，应当增加未读数量
    @Column
    private Date modifyAt; // 最后更改时间

    @ForeignKey(tableClass = Message.class)
    private Message message; // 对应的消息，外键为Message的Id

    public Session() {

    }

    public Session(Identify identify) {
        this.id = identify.id;
        this.receiverType = identify.type;
    }

    public Session(Message message) {
        if (message.getGroup() == null) {
            receiverType = Message.RECEIVER_TYPE_NONE;
            User other = message.getOther();
            id = other.getId();
            picture = other.getPortrait();
            title = other.getName();
        } else {
            receiverType = Message.RECEIVER_TYPE_GROUP;
            id = message.getGroup().getId();
            picture = message.getGroup().getPicture();
            title = message.getGroup().getName();
        }
        this.message = message;
        this.content = message.getSampleContent();
        this.modifyAt = message.getCreateAt();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getReceiverType() {
        return receiverType;
    }

    public void setReceiverType(int receiverType) {
        this.receiverType = receiverType;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public int getUnReadCount() {
        return unReadCount;
    }

    public void setUnReadCount(int unReadCount) {
        this.unReadCount = unReadCount;
    }

    public Date getModifyAt() {
        return modifyAt;
    }

    public void setModifyAt(Date modifyAt) {
        this.modifyAt = modifyAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Session session = (Session) o;

        return receiverType == session.receiverType
                && unReadCount == session.unReadCount
                && Objects.equals(id, session.id)
                && Objects.equals(picture, session.picture)
                && Objects.equals(title, session.title)
                && Objects.equals(content, session.content)
                && Objects.equals(modifyAt, session.modifyAt)
                && Objects.equals(message, session.message);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + receiverType;
        return result;
    }

    @Override
    public boolean isSame(Session oldT) {
        return Objects.equals(id, oldT.id)
                && Objects.equals(receiverType, oldT.receiverType);
    }

    @Override
    public boolean isUiContentSame(Session oldT) {
        return this.content.equals(oldT.content)
                && Objects.equals(this.modifyAt, oldT.modifyAt);
    }


    /**
     * 提取主要部分，用于和Session进行对应
     *
     * @param message 消息Model
     * @return 返回一个Session.Identify
     */
    public static Identify createSessionIdentify(Message message) {
        Identify identify = new Identify();
        if (message.getGroup() == null) {
            identify.type = Message.RECEIVER_TYPE_NONE;
            User other = message.getOther();
            identify.id = other.getId();
        } else {
            identify.type = Message.RECEIVER_TYPE_GROUP;
            identify.id = message.getGroup().getId();
        }
        return identify;
    }

    public void refreshToNow() {
        // TODO 刷新会话对应的信息为当前Message的最新状态
    }


    /**
     * 对于会话信息，最重要的部分进行提取
     * 其中主要关注两个点：
     * 一个会话最重要的是标示是和人聊天还是在群聊天；
     * 所以对于这点：Id存储的是人或者群的Id
     * 紧跟着Type：存储的是具体的类型（人、群）
     * equals 和 hashCode 也是对两个字段进行判断
     */
    public static class Identify {
        public String id;
        public int type;


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Identify identify = (Identify) o;
            return type == identify.type
                    && (id != null ? id.equals(identify.id) : identify.id == null);
        }

        @Override
        public int hashCode() {
            int result = id != null ? id.hashCode() : 0;
            result = 31 * result + type;
            return result;
        }
    }
}
