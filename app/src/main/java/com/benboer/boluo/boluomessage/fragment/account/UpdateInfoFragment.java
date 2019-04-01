package com.benboer.boluo.boluomessage.fragment.account;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import com.benboer.boluo.boluomessage.R;
import com.benboer.boluo.boluomessage.fragment.media.GalleryFragment;
import com.benboer.boluo.common.app.Application;
import com.benboer.boluo.common.app.BaseFragment;
import com.benboer.boluo.common.widget.PortraitView;
import com.benboer.boluo.factory.Factory;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.file_descriptor.FileDescriptorUriLoader;
import com.yalantis.ucrop.UCrop;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * 用户更新界面
 *
 * Created by BenBoerBoluojiushiwo on 2019/3/28.
 */
public class UpdateInfoFragment extends BaseFragment {
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
        }).show(getChildFragmentManager(), GalleryFragment.class.getName());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            // 通过UCrop得到对应的Uri
            final Uri resultUri = UCrop.getOutput(data);
            if (resultUri != null) {
                loadPortrait(resultUri);
            }

        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
    }

    /**
     * 加载Uri到当前的头像中
     *
     * @param uri Uri
     */
    private void loadPortrait(Uri uri) {
        Glide.with(getContext())
                .load(uri)
                .asBitmap()
                .centerCrop()
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
}
