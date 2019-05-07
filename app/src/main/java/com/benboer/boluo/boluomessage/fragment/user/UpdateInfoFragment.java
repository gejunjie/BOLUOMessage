package com.benboer.boluo.boluomessage.fragment.user;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import com.benboer.boluo.boluomessage.R;
import com.benboer.boluo.boluomessage.activity.AccountActivity;
import com.benboer.boluo.boluomessage.fragment.media.GalleryFragment;
import com.benboer.boluo.common.app.Application;
import com.benboer.boluo.common.app.BaseFragment;
import com.benboer.boluo.common.widget.PortraitView;
import com.benboer.boluo.factory.Factory;
import com.bumptech.glide.Glide;
import com.yalantis.ucrop.UCrop;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 用户更新界面
 *
 * Created by BenBoerBoluojiushiwo on 2019/3/28.
 */
public class UpdateInfoFragment extends BaseFragment {

    private boolean refreshPortrait;
    private Uri resultUri;


    @BindView(R.id.im_portrait)
    PortraitView mPortraitView;

    public UpdateInfoFragment(){

    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_update_info;
    }

    @OnClick(R.id.im_portrait)
    void onPortraitViewClick(){
        new GalleryFragment().setOnSelectedListener(new GalleryFragment.OnSelectedListener() {
            @Override
            public void onSelectedImage(String path) {
                UCrop.Options options = new UCrop.Options();
                options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
                options.setCompressionQuality(96);//压缩后的图片精度
                File tempPath = Application.getPortraitTmpFile();

                // 发起剪切
                UCrop.of(Uri.fromFile(new File(path)), Uri.fromFile(tempPath))
                        .withAspectRatio(1, 1) // 1比1比例
                        .withMaxResultSize(520, 520) // 返回最大的尺寸
                        .withOptions(options) // 相关参数
                        .start(getActivity());
            }
        })
                // show 的时候建议使用getChildFragmentManager
                // tag GalleryFragment class 名
                .show(getChildFragmentManager(), GalleryFragment.class.getName());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
//
//            // 通过UCrop得到对应的Uri
//            final Uri resultUri = UCrop.getOutput(data);
//            if (resultUri != null) {
//                if (isAdded()){
//                    loadPortrait(resultUri);
//                }
////                loadPortrait(resultUri);
//                refreshPortrait = true;
//                this.resultUri = resultUri;
//            }
//        } else if (resultCode == UCrop.RESULT_ERROR) {
//            final Throwable cropError = UCrop.getError(data);
//        }
//    }

    /**
     * 加载Uri到当前的头像中
     *
     * @param uri Uri
     */
    private void loadPortrait(Uri uri) {

            Glide.with(this)
                    .load(uri)
                    .centerCrop()
//                .apply(bitmapTransform(new CropCircleTransformation()))
                    .into(mPortraitView);



        // 拿到本地文件的地址
        final String localPath = uri.getPath();
        Log.e("TAG", "localPath:" + localPath);


        Factory.runOnAsync(new Runnable() {
            @Override
            public void run() {
//                String url = UploadHelper.uploadPortrait(localPath);
//                Log.e("TAG", "url:" + url);
            }
        });
    }

    //Todo 在Activity的onActivityResult回调时无法更新图片,glide无法拿到fragment依附的activity
    @Override
    public void onResume() {
        super.onResume();
        AccountActivity accountActivity = (AccountActivity) getActivity();
        Uri uri = accountActivity.getResultUri();
        if (uri != null){
            loadPortrait(uri);
        }
    }

}
