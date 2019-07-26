package com.benboer.boluo.message.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.benboer.boluo.common.base.fragment.SupportFragment;
import com.benboer.boluo.factory.R;
import com.benboer.boluo.factory.R2;
import com.benboer.boluo.message.fragment.main.ActiveFragment;
import com.benboer.boluo.message.fragment.main.ContactFragment;
import com.benboer.boluo.message.fragment.main.GroupFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by BenBoerBoluojiushiwo on 2019/7/26.
 *
 * 消息聊天模块的根容器
 */
public class MessageModuleFragment extends SupportFragment {
    @BindView(R2.id.tablayout)
    TabLayout mTablayout;
    @BindView(R2.id.viewpager)
    ViewPager mViewPager;

    List<String> mTitle;
    List<Fragment> mFragment;

    @Override
    public Object setLayout() {
        return R.layout.fragment_message_module;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View root) {
        mTitle = new ArrayList<>();
        mTitle.add("选项卡一");
        mTitle.add("选项卡二");
        mTitle.add("选项卡三");
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
        });

        mTablayout.setupWithViewPager(mViewPager);
    }
}
