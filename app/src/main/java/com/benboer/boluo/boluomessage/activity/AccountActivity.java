package com.benboer.boluo.boluomessage.activity;

import android.content.Context;
import android.content.Intent;

import com.benboer.boluo.boluomessage.R;
import com.benboer.boluo.boluomessage.fragment.account.UpdateInfoFragment;
import com.benboer.boluo.common.app.BaseActivity;

/**
 * Created by BenBoerBoluojiushiwo on 2019/3/28.
 */
public class AccountActivity extends BaseActivity {

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
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.lay_container, new UpdateInfoFragment())
                .commit();
    }
}
