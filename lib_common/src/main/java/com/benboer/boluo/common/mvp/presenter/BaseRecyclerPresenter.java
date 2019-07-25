package com.benboer.boluo.common.mvp.presenter;

import androidx.recyclerview.widget.DiffUtil;

import com.benboer.boluo.common.ui.recycler.RecyclerAdapter;

import java.util.List;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/29.
 */
public class BaseRecyclerPresenter<ViewModel, View extends BaseContract.RecyclerView>
        extends BasePresenter<View>{

    public BaseRecyclerPresenter(View view) {
        super(view);
    }

    /**
     * 刷新一堆新数据到界面中
     *
     * @param dataList 新数据
     */
    protected void refreshData(final List<ViewModel> dataList) {
         View view = getView();
            if (view == null)
                return;

            // 基本的更新数据并刷新界面
            RecyclerAdapter<ViewModel> adapter = view.getRecyclerAdapter();
            adapter.replace(dataList);
            view.onAdapterDataChanged();
    }

    /**
     * 刷新界面操作，该操作可以保证执行方法在主线程进行
     *
     * @param diffResult 一个差异的结果集
     * @param dataList   具体的新数据
     */
    protected void refreshData(final DiffUtil.DiffResult diffResult, final List<ViewModel> dataList) {
         BaseRecyclerPresenter.this.refreshDataOnUiThread(diffResult, dataList);
    }


    private void refreshDataOnUiThread(final DiffUtil.DiffResult diffResult, final List<ViewModel> dataList) {
        View view = getView();
        if (view == null)
            return;
        // 基本的更新数据并刷新界面
        RecyclerAdapter<ViewModel> adapter = view.getRecyclerAdapter();
        // 改变数据集合并不通知界面刷新
        adapter.getItems().clear();
        adapter.getItems().addAll(dataList);
        // 通知界面刷新占位布局
        view.onAdapterDataChanged();
        // 进行增量更新
        diffResult.dispatchUpdatesTo(adapter);
    }

}
