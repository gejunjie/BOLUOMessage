package com.benboer.boluo.core.fragment;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created by BenBoerBoluojiushiwo on 2019/6/25.
 */
public abstract class SupportFragment extends PermissionFragment {
    @SuppressWarnings("unchecked")
    public <T extends SupportFragment> T getParentFragments() {
        return (T) getParentFragment();
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }
}
