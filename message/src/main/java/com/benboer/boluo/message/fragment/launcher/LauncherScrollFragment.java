package com.benboer.boluo.message.fragment.launcher;

import android.content.Context;
import android.os.Bundle;
import android.preference.Preference;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.benboer.boluo.core.app.AccountManager;
import com.benboer.boluo.core.app.IUserLoginChecker;
import com.benboer.boluo.core.fragment.SupportFragment;
import com.benboer.boluo.core.util.storage.PreferenceUtil;
import com.benboer.boluo.factory.R;
import com.benboer.boluo.ui.launcher.ILauncherListener;
import com.benboer.boluo.ui.launcher.LauncherHolderCreator;
import com.benboer.boluo.ui.launcher.ScrollLauncherTag;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;

import java.util.ArrayList;

/**
 * Created by BenBoerBoluojiushiwo on 2019/6/28.
 */
public class LauncherScrollFragment extends SupportFragment implements OnItemClickListener {

    private ConvenientBanner<Integer> mConvenientBanner = null;
    private static final ArrayList<Integer> INTEGERS = new ArrayList<>();
    private ILauncherListener mILauncherListener = null;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ILauncherListener){
            this.mILauncherListener = (ILauncherListener) context;
        }
    }

    @Override
    public Object setLayout() {
        mConvenientBanner = new ConvenientBanner<>(getContext());

        return mConvenientBanner;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View root) {
        initBanner();
    }

    private void initBanner() {
        if (INTEGERS.size() == 0) {
            INTEGERS.add(R.mipmap.launcher_01);
            INTEGERS.add(R.mipmap.launcher_02);
            INTEGERS.add(R.mipmap.launcher_03);
            INTEGERS.add(R.mipmap.launcher_04);
            INTEGERS.add(R.mipmap.launcher_05);
        }

        mConvenientBanner
                .setPages(new LauncherHolderCreator(), INTEGERS)
                .setPageIndicator(new int[]{R.drawable.dot_normal, R.drawable.dot_focus})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(this)
                .setCanLoop(false);
    }

    @Override
    public void onItemClick(int position) {
        if (position == INTEGERS.size() - 1) {
            PreferenceUtil.setAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name(), true);
            //检查用户是否已经登陆，跳转下一个页面
            AccountManager.checkAccount(new IUserLoginChecker() {
                @Override
                public void onSignIn() {
                    if (mILauncherListener != null) {
                        mILauncherListener.onLauncherFinish(ILauncherListener.SINGED);
                    }
                }

                @Override
                public void onNotSignIn() {
                    if (mILauncherListener != null) {
                        mILauncherListener.onLauncherFinish(ILauncherListener.NOT_SINGED);
                    }
                }
            });
        }
    }
}
