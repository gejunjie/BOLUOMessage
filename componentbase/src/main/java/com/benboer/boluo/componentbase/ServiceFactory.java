package com.benboer.boluo.componentbase;

import com.benboer.boluo.componentbase.empty_service.EmptyAccountService;
import com.benboer.boluo.componentbase.service.IAccountService;

/**
 * Created by BenBoerBoluojiushiwo on 2019/7/5.
 */
public class ServiceFactory {

    private IAccountService accountService;

    /**
     * 禁止外部创建 ServiceFactory 对象
     */
    private ServiceFactory() {
    }

    /**
     * 通过静态内部类方式实现 ServiceFactory 的单例
     */
    public static ServiceFactory getInstance() {
        return Inner.serviceFactory;
    }

    private static class Inner {
        private static ServiceFactory serviceFactory = new ServiceFactory();
    }

    /**
     * 接收 Login 组件实现的 Service 实例
     */
    public void setAccountService(IAccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * 返回 Login 组件的 Service 实例
     */
    public IAccountService getAccountService() {
        if (accountService == null) {
            accountService = new EmptyAccountService();
        }
        return accountService;
    }
}
