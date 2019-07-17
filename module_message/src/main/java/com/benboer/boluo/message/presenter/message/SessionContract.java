package com.benboer.boluo.message.presenter.message;

import com.benboer.boluo.lib_db.db.Session;
import com.benboer.boluo.module_common.mvp.presenter.BaseContract;

/**
 * Created by BenBoerBoluojiushiwo on 2019/6/6.
 */
public interface SessionContract {

    interface Presenter extends BaseContract.Presenter{

    }

    interface View extends BaseContract.RecyclerView<Presenter, Session>{

    }
}
