package com.benboer.boluo.main;

import android.os.Bundle;
import android.util.ArrayMap;

import androidx.annotation.Nullable;

import com.benboer.boluo.common.app.BoLuo;
import com.benboer.boluo.common.app.ConfigKeys;
import com.benboer.boluo.common.base.activity.ProxyActivity;
import com.benboer.boluo.common.base.fragment.SupportFragment;
import com.benboer.boluo.componentbase.service.IBottomFragmentService;
import com.benboer.boluo.main.fragment.launcher.LauncherFragment;
import com.benboer.boluo.main.ui.launcher.ILauncherListener;

import me.yokeyword.fragmentation.ISupportFragment;
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
//        getSupportDelegate().startWithPop(new BottomFragment());//TODO
        ArrayMap map = BoLuo.getConfiguration(ConfigKeys.SERVICE_FRAGMENT);
        IBottomFragmentService service = (IBottomFragmentService) map.get(IBottomFragmentService.class);
        getSupportDelegate().startWithPop((ISupportFragment) service.newBottomFragment());
//        switch (launcherTag){
//            case SINGED:
////                getSupportDelegate().startWithPop(
////                        (ISupportFragment) ServiceFactory.getInstance().getFragmentService().newBottomFragment());
//                HashMap map = BoLuo.getConfiguration(ConfigKeys.SERVICE_FRAGMENT);
//
//                getSupportDelegate().startWithPop((ISupportFragment) map.get(IBottomFragmentService.class));
//                break;
//            case NOT_SINGED:
//                getSupportDelegate().startWithPop(new LoginFragment());
//                break;
//            default:
//                break;
//        }
    }

}
