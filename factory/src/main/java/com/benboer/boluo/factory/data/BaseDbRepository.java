package com.benboer.boluo.factory.data;

import androidx.annotation.NonNull;

import com.benboer.boluo.factory.data.helper.DbHelper;
import com.benboer.boluo.factory.model.db.BaseDbModel;
import com.benboer.boluo.utils.CollectionUtil;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;

import net.qiujuer.genius.kit.reflect.Reflector;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/23.
 */
public abstract class BaseDbRepository<Data extends BaseDbModel<Data>>
        implements DbDataSource<Data>,
                   DbHelper.ChangedListener<Data>,
                   QueryTransaction.QueryResultListCallback<Data> {

    private SucceedCallback<List<Data>> callback; // 和Presenter交互的回调
    protected final LinkedList<Data> dataList = new LinkedList<>(); // 当前缓存的数据
    private Class<Data> dataClass; // 当前范型对应的真实的Class信息

    public BaseDbRepository(){
        // 拿当前类的范型数组信息
        Type[] types = Reflector.getActualTypeArguments(BaseDbRepository.class, this.getClass());
        dataClass = (Class<Data>) types[0];
    }

    @Override
    public void load(SucceedCallback<List<Data>> callback) {
        this.callback = callback;
        // 进行数据库监听操作
        registerDbChangedListener();
    }

    @Override
    public void dispose() {
        this.callback = null;
        DbHelper.removeListener(dataClass, this);
        dataList.clear();
    }

    @Override
    public void onDataSave(Data... list) {
        boolean isChanged = false;
        // 当数据库数据变更的操作
        for (Data data : list) {
            // 是关注的人，同时不是我自己
            if (isRequired(data)) {
                insertOrUpdate(data);
                isChanged = true;
            }
        }
        // 有数据变更，则进行界面刷新
        if (isChanged)
            notifyDataChange();
    }

    @Override
    public void onDataDelete(Data... list) {
        // 在删除情况下不用进行过滤判断
        // 但数据库数据删除的操作
        boolean isChanged = false;
        for (Data data : list) {
            if (dataList.remove(data))
                isChanged = true;
        }

        // 有数据变更，则进行界面刷新
        if (isChanged)
            notifyDataChange();
    }

    @Override
    public void onListQueryResult(QueryTransaction transaction, @NonNull List<Data> tResult) {
        // 数据库加载数据成功
        if (tResult.size() == 0) {
            dataList.clear();
            notifyDataChange();
            return;
        }

        // 转变为数组
        Data[] users = CollectionUtil.toArray(tResult, dataClass);
        // 回到数据集更新的操作中
        onDataSave(users);
    }

    // 插入或者更新
    private void insertOrUpdate(Data data) {
        int index = indexOf(data);
        if (index >= 0) {
            replace(index, data);
        } else {
            insert(data);
        }
    }

    // 更新操作，更新某个坐标下的数据
    protected void replace(int index, Data data) {
        dataList.remove(index);
        dataList.add(index, data);
    }

    // 添加方法
    protected void insert(Data data) {
        dataList.add(data);
    }


    // 查询一个数据是否在当前的缓存数据中，如果在则返回坐标
    protected int indexOf(Data newData) {
        int index = -1;
        for (Data data : dataList) {
            index++;
            if (data.isSame(newData)) {
                return index;
            }
        }
        return -1;
    }

    /**
     * 检查一个User是否是需要关注的数据
     *
     * @param data Data
     * @return True是我关注的数据
     */
    protected abstract boolean isRequired(Data data);

    protected void registerDbChangedListener(){
        DbHelper.addChangedListener(dataClass, this);
    }

    private void notifyDataChange(){
        SucceedCallback<List<Data>> callback = this.callback;
        if (callback != null){
            callback.onDataLoaded(dataList);
        }
    }

}
