package com.benboer.boluo.boluomessage.fragment.assist;

import com.benboer.boluo.common.app.BaseFragment;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * 权限申请弹出框
 *
 * Created by BenBoerBoluojiushiwo on 2019/3/28.
 */
public class PermissionsFragment extends BaseFragment implements EasyPermissions.PermissionCallbacks {
    @Override
    protected int getContentLayoutId() {
        return 0;
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }
}
