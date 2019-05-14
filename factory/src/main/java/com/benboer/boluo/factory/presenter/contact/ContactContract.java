package com.benboer.boluo.factory.presenter.contact;

import com.benboer.boluo.factory.model.db.User;
import com.benboer.boluo.factory.presenter.BaseContract;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/14.
 */
public class ContactContract {

    interface Presenter extends BaseContract.Presenter{

    }

    interface View extends BaseContract.RecyclerView<Presenter, User> {

    }
}
