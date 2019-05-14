package com.benboer.boluo.factory.presenter.contact;

import com.benboer.boluo.factory.model.db.User;
import com.benboer.boluo.factory.presenter.BasePresenter;
import com.raizlabs.android.dbflow.sql.language.SQLite;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/14.
 */
public class ContactPresenter extends BasePresenter<ContactContract.View>
        implements ContactContract.Presenter{

    public ContactPresenter(ContactContract.View view) {
        super(view);
    }

    @Override
    public void start() {
        super.start();

        SQLite.select()
                .from(User.class)
                .where()
    }
}

