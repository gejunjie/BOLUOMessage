package com.benboer.boluo.core.fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.benboer.boluo.core.ui.camera.BoluoCamera;
import com.benboer.boluo.core.ui.camera.CameraImageBean;
import com.benboer.boluo.core.ui.camera.RequestCodes;
import com.benboer.boluo.core.ui.scanner.ScannerFragment;
import com.benboer.boluo.core.util.callback.CallbackManager;
import com.benboer.boluo.core.util.callback.CallbackType;
import com.benboer.boluo.core.util.callback.IGlobalCallback;
import com.blankj.utilcode.util.ToastUtils;
import com.yalantis.ucrop.UCrop;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by BenBoerBoluojiushiwo on 2019/6/25.
 */
@RuntimePermissions
public abstract class PermissionFragment extends BaseFragment {

    //不是直接调用该方法
    @NeedsPermission(Manifest.permission.CAMERA)
    void startCamera() {
        PermissionFragmentPermissionsDispatcher.checkStoryPermissionWithPermissionCheck(this);
    }

    //这个是真正调用的方法
    public void startCameraWithCheck() {
        PermissionFragmentPermissionsDispatcher.startCameraWithPermissionCheck(this);
    }

    //扫描二维码(不直接调用)
    @NeedsPermission(Manifest.permission.CAMERA)
    void startScan(BaseFragment fragment) {
        fragment.getSupportDelegate().startForResult(new ScannerFragment(), RequestCodes.SCAN);
    }

    public void startScanWithCheck(BaseFragment fragment) {
        PermissionFragmentPermissionsDispatcher.startScanWithPermissionCheck(this,fragment);
    }

    @OnPermissionDenied((Manifest.permission.CAMERA))
    void onCameraDenied() {
        ToastUtils.showShort("不允许拍照");
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    void onCameraNever() {
        ToastUtils.showShort("永久拒绝权限");
    }

    @OnShowRationale(Manifest.permission.CAMERA)
    void onCameraRationale(PermissionRequest request) {
        showRationaleDialog(request);
    }

    //存储权限
    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void checkStoryPermission() {
        BoluoCamera.start(this);
    }

    @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void onStorageRationale(final PermissionRequest request) {
        showRationaleDialog(request);
    }

    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void onStorageDenied() {
        ToastUtils.showShort("存储权限已拒绝");
    }

    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void onStorageNever() {
        ToastUtils.showShort("存储权限已被永久拒绝");
    }

    //不是直接调用此方法
    private void showRationaleDialog(final PermissionRequest request) {
        if (getContext() != null) {
            new AlertDialog.Builder(getContext())
                    .setPositiveButton("同意使用", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            request.proceed();
                        }
                    })
                    .setNegativeButton("拒绝使用", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            request.cancel();
                        }
                    })
                    .setCancelable(false)
                    .setMessage("权限管理")
                    .show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RequestCodes.TAKE_PHOTO:
                    final Uri resultUri = CameraImageBean.getInstance().getPath();
                    if (getContext() != null) {
                        UCrop.of(resultUri, resultUri)
                                .withMaxResultSize(400, 400)
                                .start(getContext(), this);
                    }
                    break;
                case RequestCodes.PICK_PHOTO:
                    if (data != null) {
                        final Uri pickPath = data.getData();
                        //从相册选择后需要有个路径存放裁剪后的图片
                        final String pickCropPath = BoluoCamera.createCropFile().getPath();
                        if (pickPath != null && getContext() != null) {
                            UCrop.of(pickPath, Uri.parse(pickCropPath))
                                    .withMaxResultSize(400, 400)
                                    .start(getContext(), this);
                        }
                    }
                    break;
                case RequestCodes.CROP_PHOTO:
                    final Uri cropUri = UCrop.getOutput(data);
                    //拿到裁剪后的数据进行处理
                    @SuppressWarnings("unchecked") final IGlobalCallback<Uri> callback = CallbackManager
                            .getInstance()
                            .getCallback(CallbackType.ON_CROP);
                    if (callback != null) {
                        callback.executeCallback(cropUri);
                    }
                    break;
                case RequestCodes.CROP_ERROR:
                    ToastUtils.showShort("裁剪出错！");
                    break;
                default:
                    break;
            }
        }
    }
}
