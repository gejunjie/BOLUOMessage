package com.benboer.boluo.common.util.timer;

import java.util.TimerTask;

/**
 * Created by BenBoerBoluojiushiwo on 2019/6/26.
 */
public class BaseTimerTask extends TimerTask {
    private ITimerListener mITimerListener;

    public BaseTimerTask(ITimerListener timerListener) {
        this.mITimerListener = timerListener;
    }

    @Override
    public void run() {
        if (mITimerListener != null) {
            mITimerListener.onTimer();
        }
    }
}
