package com.benboer.boluo.boluomessage;

import com.benboer.boluo.boluomessage.activity.MainActivity;
import com.benboer.boluo.common.app.BaseActivity;

/**
 * Created by BenBoerBoluojiushiwo on 2019/3/28.
 */
public class LaunchActivity extends BaseActivity {
    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_launch;
    }

    @Override
    protected void onResume() {
        super.onResume();
//        MainActivity.startMainActivity(this);
    }
}
