package com.benboer.boluo.message.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.benboer.boluo.common.base.fragment.SupportFragment;
import com.benboer.boluo.common.persistence.Account;
import com.benboer.boluo.factory.R;
import com.benboer.boluo.factory.R2;
import com.benboer.boluo.message.fragment.main.ActiveFragment;
import com.benboer.boluo.message.fragment.main.ContactFragment;
import com.benboer.boluo.message.fragment.main.GroupFragment;
import com.benboer.boluo.message.fragment.search.SearchGroupFragment;
import com.benboer.boluo.message.fragment.search.SearchUserFragment;
import com.benboer.boluo.message.fragment.user.PersonalFragment;
import com.benboer.boluo.message.widget.PortraitView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnFocusChange;

/**
 * Created by BenBoerBoluojiushiwo on 2019/7/26.
 *
 * 消息聊天模块的根容器
 */
public class MessageModuleFragment extends SupportFragment {
    @BindView(R2.id.appbar)
    AppBarLayout mLayAppbar;
    @BindView(R2.id.im_portrait)
    PortraitView mPortrait;
    @BindView(R2.id.tab_layout)
    TabLayout mTablayout;
    @BindView(R2.id.viewpager)
    ViewPager mViewPager;
    @BindView(R2.id.et_search_view)
    AppCompatEditText mEditText;
    List<String> mTabTitle;
    List<Fragment> mFragment;

    @Override
    public Object setLayout() {
        return R.layout.fragment_message_module;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View root) {
        mTabTitle = new ArrayList<>();
        mTabTitle.add("active");
        mTabTitle.add("contact");
        mTabTitle.add("group");
        mFragment = new ArrayList<>();
        mFragment.add(new ActiveFragment());
        mFragment.add(new ContactFragment());
        mFragment.add(new GroupFragment());
        mViewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return mTabTitle.get(position);
            }
        });
        mTablayout.setupWithViewPager(mViewPager);

        Glide.with(this)
                .load(R.drawable.bg_src_morning)
                .centerCrop()
                .into(new CustomViewTarget<AppBarLayout, Drawable>(mLayAppbar) {
                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {

                    }

                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        this.view.setBackground(resource.getCurrent());
                    }

                    @Override
                    protected void onResourceCleared(@Nullable Drawable placeholder) {

                    }
                });
        mPortrait.setup(Glide.with(this), Account.getUser());

    }

    @OnFocusChange(R2.id.et_search_view)
    void OnFocusChange(boolean hasFocus){
        if (hasFocus) {
            switch (mViewPager.getCurrentItem()) {
                case 1:
                    getParentFragments().start(new SearchGroupFragment());
                    break;
                case 2:
                    getParentFragments().start(new SearchUserFragment());
                    break;
                default:
                    break;
            }
        }
    }

//    @OnClick(R2.id.im_search)
//    void onSearchMenuClick() {
//        switch (mViewPager.getCurrentItem()) {
//            case 1:
//                getParentFragments().start(new SearchGroupFragment());
//                mEditText.clearFocus();
//                break;
//            case 2:
//                getParentFragments().start(new SearchUserFragment());
//                mEditText.clearFocus();
//                break;
//            default:
//                break;
//        }
//    }

    @OnClick(R2.id.im_portrait)
    void onPortraitClick() {
        getSupportDelegate().start(PersonalFragment.newInstance(Account.getUserId()));
    }

}