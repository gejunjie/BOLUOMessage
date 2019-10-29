package com.benboer.boluo.message.presenter.message;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.benboer.boluo.common.service.AccountService;
import com.benboer.boluo.message.db.Group;
import com.benboer.boluo.message.db.Message;
import com.benboer.boluo.message.db.view.MemberUserModel;
import com.benboer.boluo.message.data.helper.GroupHelper;
import com.benboer.boluo.message.data.message.MessageGroupRepository;

import java.util.List;

/**
 * Created by BenBoerBoluojiushiwo on 2019/6/6.
 */
public class ChatGroupPresenter extends ChatPresenter<ChatContract.GroupView>
        implements ChatContract.Presenter {

    @Autowired(name = "/main/account_service")
    protected AccountService mAccountService;

    public ChatGroupPresenter(ChatContract.GroupView view, String receiverId) {
        // 数据源，View，接收者，接收者的类型
        super(new MessageGroupRepository(receiverId), view, receiverId, Message.RECEIVER_TYPE_GROUP);
        ARouter.getInstance().inject( this);
    }

    @Override
    public void start() {
        super.start();
        Group group = GroupHelper.findFromLocal(mReceiverId);
        if (group != null) {
            // 初始化操作
            ChatContract.GroupView view = getView();

            boolean isAdmin = mAccountService.getUserId().equalsIgnoreCase(group.getOwner().getId());
            view.showAdminOption(isAdmin);

            // 基础信息初始化
            view.onInit(group);

            // 成员初始化
            List<MemberUserModel> models = GroupHelper.getLatelyGroupMembers(group.getId());
            final long memberCount = GroupHelper.getGroupMemberCount(group.getId());
            // 没有显示的成员的数量
            long moreCount = memberCount - models.size();
            view.onInitGroupMembers(models, moreCount);
        }
    }
}
