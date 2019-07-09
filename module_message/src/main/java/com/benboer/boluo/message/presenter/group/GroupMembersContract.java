package com.benboer.boluo.message.presenter.group;

import com.benboer.boluo.message.model.db.view.MemberUserModel;
import com.benboer.boluo.module_common.base.presenter.BaseContract;

/**
 * Created by BenBoerBoluojiushiwo on 2019/6/18.
 */
public interface GroupMembersContract {

    interface Presenter extends BaseContract.Presenter {

        void refresh();
    }

    interface View extends BaseContract.RecyclerView<Presenter, MemberUserModel> {
        // 获取群的ID
        String getGroupId();
    }
}
