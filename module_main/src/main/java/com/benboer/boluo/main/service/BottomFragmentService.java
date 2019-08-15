package com.benboer.boluo.main.service;


import androidx.fragment.app.Fragment;

import com.benboer.boluo.componentbase.service.IBottomFragmentService;
import com.benboer.boluo.main.fragment.bottom.BottomFragment;

/**
 * Created by BenBoerBoluojiushiwo on 2019/7/11.
 *
 * 向其他业务模块暴露BottomFragment
 */
public class BottomFragmentService implements IBottomFragmentService {
    @Override
    public Fragment newBottomFragment() {
        return new BottomFragment();
    }
}
