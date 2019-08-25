package com.benboer.boluo.common.util.launchstarter;

public abstract class MainTask extends Task {

    @Override
    public boolean runOnMainThread() {
        return true;
    }

}
