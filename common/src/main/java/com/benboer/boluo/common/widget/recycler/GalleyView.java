package com.benboer.boluo.common.widget.recycler;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.print.PageRange;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.benboer.boluo.common.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @ClassName: GalleyView
 * @Description: 图片选择器
 * @Author:  BenBoerBoluojiushiwo
 * @CreateDate: 2019/3/30 10:40 AM
 * @Version: 1.0
 */
public class GalleyView extends RecyclerView {

    private static final int MAX_IMAGE_COUNT = 3;

    private static final int LOADER_ID = 0x0100;

    private LoaderCallback mLoaderCallback = new LoaderCallback();

    private Adapter mAdapter = new Adapter();

    private List<Image> mSelectedImages = new LinkedList<>();

    private SelectedChangeListener mSelectedChangeListener;

    private static final int MIN_IMAGE_FILE_SIZE = 10*1024;

    public GalleyView(@NonNull Context context) {
        super(context);
        init();
    }

    public GalleyView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GalleyView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        setLayoutManager(new GridLayoutManager(getContext(), 4));
        setAdapter(mAdapter);
        mAdapter.setAdapterListener(new RecyclerAdapter.AdapterListenerImpl<Image>() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder holder, Image data) {
                //判断是否可以点击
                if (onItemSelectClick(data)){
                    holder.updateData(data);
                }
            }
        });
    }

    public String[] getSelectedPath(){
        String[] paths = new String[mSelectedImages.size()];
        int index = 0;
        for (Image image : mSelectedImages){
            paths[index++] = image.path;
        }
        return paths;
    }

    public void clear(){
        for (Image image : mSelectedImages){
            image.isSelected = false;
        }
        mSelectedImages.clear();
        mAdapter.notifyDataSetChanged();
    }

    /**
     *
     * @param loaderImager
     * @return loaderID 用于销毁loader
     */
    public int setUp(LoaderManager loaderImager, SelectedChangeListener listener){
        loaderImager.initLoader(LOADER_ID, null, mLoaderCallback);
        mSelectedChangeListener = listener;
        return LOADER_ID;
    }

    /**
     *点击事件
     *
     * @param image
     * @return true数据更改，需要刷新
     */
    private boolean onItemSelectClick(Image image){
        boolean notifyRefreash;
        if (mSelectedImages.contains(image)){
            image.isSelected = false;
            notifyRefreash = true;
        }else {
            if (mSelectedImages.size() >= MAX_IMAGE_COUNT){
                String string = getResources().getString(R.string.label_gallery_select_max_size);
                string = String.format(string, MAX_IMAGE_COUNT);//格式化
                Toast.makeText(getContext(), string, Toast.LENGTH_LONG).show();
                notifyRefreash = false;
            }else {
                mSelectedImages.add(image);
                image.isSelected = true;
                notifyRefreash = true;
            }
        }
        if (notifyRefreash){
            notifySelectChanged();
        }
        return true;
    }

    /**
     * 通知选中状态改变
     */
    private void notifySelectChanged(){
        SelectedChangeListener listener = mSelectedChangeListener;
        if (listener != null){
            listener.onSelectedCountChanged(mSelectedImages.size());
        }
    }

    private class Adapter extends RecyclerAdapter<Image>{

        @Override
        protected int getItemViewType(int postion, Image data) {
            return R.layout.cell_galley;
        }

        @Override
        protected ViewHolder<Image> onCreateViewHolder(View root, int viewType) {
            return new GalleyView.ViewHolder(root);
        }
    }

    private class ViewHolder extends RecyclerAdapter.ViewHolder<Image>{

        private ImageView mImageView;
        private View mShade;
        private CheckBox mCheckBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.im_image);
            mShade = itemView.findViewById(R.id.view_shade);
            mCheckBox = itemView.findViewById(R.id.cb_select);
        }

        @Override
        protected void onBind(Image data) {
            Glide.with(getContext())
                    .load(data.path)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .placeholder(R.color.green_300)
                    .into(mImageView);
            mShade.setVisibility(data.isSelected ?
                    VISIBLE : INVISIBLE);
            mCheckBox.setChecked(data.isSelected);
        }
    }

    private class LoaderCallback implements LoaderManager.LoaderCallbacks<Cursor>{

        private final String[] IMAGE_PROJECTION = new String[]{
                MediaStore.Images.Media._ID,        //id
                MediaStore.Images.Media.DATA,       //图片路径
                MediaStore.Images.Media.DATE_ADDED  //创建时间
        };

        @NonNull
        @Override
        public Loader onCreateLoader(int id, @Nullable Bundle args) {
            if (id == LOADER_ID){
                return new CursorLoader(getContext(),
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        IMAGE_PROJECTION,
                        null,
                        null,
                        IMAGE_PROJECTION[2] + "DESC");//倒叙查询
            }
            return null;
        }

        private void updateSource(List<Image> images){
            mAdapter.replace(images);
        }

        @Override
        public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
            List<Image> images = new ArrayList<>();
            if (data != null){
                int count = data.getCount();
                if (count > 0){
                    int indexId = data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]);
                    int indexPath = data.getColumnIndexOrThrow(IMAGE_PROJECTION[1]);
                    int indexDate = data.getColumnIndexOrThrow(IMAGE_PROJECTION[2]);
                    data.moveToFirst();
                    do {
                        int id = data.getInt(indexId);
                        String path = data.getString(indexPath);
                        long date = data.getLong(indexDate);

                        File file = new File(path);
                        if (!file.exists() || file.length() < MIN_IMAGE_FILE_SIZE){
                            continue;
                        }
                        Image image = new Image();
                        image.id = id;
                        image.path = path;
                        image.date = date;
                        images.add(image);
                    }while (data.moveToNext());
                }
            }
            updateSource(images);
        }

        @Override
        public void onLoaderReset(@NonNull Loader loader) {
            //界面清空
            updateSource(null);
        }
    }

    private static class Image{
        int id;
        String path;            //存储路径
        long date;              //创建日期
        boolean isSelected;     //是否被选中

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Image image = (Image) o;
            return Objects.equals(path, image.path);
        }

        @Override
        public int hashCode() {
            return Objects.hash(path);
        }
    }

    public interface SelectedChangeListener{
        void onSelectedCountChanged(int count);
    }
}
