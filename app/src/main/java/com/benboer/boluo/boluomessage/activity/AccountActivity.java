package com.benboer.boluo.boluomessage.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.benboer.boluo.boluomessage.R;
import com.benboer.boluo.boluomessage.fragment.account.UpdateInfoFragment;
import com.benboer.boluo.common.app.BaseActivity;
import com.yalantis.ucrop.UCrop;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Created by BenBoerBoluojiushiwo on 2019/3/28.
 */
public class AccountActivity extends BaseActivity {

    private Fragment mCurrentFragment;

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
        mCurrentFragment = new UpdateInfoFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.lay_container, new UpdateInfoFragment())
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

}
