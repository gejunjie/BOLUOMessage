package com.benboer.boluo.message;
import android.content.Context;
import android.util.Log;

import com.benboer.boluo.message.data.group.GroupDispatcher;
import com.benboer.boluo.message.data.message.MessageDispatcher;
import com.benboer.boluo.message.data.user.UserDispatcher;
import com.benboer.boluo.message.model.api.PushModel;
import com.benboer.boluo.message.model.card.GroupCard;
import com.benboer.boluo.message.model.card.GroupMemberCard;
import com.benboer.boluo.message.model.card.MessageCard;
import com.benboer.boluo.message.model.card.UserCard;
import com.benboer.boluo.common.persistence.Account;
import com.google.gson.reflect.TypeToken;
import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;

import java.lang.reflect.Type;
import java.util.List;

import static com.benboer.boluo.common.Factory.getGson;

/**
 * Created by BenBoerBoluojiushiwo on 2019/6/17.
 *
 * 接收个推SDK推送过来的消息
 */
public class PushIntentService extends GTIntentService {

    @Override
    public void onReceiveServicePid(Context context, int pid) {

    }

    /**
     * 接收 ClientId
     * @param context
     * @param cid
     */
    @Override
    public void onReceiveClientId(Context context, String cid) {
        // 设置设备Id
        Account.setPushId(cid);
        if (Account.isLogin()) {
            // 账户登录状态，进行一次PushId绑定
            // 没有登录是不能绑定PushId的
//            AccountHelper.bindPush(null);
            //TODO
        }
    }

    /**
     * 处理透传消息
     * @param context
     * @param msg
     */
    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage msg) {
        String appid = msg.getAppid();
        String taskid = msg.getTaskId();
        String messageid = msg.getMessageId();
        byte[] payload = msg.getPayload();
        String pkg = msg.getPkgName();
        String cid = msg.getClientId();
        if (payload != null) {
            String message = new String(payload);
            Log.i(GTIntentService.TAG, "GET_MSG_DATA:" + message);
            // 交给Factory处理
            dispatchPush(message);
        }
    }

    /**
     * 离线上线通知
     * @param context
     * @param b
     */
    @Override
    public void onReceiveOnlineState(Context context, boolean b) {

    }

    /**
     * 各种事件处理回执
     * @param context
     * @param gtCmdMessage
     */
    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage gtCmdMessage) {

    }

    @Override
    public void onNotificationMessageArrived(Context context, GTNotificationMessage gtNotificationMessage) {

    }

    @Override
    public void onNotificationMessageClicked(Context context, GTNotificationMessage gtNotificationMessage) {

    }

    /**
     * 处理推送来的消息
     *
     * @param str 消息
     */
    private static void dispatchPush(String str) {
        // 首先检查登录状态
        if (!Account.isLogin())
            return;

        PushModel model = PushModel.decode(str);
        if (model == null)
            return;

        Log.e(GTIntentService.TAG, model.toString());
        // 对推送集合进行遍历
        for (PushModel.Entity entity : model.getEntities()) {
            switch (entity.type) {
                case PushModel.ENTITY_TYPE_LOGOUT:
//                    instance.logout();
                    // 退出情况下，直接返回，并且不可继续
                    return;

                case PushModel.ENTITY_TYPE_MESSAGE: {
                    // 普通消息
                    MessageCard card = getGson().fromJson(entity.content, MessageCard.class);
                    MessageDispatcher.instance().dispatch(card);
                    break;
                }

                case PushModel.ENTITY_TYPE_ADD_FRIEND: {
                    // 好友添加
                    UserCard card = getGson().fromJson(entity.content, UserCard.class);
                    UserDispatcher.instance().dispatch(card);
                    break;
                }

                case PushModel.ENTITY_TYPE_ADD_GROUP: {
                    // 添加群

                    GroupCard card = getGson().fromJson(entity.content, GroupCard.class);
                    GroupDispatcher.instance().dispatch(card);
                    break;
                }

                case PushModel.ENTITY_TYPE_ADD_GROUP_MEMBERS:
                    break;
                case PushModel.ENTITY_TYPE_MODIFY_GROUP_MEMBERS: {
                    // 群成员变更, 回来的是一个群成员的列表
                    Type type = new TypeToken<List<GroupMemberCard>>() {
                    }.getType();
                    List<GroupMemberCard> card = getGson().fromJson(entity.content, type);
                    // 把数据集合丢到数据中心处理
                    GroupDispatcher.instance().dispatch(card.toArray(new GroupMemberCard[0]));
                    break;
                }
                case PushModel.ENTITY_TYPE_EXIT_GROUP_MEMBERS: {
                    // TODO 成员退出的推送
                }

            }
        }
    }

}
