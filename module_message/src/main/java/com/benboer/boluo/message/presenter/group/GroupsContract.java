package com.benboer.boluo.message.presenter.group;

import com.benboer.boluo.message.db.Group;
import com.benboer.boluo.common.mvp.presenter.BaseContract;

/**
 * Created by BenBoerBoluojiushiwo on 2019/6/10.
 *
 * 我的群列表契约
 */
public interface GroupsContract {

    interface Presenter extends BaseContract.Presenter {

    }

    interface View extends BaseContract.RecyclerView<Presenter, Group> {

    }
}
