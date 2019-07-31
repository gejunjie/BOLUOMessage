package com.benboer.boluo.main.service;

import androidx.fragment.app.Fragment;

import com.benboer.boluo.componentbase.service.IPersonalFragmentService;
import com.benboer.boluo.main.fragment.personal.PersonalCenterFragment;

/**
 * Created by BenBoerBoluojiushiwo on 2019/7/31.
 */
public class PersonalFragmentService implements IPersonalFragmentService {
    @Override
    public Fragment newPersonalFragment() {
        return new PersonalCenterFragment();
    }
}
