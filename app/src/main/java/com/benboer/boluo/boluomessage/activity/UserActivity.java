package com.benboer.boluo.boluomessage.activity;

import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.benboer.boluo.boluomessage.R;
import com.benboer.boluo.factory.fragment.user.UpdateInfoFragment;
import com.benboer.boluo.common.app.BaseActivity;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/16.
 *
 *  用户信息界面
 *  提供用户信息修改
 */
public class UserActivity extends BaseActivity {

    private Fragment fragment;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_user;
    }
//
//    public static void show(Context context) {
//        context.startActivity(new Intent(context, UserActivity.class));
//    }

    @Override
    protected void initWidget() {
        super.initWidget();
        fragment = new UpdateInfoFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.lay_container,fragment)
                .commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        fragment.onActivityResult(requestCode, resultCode, data);
    }
}
