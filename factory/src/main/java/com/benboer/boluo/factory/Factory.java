package com.benboer.boluo.factory;

import com.benboer.boluo.common.app.Application;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by BenBoerBoluojiushiwo on 2019/4/1.
 */
public class Factory {
    private static final Factory instance;

    private static Executor executor;

    static {
         instance = new Factory();
    }

    /**
     * 返回全局的Application
     *
     * @return Application
     */
    public static Application app() {
        return Application.getInstance();
    }

    private Factory(){
        executor = Executors.newFixedThreadPool(4);
    }

    public static void runOnAsync(Runnable runnable){
        executor.execute(runnable);
    }

}
