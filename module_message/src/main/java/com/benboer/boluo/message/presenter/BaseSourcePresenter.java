package com.benboer.boluo.message.presenter;

import com.benboer.boluo.module_common.base.mvp.data.DataSource;
import com.benboer.boluo.module_common.base.mvp.data.DbDataSource;
import com.benboer.boluo.module_common.base.mvp.presenter.BaseContract;
import com.benboer.boluo.module_common.base.mvp.presenter.BaseRecyclerPresenter;

import java.util.List;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/29.
 */
public abstract class BaseSourcePresenter<Data,
                                          ViewModel,
                                          Source extends DbDataSource<Data>,
                                          View extends BaseContract.RecyclerView>
        extends BaseRecyclerPresenter<ViewModel, View>
        implements DataSource.SucceedCallback<List<Data>> {

    protected Source mSource;

    public BaseSourcePresenter(Source source, View view) {
        super(view);
        this.mSource = source;
    }

    @Override
    public void start() {
        super.start();
        if (mSource != null)
            mSource.load(this);
    }

    @Override
    public void destroy() {
        super.destroy();
        mSource.dispose();
        mSource = null;
    }
}
