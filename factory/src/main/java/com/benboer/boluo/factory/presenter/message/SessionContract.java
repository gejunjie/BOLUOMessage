package com.benboer.boluo.factory.presenter.message;

import com.benboer.boluo.factory.model.db.Session;
import com.benboer.boluo.factory.presenter.BaseContract;

/**
 * Created by BenBoerBoluojiushiwo on 2019/6/6.
 */
public interface SessionContract {

    interface Presenter extends BaseContract.Presenter{

    }

    interface View extends BaseContract.RecyclerView<Presenter, Session>{

    }
}
