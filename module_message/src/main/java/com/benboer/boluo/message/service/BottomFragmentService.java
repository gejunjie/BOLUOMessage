package com.benboer.boluo.message.service;


import androidx.fragment.app.Fragment;

import com.benboer.boluo.componentbase.service.IFragmentService;
import com.benboer.boluo.message.fragment.main.BottomFragment;

/**
 * Created by BenBoerBoluojiushiwo on 2019/7/11.
 */
public class BottomFragmentService implements IFragmentService {
    @Override
    public Fragment newBottomFragment() {
        return new BottomFragment();
    }
}
