package com.benboer.boluo.core.ui.camera;

import android.net.Uri;

import com.benboer.boluo.core.fragment.PermissionFragment;
import com.benboer.boluo.core.util.file.FileUtil;

/**
 * Created by Anding
 * 照相机调用类
 */
public class BoluoCamera {

    public static Uri createCropFile() {
        //剪裁图片的地址
        return Uri.parse
                (FileUtil.createFile("crop_image",
                        FileUtil.getFileNameByTime("IMG", "jpg")).getPath());
    }

    public static void start(PermissionFragment delegate) {
        new CameraHandler(delegate).beginCameraDialog();
    }
}
