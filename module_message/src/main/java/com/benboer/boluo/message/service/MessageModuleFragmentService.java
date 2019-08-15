package com.benboer.boluo.message.service;

import androidx.fragment.app.Fragment;

import com.benboer.boluo.componentbase.service.IMessageModuleFragmentService;
import com.benboer.boluo.message.fragment.MessageModuleFragment;

/**
 * Created by BenBoerBoluojiushiwo on 2019/7/9.
 *
 * 向其他业务模块暴露
 */
public class MessageModuleFragmentService implements IMessageModuleFragmentService {

    @Override
    public Fragment newMessageModuleFragment() {
        return new MessageModuleFragment();
    }
}
