package com.benboer.boluo.login.ui.launcher;

/**
 * Created by BenBoerBoluojiushiwo on 2019/6/28.
 */
public interface ILauncherListener {
    /**
     * 用户已经登录
     */
    int SINGED = 0;

    /**
     * 用户还未登录
     */
    int NOT_SINGED = 1;

    void onLauncherFinish(int launcherTag);
}
