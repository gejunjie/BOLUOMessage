package com.benboer.boluo.module_common.utils;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/14.
 */
public class DiffUiDataCallback<T extends DiffUiDataCallback.UiDataDiffer<T>>
        extends DiffUtil.Callback {

    private List<T> mOldList, mNewList;

    public DiffUiDataCallback(List<T> oldList, List<T> newList){
        mOldList = oldList;
        mNewList = newList;
    }

    @Override
    public int getOldListSize() {
        return mOldList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewList.size();
    }

    // 两个类是否就是同一个东西，比如Id相等的User
    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        T oldItem = mOldList.get(oldItemPosition);
        T newItem = mNewList.get(newItemPosition);
        return newItem.isSame(oldItem);
    }

    // 在经过相等判断后，进一步判断是否有数据更改
    // 比如，同一个用户的两个不同实例，其中的name字段不同
    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        T oldItem = mOldList.get(oldItemPosition);
        T newItem = mNewList.get(newItemPosition);
        return newItem.isUiContentSame(oldItem);
    }

    // 进行比较的数据类型
    public interface UiDataDiffer<T> {
        // 比较是否标示的是同一个数据
        boolean isSame(T old);

        // 旧的数据对比，内容是否相同
        boolean isUiContentSame(T old);
    }
}
