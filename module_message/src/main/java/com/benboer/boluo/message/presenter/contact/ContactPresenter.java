package com.benboer.boluo.message.presenter.contact;

import androidx.recyclerview.widget.DiffUtil;

import com.benboer.boluo.core.ui.recycler.RecyclerAdapter;
import com.benboer.boluo.module_common.base.mvp.data.DataSource;
import com.benboer.boluo.message.data.helper.UserHelper;
import com.benboer.boluo.message.data.user.ContactDataSource;
import com.benboer.boluo.message.data.user.ContactRepository;
import com.benboer.boluo.message.model.db.User;
import com.benboer.boluo.message.presenter.BaseSourcePresenter;
import com.benboer.boluo.module_common.utils.DiffUiDataCallback;

import java.util.List;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/14.
 */
public class ContactPresenter extends BaseSourcePresenter<User, User, ContactDataSource, ContactContract.View>
        implements ContactContract.Presenter, DataSource.SucceedCallback<List<User>> {

    public ContactPresenter(ContactContract.View view) {
        // 初始化数据仓库
        super(new ContactRepository(), view);
    }

    @Override
    public void start() {
        super.start();
        UserHelper.refreshContacts();
    }

    @Override
    public void onDataLoaded(List<User> users) {
        final ContactContract.View view = getView();
        if (view == null) return;
        RecyclerAdapter<User> adapter = view.getRecyclerAdapter();
        List<User> old = adapter.getItems();

        // 进行数据对比
        DiffUtil.Callback callback = new DiffUiDataCallback<>(old, users);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);

        // 调用基类方法进行界面刷新
        refreshData(result, users);
    }
}

