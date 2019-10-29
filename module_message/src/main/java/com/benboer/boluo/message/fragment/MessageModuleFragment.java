package com.benboer.boluo.message.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.benboer.boluo.common.base.fragment.SupportFragment;
import com.benboer.boluo.message.R;
import com.benboer.boluo.message.R2;
import com.benboer.boluo.message.fragment.main.ActiveFragment;
import com.benboer.boluo.message.fragment.main.ContactFragment;
import com.benboer.boluo.message.fragment.main.GroupFragment;
import com.benboer.boluo.message.fragment.search.SearchGroupFragment;
import com.benboer.boluo.message.fragment.search.SearchUserFragment;
import com.benboer.boluo.message.fragment.user.PersonalFragment;
import com.benboer.boluo.message.widget.PortraitView;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by BenBoerBoluojiushiwo on 2019/7/26.
 *
 * 消息聊天模块的根容器
 */
@Route(path = "/message/messageFragment")
public class MessageModuleFragment extends SupportFragment {
    @BindView(R2.id.appbar)
    AppBarLayout mLayAppbar;
    @BindView(R2.id.im_portrait)
    PortraitView mPortrait;
    @BindView(R2.id.tab_layout)
    TabLayout mTablayout;
    @BindView(R2.id.viewpager)
    ViewPager mViewPager;
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

//        mPortrait.setup(Glide.with(this), Account.getUser());//todo Account

    }

    @OnClick(R2.id.tv_search_view)
    void onSearch(){
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

    @OnClick(R2.id.im_portrait)
    void onPortraitClick() {
//        getSupportDelegate().start(PersonalFragment.newInstance(Account.getUserId()));//todo Account
    }

}