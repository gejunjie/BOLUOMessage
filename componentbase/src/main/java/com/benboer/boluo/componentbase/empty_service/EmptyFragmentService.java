package com.benboer.boluo.componentbase.empty_service;

import androidx.fragment.app.Fragment;

import com.benboer.boluo.componentbase.service.IBottomFragmentService;

/**
 * Created by BenBoerBoluojiushiwo on 2019/7/11.
 */
public class EmptyFragmentService implements IBottomFragmentService {
    @Override
    public Fragment newBottomFragment() {
        return new Fragment();
    }
}
