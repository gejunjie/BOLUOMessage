package com.benboer.boluo.message.presenter.group;

import com.benboer.boluo.common.mvp.presenter.BaseContract;

/**
 * Created by BenBoerBoluojiushiwo on 2019/6/18.
 */
public interface GroupMemberAddContract {

    interface Presenter extends BaseContract.Presenter {
        // 提交成员
        void submit();

        // 更改一个Model的选中状态
        void changeSelect(GroupCreateContract.ViewModel model, boolean isSelected);
    }

    interface View extends BaseContract.RecyclerView<Presenter, GroupCreateContract.ViewModel> {
        // 添加群成员成功
        void onAddedSucceed();

        // 获取群的Id
        String getGroupId();
    }
}
