package com.benboer.boluo.boluomessage.activity;

import android.content.Context;
import android.content.Intent;

import com.benboer.boluo.boluomessage.R;
import com.benboer.boluo.common.app.BaseActivity;
import com.benboer.boluo.factory.model.Author;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/22.
 */
public class MessageActivity extends BaseActivity {
    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_message;
    }

    public static void show(Context context, Author author){

        context.startActivity(new Intent(context, MessageActivity.class));
    }

}