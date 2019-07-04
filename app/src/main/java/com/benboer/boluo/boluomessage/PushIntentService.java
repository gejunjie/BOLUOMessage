package com.benboer.boluo.boluomessage;

import android.content.Context;
import android.util.Log;

import com.benboer.boluo.message.Factory;
import com.benboer.boluo.message.data.helper.AccountHelper;
import com.benboer.boluo.message.persistence.Account;
import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;

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
            AccountHelper.bindPush(null);
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
            Log.i(TAG, "GET_MSG_DATA:" + message);
            // 交给Factory处理
            Factory.dispatchPush(message);
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

}
