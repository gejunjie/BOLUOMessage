package com.benboer.boluo.factory.presenter.group;

import androidx.recyclerview.widget.DiffUtil;

import com.benboer.boluo.factory.data.group.GroupsDataSource;
import com.benboer.boluo.factory.data.helper.GroupHelper;
import com.benboer.boluo.factory.model.db.Group;
import com.benboer.boluo.factory.presenter.BaseSourcePresenter;
import com.benboer.boluo.factory.utils.DiffUiDataCallback;

import java.util.List;

/**
 * Created by BenBoerBoluojiushiwo on 2019/6/10.
 */
public class GroupsPresenter extends BaseSourcePresenter<Group, Group, GroupsDataSource, GroupsContract.View> {

    public GroupsPresenter(GroupsDataSource source, GroupsContract.View view) {
        super(source, view);
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
