package com.benboer.boluo.boluomessage.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.benboer.boluo.boluomessage.R;
import com.benboer.boluo.boluomessage.fragment.account.AccountTrigger;
import com.benboer.boluo.boluomessage.fragment.account.LoginFragment;
import com.benboer.boluo.boluomessage.fragment.account.RegisterFragment;
import com.benboer.boluo.common.app.BaseActivity;
import com.benboer.boluo.factory.persistence.Account;
import com.yalantis.ucrop.UCrop;

/**
 * Created by BenBoerBoluojiushiwo on 2019/3/28.
 */
public class AccountActivity extends BaseActivity implements AccountTrigger {

    private Fragment mCurFragment;
    private Fragment mLoginFragment;
    private Fragment mRegisterFragment;

    private Uri resultUri;

    public static void show(Context context){
        context.startActivity(new Intent(context, AccountActivity.class));
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_account;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        if (Account.isLogin()){
            mCurFragment = mLoginFragment = new LoginFragment();
        }else {
            mCurFragment = mRegisterFragment = new RegisterFragment();
        }
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.lay_container, mCurFragment)
                .commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        mCurrentFragment.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            resultUri = UCrop.getOutput(data);
        }
    }

    public Uri getResultUri(){
        return resultUri;
    }

    @Override
    public void triggerView() {
        Fragment fragment;
        if (mCurFragment == mLoginFragment) {
            if (mRegisterFragment == null) {
                //默认情况下为null，
                //第一次之后就不为null了
                mRegisterFragment = new RegisterFragment();
            }
            fragment = mRegisterFragment;
        } else {
            // 因为默认请求下mLoginFragment已经赋值，无须判断null
            fragment = mLoginFragment;
        }

        // 重新赋值当前正在显示的Fragment
        mCurFragment = fragment;
        // 切换显示
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.lay_container, fragment)
                .commit();
    }
}
