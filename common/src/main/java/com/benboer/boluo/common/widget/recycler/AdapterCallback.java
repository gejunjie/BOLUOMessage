package com.benboer.boluo.common.widget.recycler;

/**
 * @ClassName: AdapterCallback
 * @Description: java类作用描述
 * @Author:  BenBoerBoluojiushiwo
 * @CreateDate: 2019/3/23 3:59 PM
 * @Version: 1.0
 */
public interface AdapterCallback<T> {

    void update(T data, RecyclerAdapter.ViewHolder<T> holder);
}
