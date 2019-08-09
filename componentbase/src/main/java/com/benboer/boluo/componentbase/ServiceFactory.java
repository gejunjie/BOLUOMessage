package com.benboer.boluo.componentbase;

import androidx.fragment.app.Fragment;

import com.benboer.boluo.componentbase.empty_service.EmptyAccountService;
import com.benboer.boluo.componentbase.service.IAccountService;
import com.benboer.boluo.componentbase.service.IBottomFragmentService;
import com.benboer.boluo.componentbase.service.IPersonalFragmentService;

import java.util.HashMap;


/**
 * Created by BenBoerBoluojiushiwo on 2019/7/5.
 */
public class ServiceFactory{

//    private IAccountService accountService;
//    private IBottomFragmentService bottomFragmentService;
//    private IPersonalFragmentService personalFragmentService;
//
//
//
//    private ServiceFactory() {
//    }
//
//    public static ServiceFactory getInstance() {
//        return Inner.serviceFactory;
//    }
//
//    private static class Inner {
//        private static ServiceFactory serviceFactory = new ServiceFactory();
//    }
//
//    /**
//     * 接收 Account 组件实现的 Service 实例
//     */
//    public void setAccountService(IAccountService accountService) {
//        this.accountService = accountService;
//    }
//
//    /**
//     * 返回 Account 组件的 Service 实例
//     */
//    public IAccountService getAccountService() {
//        if (accountService == null) {
//            accountService = new EmptyAccountService();
//        }
//        return accountService;
//    }
//
//    /**
//     * 接收 Personal Fragment 组件实现的 Service 实例
//     */
//    public void setPersonalService(IPersonalFragmentService personalFragmentService) {
//        this.personalFragmentService = personalFragmentService;
//    }
//
//    /**
//     * 返回 Personal Fragment 组件的 Service 实例
//     */
//    public IPersonalFragmentService getPersonalFragmentService() {
//        if (personalFragmentService == null) {
////            personalFragmentService = new EmptyFragmentService();
//        }
//        return personalFragmentService;
//    }
//
//    /**
//     * 接收 Fragment根布局 组件实现的 Service 实例
//     */
//    public void setFragmentService(IBottomFragmentService fragmentService) {
//        this.bottomFragmentService = fragmentService;
//    }
//
//    /**
//     * 返回 Fragment根布局 组件的 Service 实例
//     */
//    public IBottomFragmentService getFragmentService() {
//        if (bottomFragmentService == null) {
////            bottomFragmentService = new EmptyFragmentService();
//        }
//        return bottomFragmentService;
//    }
}
