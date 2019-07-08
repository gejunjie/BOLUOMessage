package com.benboer.boluo.message.presenter.message;

import androidx.recyclerview.widget.DiffUtil;

import com.benboer.boluo.message.data.message.SessionDataSource;
import com.benboer.boluo.message.data.message.SessionRepository;
import com.benboer.boluo.message.model.db.Session;
import com.benboer.boluo.message.presenter.BaseSourcePresenter;
import com.benboer.boluo.module_common.utils.DiffUiDataCallback;

import java.util.List;

/**
 * Created by BenBoerBoluojiushiwo on 2019/6/6.
 */
public class SessionPresenter extends BaseSourcePresenter<Session,
                                                          Session,
                                                          SessionDataSource,
                                                          SessionContract.View>
        implements SessionContract.Presenter{

    public SessionPresenter(SessionContract.View view) {
        super(new SessionRepository(), view);
    }

    @Override
    public void onDataLoaded(List<Session> sessions) {
        SessionContract.View view = getView();
        if (view == null) return;
        List<Session> old = view.getRecyclerAdapter().getItems();

        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUiDataCallback<>(old, sessions));
        refreshData(result, sessions);
    }
}
