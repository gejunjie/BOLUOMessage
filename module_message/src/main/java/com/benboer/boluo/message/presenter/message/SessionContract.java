package com.benboer.boluo.message.presenter.message;

import com.benboer.boluo.db.db.Session;
import com.benboer.boluo.common.mvp.presenter.BaseContract;

/**
 * Created by BenBoerBoluojiushiwo on 2019/6/6.
 */
public interface SessionContract {

    interface Presenter extends BaseContract.Presenter{

    }

    interface View extends BaseContract.RecyclerView<Presenter, Session>{

    }
}
