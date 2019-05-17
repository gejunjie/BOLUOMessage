package com.benboer.boluo.boluomessage.fragment.assist;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.benboer.boluo.boluomessage.R;
import com.benboer.boluo.boluomessage.activity.AccountActivity;
import com.benboer.boluo.boluomessage.activity.MainActivity;
import com.benboer.boluo.factory.persistence.Account;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 权限申请弹出框
 *
 * Created by BenBoerBoluojiushiwo on 2019/3/28.
 */
public class PermissionsFragment extends BottomSheetDialogFragment
        implements EasyPermissions.PermissionCallbacks {

    /**
     * 权限回调的标示
     */
    private static final int RC = 0x0100;

    public PermissionsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_permissions, container, false);
        view.findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPerm();
            }
        });

        return view;

    }

    /**
     * 申请权限的方法
     */
    @AfterPermissionGranted(RC)
    private void requestPerm() {
        String[] perms = new String[]{
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO
        };

        if (EasyPermissions.hasPermissions(getContext(), perms)) {
//            Application.showToast(R.string.label_permission_ok);
            // Fragment 中调用getView得到根布局，前提是在onCreateView方法之后
            refreshState(getView());
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.title_assist_permissions),
                    RC, perms);
        }
    }

    /**
     * 刷新图片的状态
     *
     * @param root 根布局
     */
    private void refreshState(View root) {
        if (root == null)
            return;

        Context context = getContext();
        root.findViewById(R.id.im_state_permission_network)
                .setVisibility(haveNetworkPerm(context) ? View.VISIBLE : View.GONE);
        root.findViewById(R.id.im_state_permission_read)
                .setVisibility(haveReadPerm(context) ? View.VISIBLE : View.GONE);
        root.findViewById(R.id.im_state_permission_write)
                .setVisibility(haveWritePerm(context) ? View.VISIBLE : View.GONE);
        root.findViewById(R.id.im_state_permission_record_audio)
                .setVisibility(haveRecordAudioPerm(context) ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        // 界面显示的时候进行刷新
        refreshState(getView());
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if (Account.isLogin()) {
            MainActivity.show(getContext());
        } else {
            AccountActivity.show(getContext());
        }
        getActivity().finish();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog
                    .Builder(this)
                    .build()
                    .show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    /**
     * 获取是否有网络权限
     *
     * @param context 上下文
     * @return True则有
     */
    private static boolean haveNetworkPerm(Context context) {
        // 准备需要检查的网络权限
        String[] perms = new String[]{
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE
        };

        return EasyPermissions.hasPermissions(context, perms);
    }

    /**
     * 获取是否有外部存储读取权限
     *
     * @param context 上下文
     * @return True则有
     */
    private static boolean haveReadPerm(Context context) {
        // 准备需要检查的读取权限
        String[] perms = new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE
        };

        return EasyPermissions.hasPermissions(context, perms);
    }

    /**
     * 获取是否有外部存储写入权限
     *
     * @param context 上下文
     * @return True则有
     */
    private static boolean haveWritePerm(Context context) {
        // 准备需要检查的写入权限
        String[] perms = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        return EasyPermissions.hasPermissions(context, perms);
    }

    /**
     * 获取是否录音权限
     *
     * @param context 上下文
     * @return True则有
     */
    private static boolean haveRecordAudioPerm(Context context) {
        // 准备需要检查的录音权限
        String[] perms = new String[]{
                Manifest.permission.RECORD_AUDIO
        };

        return EasyPermissions.hasPermissions(context, perms);
    }

    /**
     * 检查是否具有所有的权限
     *
     * @param context Context
     * @param manager FragmentManager
     * @return 是否有权限
     */
    public static boolean haveAllPerm(Context context, FragmentManager manager) {
        // 检查是否具有所有的权限
        boolean haveAll = haveNetworkPerm(context)
                && haveReadPerm(context)
                && haveWritePerm(context)
                && haveRecordAudioPerm(context);

        // 如果没有则显示当前申请权限的界面
        if (!haveAll) {
            show(manager);
        }

        return haveAll;
    }

    private static void show(FragmentManager manager) {
        new PermissionsFragment()
                .show(manager, PermissionsFragment.class.getName());
    }
}
