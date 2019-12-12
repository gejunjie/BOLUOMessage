package com.benboer.boluo.main;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.benboer.boluo.common.app.BoLuo;
import com.benboer.boluo.common.base.activity.ProxyActivity;
import com.benboer.boluo.common.base.fragment.SupportFragment;
import com.benboer.boluo.main.fragment.account.LoginFragment;
import com.benboer.boluo.main.fragment.bottom.BottomFragment;
import com.benboer.boluo.main.fragment.launcher.LauncherFragment;
import com.benboer.boluo.main.ui.launcher.ILauncherListener;

import qiu.niorgai.StatusBarCompat;

/**
 * Created by BenBoerBoluojiushiwo on 2019/6/26.
 */
public class MainActivity extends ProxyActivity implements ILauncherListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        BoLuo.getConfigurator().withActivity(this);
        StatusBarCompat.translucentStatusBar(this, true);
    }

    @Override
    public SupportFragment setRootFragment() {
        return new LauncherFragment();
    }

    @Override
    public void onLauncherFinish(int launcherTag) {
        switch (launcherTag){
            case SINGED:
                getSupportDelegate().startWithPop(new BottomFragment());
                break;
            case NOT_SINGED:
                getSupportDelegate().startWithPop(new LoginFragment());
                break;
            default:
                getSupportDelegate().startWithPop(new LoginFragment());
                break;
        }
    }

}
