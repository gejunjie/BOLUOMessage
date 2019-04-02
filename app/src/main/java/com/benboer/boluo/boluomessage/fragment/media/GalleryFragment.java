package com.benboer.boluo.boluomessage.fragment.media;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.benboer.boluo.boluomessage.R;
import com.benboer.boluo.common.widget.recycler.GalleryView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 图片选择fragment
 *
 * Created by BenBoerBoluojiushiwo on 2019/3/28.
 */
public class GalleryFragment extends BottomSheetDialogFragment
        implements GalleryView.SelectedChangeListener {

    private GalleryView mGallery;
    private OnSelectedListener mListener;

    public GalleryFragment(){

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new TransStatusBottomSheetDialog(getContext());
    }

    @Override
    public void onStart() {
        super.onStart();
        mGallery.setUp(getLoaderManager(), this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_gallery, container, false);
        mGallery = root.findViewById(R.id.galleryView);
        return root;
    }

    @Override
    public void onSelectedCountChanged(int count) {
        if (count > 0){
            dismiss();
            if (mListener != null){
                String[] pahts = mGallery.getSelectedPath();
                mListener.onSelectedImage(pahts[0]);
                //回收内存
                mListener = null;
            }
        }
    }

    /**
     * 图片选中监听
     * @param listener
     * @return
     */
    public GalleryFragment setOnSelectedListener(OnSelectedListener listener){
        mListener = listener;
        return this;
    }

    public interface OnSelectedListener{
        void onSelectedImage(String path);
    }

    private static class TransStatusBottomSheetDialog extends BottomSheetDialog{

        public TransStatusBottomSheetDialog(@NonNull Context context) {
            super(context);
        }

        public TransStatusBottomSheetDialog(@NonNull Context context, int theme) {
            super(context, theme);
        }

        protected TransStatusBottomSheetDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
            super(context, cancelable, cancelListener);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            final Window window = getWindow();
            if (window == null){
                return;
            }
            //屏幕高度
            int screenHeight = getContext().getResources().getDisplayMetrics().heightPixels;
            // 得到状态栏的高度
//            int statusHeight = getContext().getStatusBarHeight(getOwnerActivity());

//            // 计算dialog的高度并设置
//            int dialogHeight = screenHeight - statusHeight;
//            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
//                    dialogHeight <= 0 ? ViewGroup.LayoutParams.MATCH_PARENT : dialogHeight);
        }
    }

}
