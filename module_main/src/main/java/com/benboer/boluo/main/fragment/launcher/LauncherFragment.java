package com.benboer.boluo.main.fragment.launcher;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.benboer.boluo.common.app.AccountManager;
import com.benboer.boluo.common.app.BoLuo;
import com.benboer.boluo.common.app.IUserLoginChecker;
import com.benboer.boluo.common.base.fragment.SupportFragment;
import com.benboer.boluo.common.service.AccountService;
import com.benboer.boluo.common.util.storage.PreferenceUtil;
import com.benboer.boluo.common.util.timer.BaseTimerTask;
import com.benboer.boluo.common.util.timer.ITimerListener;
import com.benboer.boluo.main.R;
import com.benboer.boluo.main.serviceImpl.Account;
import com.benboer.boluo.main.serviceImpl.AccountServiceImpl;
import com.benboer.boluo.main.ui.launcher.ILauncherListener;
import com.benboer.boluo.main.ui.launcher.ScrollLauncherTag;

import java.text.MessageFormat;
import java.util.Timer;

/**
 * Created by BenBoerBoluojiushiwo on 2019/6/28.
 *
 * app启动页面
 */
public class LauncherFragment extends SupportFragment implements ITimerListener {

    private ILauncherListener mILauncherListener = null;

    public AppCompatTextView mTvTimer = null;
    private FrameLayout mSplashLayout;
    private Timer mTimer = null;
    private int mCount = 5;



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ILauncherListener){
            this.mILauncherListener = (ILauncherListener) context;
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_launcher;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View root) {
        initTimer();
        mSplashLayout = bind(R.id.splash_layout);
        mTvTimer = bind(R.id.tv_launcher_timer);
        mTvTimer.setOnClickListener(v -> onClickTimerView());
    }

    private void initTimer() {
        mTimer = new Timer();
        final BaseTimerTask task = new BaseTimerTask(this);
        mTimer.schedule(task, 0, 1000);
    }

    /**
     * 点击跳过直接跳转
     */
    private void onClickTimerView() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
            checkIsShowScroll();
        }
    }

    public void checkIsShowScroll() {
        if (!PreferenceUtil.getAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name())) {
            getSupportDelegate().startWithPop(new LauncherScrollFragment());
        } else {
            //用户是否登录
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

    @Override
    public void onTimer() {
        getProxyActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mTvTimer != null) {
                    mTvTimer.setText(MessageFormat.format("跳过 {0}s", mCount));
                    //隐藏splash界面
                    if (mCount < 2 && mSplashLayout.getVisibility() == View.VISIBLE){
                        mSplashLayout.setVisibility(View.INVISIBLE);
                    }
                    mCount--;
                    if (mCount < 1) {
                        if (mTimer != null) {
                            mTimer.cancel();
                            mTimer = null;
                        }
                        checkIsShowScroll();
//                        waitPushReceiverId();
                    }
                }
            }
        });
    }

    /**
     * 等待个推框架设置好PushId
     */
    private void waitPushReceiverId() {
        checkIsShowScroll();//todo
        if (Account.isLogin()) {
            // 已经登录情况下，判断是否绑定
            // 如果没有绑定则等待广播接收器进行绑定
            if (Account.isBind()) {
                //判断是否第一次登录
                checkIsShowScroll();
                return;
            }
        } else {
            // 没有登录
            // 如果拿到了PushId, 没有登录是不能绑定PushId的
            if (!TextUtils.isEmpty( Account.getPushId())) {
                // 跳转
                // 判断是否第一次登录
                checkIsShowScroll();
                return;
            }
        }

        // 循环等待
        getActivity().getWindow().getDecorView()
                .postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        waitPushReceiverId();
                    }
                }, 500);
    }

    @Override
    public void onDestroy() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        super.onDestroy();
    }

}
