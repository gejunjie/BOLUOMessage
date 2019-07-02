package com.benboer.boluo.widget.recycler;

/**
 * @ClassName: AdapterCallback
 * @Description:
 * @Author:  BenBoerBoluojiushiwo
 * @CreateDate: 2019/3/23 3:59 PM
 * @Version: 1.0
 */
public interface AdapterCallback<T> {

    void update(T data, RecyclerAdapter.ViewHolder<T> holder);
}
