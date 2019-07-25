package com.benboer.boluo.message.presenter.group;

import androidx.recyclerview.widget.DiffUtil;

import com.benboer.boluo.db.DiffUiDataCallback;
import com.benboer.boluo.db.db.Group;
import com.benboer.boluo.message.data.group.GroupsDataSource;
import com.benboer.boluo.message.data.group.GroupsRepository;
import com.benboer.boluo.message.data.helper.GroupHelper;
import com.benboer.boluo.message.presenter.BaseSourcePresenter;

import java.util.List;

/**
 * Created by BenBoerBoluojiushiwo on 2019/6/10.
 */
public class GroupsPresenter extends BaseSourcePresenter<Group,
                                                         Group,
                                                         GroupsDataSource,
                                                         GroupsContract.View>
            implements GroupsContract.Presenter {

    public GroupsPresenter(GroupsContract.View view) {
        super(new GroupsRepository(), view);
    }

    @Override
    public void start() {
        super.start();
        GroupHelper.refreshGroups();
    }

    @Override
    public void onDataLoaded(List<Group> groups) {
        final GroupsContract.View view = getView();
        if (view == null)
            return;

        // 对比差异
        List<Group> old = view.getRecyclerAdapter().getItems();
        DiffUiDataCallback<Group> callback = new DiffUiDataCallback<>(old, groups);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);

        // 界面刷新
        refreshData(result, groups);
    }
}
