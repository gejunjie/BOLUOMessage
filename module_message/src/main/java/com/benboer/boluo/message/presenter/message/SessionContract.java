package com.benboer.boluo.message.presenter.message;

import com.benboer.boluo.message.model.db.Session;
import com.benboer.boluo.module_common.base.mvp.presenter.BaseContract;

/**
 * Created by BenBoerBoluojiushiwo on 2019/6/6.
 */
public interface SessionContract {

    interface Presenter extends BaseContract.Presenter{

    }

    interface View extends BaseContract.RecyclerView<Presenter, Session>{

    }
}
