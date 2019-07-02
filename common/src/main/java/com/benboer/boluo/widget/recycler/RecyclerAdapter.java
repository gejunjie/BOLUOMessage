package com.benboer.boluo.widget.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.benboer.boluo.common.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @ClassName: RecyclerAdapter
 * @Description: 自定义RecyclerView的适配器
 * @Author:  BenBoerBoluojiushiwo
 * @CreateDate: 2019/3/23 3:59 PM
 * @Version: 1.0
 */
public abstract class RecyclerAdapter<T>
        extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder<T>>
        implements View.OnClickListener, View.OnLongClickListener, AdapterCallback<T> {

    private final List<T> mDataList;
    private AdapterListener<T> mAdapterListener;

    public RecyclerAdapter(){
        this(null);
    }

    public RecyclerAdapter(AdapterListener<T> listener){
        this(new ArrayList<>(), listener);
    }

    public RecyclerAdapter(List<T> dataList, AdapterListener<T> listener){
        this.mDataList = dataList;
        this.mAdapterListener = listener;
    }

    /**
     * 复写默认的布局类型
     *
     * @param position 坐标
     * @return  布局类型
     */
    @Override
    public int getItemViewType(int position) {
        return getItemViewType(position, mDataList.get(position));
    }

    /**
     * 得到布局的类型
     *
     * @param postion
     * @param data
     * @return  XML文件的id
     */
    @LayoutRes
    protected abstract int getItemViewType(int postion, T data);

    /**
     * 根据viewType创建viewHolder
     * @param parent recyclerView
     * @param viewType 界面类型，约定为XML布局的id
     * @return
     */
    @NonNull
    @Override
    public ViewHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(viewType ,parent ,false);
        ViewHolder<T> viewHolder = onCreateViewHolder(view, viewType);
        //设置点击事件
        view.setOnClickListener(this);
//        viewHolder.itemView.setOnClickListener(this);
        view.setOnLongClickListener(this);
        //设置View的Tag为ViewHolder，进行双向绑定
        view.setTag(R.id.tag_recycler_holder, viewHolder);
        //界面注解绑定
        viewHolder.unbinder = ButterKnife.bind(viewHolder, view);
        viewHolder.callback = this;
        return viewHolder;
    }

    protected abstract ViewHolder<T> onCreateViewHolder(View root, int viewType);

    @Override
    public void onBindViewHolder(@NonNull ViewHolder<T> holder, int position) {
        T data = mDataList.get(position);
        holder.bind(data);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    /**
     * 返回整个集合
     *
     * @return List<Data>
     */
    public List<T> getItems() {
        return mDataList;
    }


    /**
     * 插入一条数据并通知更新
     * @param data
     */
    public void add(T data){
        mDataList.add(data);
//        notifyDataSetChanged();
        notifyItemInserted(mDataList.size() - 1);
    }

    /**
     * 插入一组数据并通知更新
     * @param dataList 数组集合
     */
    public void add(T... dataList){
        if(dataList != null && dataList.length > 0){
            int startPos = mDataList.size();
            Collections.addAll(mDataList, dataList);
            notifyItemRangeChanged(startPos, dataList.length);
        }
    }

    /**
     * 插入一组数据并通知更新
     * @param dataList Collection集合
     */
    public void add(Collection<T>  dataList){
        if(dataList != null && dataList.size() > 0){
            int startPos = mDataList.size();
            mDataList.addAll(dataList);
            notifyItemRangeChanged(startPos, dataList.size());
        }
    }

    /**
     * 删除数据
     */
    public void clear(){
        mDataList.clear();
        notifyDataSetChanged();
    }

    /**
     * 替换集合
     * @param dataList
     */
    public void replace(Collection<T> dataList){
        mDataList.clear();
        if (dataList == null && mDataList.size() == 0){
            return;
        }
        mDataList .addAll(dataList);
        notifyDataSetChanged();
    }

    @Override
    public void update(T data, ViewHolder<T> holder) {
        int pos = holder.getAdapterPosition();
        if (pos >= 0){
            mDataList.remove(pos);
            mDataList.add(pos, data);
            notifyItemChanged(pos);
        }
    }

    @Override
    public void onClick(View v) {
        ViewHolder viewHolder = (ViewHolder) v.getTag(R.id.tag_recycler_holder);
        if (mAdapterListener != null){
            int pos = viewHolder.getAdapterPosition();
            mAdapterListener.onItemClick(viewHolder, mDataList.get(pos));
        }
    }

    @Override
    public boolean onLongClick(View v) {
        ViewHolder viewHolder = (ViewHolder) v.getTag(R.id.tag_recycler_holder);
        if (mAdapterListener != null){
            int pos = viewHolder.getAdapterPosition();
            mAdapterListener.onItemLongClick(viewHolder, mDataList.get(pos));
            return true;
        }
        return false;
    }

    /**
     * 设置适配器监听
     * @param adapterListener
     */
    public void setAdapterListener(AdapterListener<T> adapterListener){
        this.mAdapterListener = adapterListener;
    }

    public interface AdapterListener<T>{

        void onItemClick(RecyclerAdapter.ViewHolder holder, T data);

        void onItemLongClick(RecyclerAdapter.ViewHolder holder, T data);
    }

    /**
     * 自定义viewHolder
     * @param <T>
     */
    public static abstract class ViewHolder<T> extends RecyclerView.ViewHolder{
        Unbinder unbinder;
        private AdapterCallback<T> callback;
        protected T data;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        /**
         * 绑定数据的触发
         * @param data
         */
        void bind(T data){
            this.data = data;
            onBind(data);
        }

        /**
         * 当触发绑定数据的时候的回调,必须复写
         * @param data
         */
        protected abstract void onBind(T data);

        /**
         * 对data进行更新操作
         * @param data
         */
        public void updateData(T data){
            if (this.callback != null){
                this.callback.update(data, this);
            }
        }
    }

    /**
     * 包装AapterListener
     * @param <T>
     */
    public static abstract class AdapterListenerImpl<T> implements AdapterListener<T>{

        @Override
        public void onItemClick(ViewHolder holder, T data) {

        }

        @Override
        public void onItemLongClick(ViewHolder holder, T data) {

        }
    }


}
