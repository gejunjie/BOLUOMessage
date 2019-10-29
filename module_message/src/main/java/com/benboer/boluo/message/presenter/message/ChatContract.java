package com.benboer.boluo.message.presenter.message;

import com.benboer.boluo.message.db.Group;
import com.benboer.boluo.message.db.Message;
import com.benboer.boluo.message.db.User;
import com.benboer.boluo.message.db.view.MemberUserModel;
import com.benboer.boluo.common.mvp.presenter.BaseContract;

import java.util.List;

/**
 * Created by BenBoerBoluojiushiwo on 2019/6/6.
 */
public interface ChatContract {
    interface Presenter extends BaseContract.Presenter{
        // 发送文字
        void pushText(String content);

        // 发送语音
        void pushAudio(String path);

        // 发送图片
        void pushImages(String[] paths);

        // 重新发送一个消息，返回是否调度成功
        boolean rePush(Message message);
    }

    interface View<InitModel> extends BaseContract.RecyclerView<Presenter, Message>{
        void onInit(InitModel model);
    }

    // 人聊天的界面
    interface UserView extends View<User> {

    }

    // 群聊天的界面
    interface GroupView extends View<Group> {
        // 显示管理员菜单
        void showAdminOption(boolean isAdmin);

        // 初始化成员信息
        void onInitGroupMembers(List<MemberUserModel> members, long moreCount);
    }
}
