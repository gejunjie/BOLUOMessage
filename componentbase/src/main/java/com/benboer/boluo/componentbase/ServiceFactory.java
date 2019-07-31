package com.benboer.boluo.componentbase;

import com.benboer.boluo.componentbase.empty_service.EmptyAccountService;
import com.benboer.boluo.componentbase.empty_service.EmptyFragmentService;
import com.benboer.boluo.componentbase.service.IAccountService;
import com.benboer.boluo.componentbase.service.IFragmentService;
import com.benboer.boluo.componentbase.service.IPersonalFragmentService;

/**
 * Created by BenBoerBoluojiushiwo on 2019/7/5.
 */
public class ServiceFactory {

    private IAccountService accountService;
    private IFragmentService fragmentService;
    private IPersonalFragmentService personalFragmentService;
    /**
     * 禁止外部创建 ServiceFactory 对象
     */
    private ServiceFactory() {
    }

    public static ServiceFactory getInstance() {
        return Inner.serviceFactory;
    }

    private static class Inner {
        private static ServiceFactory serviceFactory = new ServiceFactory();
    }

    /**
     * 接收 Account 组件实现的 Service 实例
     */
    public void setAccountService(IAccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * 返回 Account 组件的 Service 实例
     */
    public IAccountService getAccountService() {
        if (accountService == null) {
            accountService = new EmptyAccountService();
        }
        return accountService;
    }

    /**
     * 接收 Personal 组件实现的 Service 实例
     */
    public void setPersonalService(IPersonalFragmentService personalFragmentService) {
        this.personalFragmentService = personalFragmentService;
    }

    /**
     * 返回 Personal 组件的 Service 实例
     */
    public IPersonalFragmentService getPersonalFragmentService() {
        if (personalFragmentService == null) {
//            personalFragmentService = new EmptyFragmentService();
        }
        return personalFragmentService;
    }

    /**
     * 接收 Login 组件实现的 Service 实例
     */
    public void setFragmentService(IFragmentService fragmentService) {
        this.fragmentService = fragmentService;
    }

    /**
     * 返回 Login 组件的 Service 实例
     */
    public IFragmentService getFragmentService() {
        if (fragmentService == null) {
            fragmentService = new EmptyFragmentService();
        }
        return fragmentService;
    }
}
