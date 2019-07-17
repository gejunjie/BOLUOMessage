package com.benboer.boluo.message.presenter.contact;

import com.benboer.boluo.lib_db.db.User;
import com.benboer.boluo.module_common.mvp.presenter.BaseContract;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/14.
 */
public interface ContactContract {

    interface Presenter extends BaseContract.Presenter{

    }

    interface View extends BaseContract.RecyclerView<Presenter, User> {

    }
}
