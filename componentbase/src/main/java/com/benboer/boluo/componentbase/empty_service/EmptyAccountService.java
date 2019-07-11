package com.benboer.boluo.componentbase.empty_service;

import com.benboer.boluo.componentbase.service.IAccountService;

import java.util.Date;

/**
 * Created by BenBoerBoluojiushiwo on 2019/7/9.
 */
public class EmptyAccountService implements IAccountService {


    @Override
    public void saveUser(String id, String name, String phone, String portrait, String desc, int sex, int follows, int following, boolean isFollow, Date modifyAt) {

    }

    @Override
    public void logout() {

    }
}
