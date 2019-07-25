package com.benboer.boluo.message.fragment.main;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.benboer.boluo.factory.R;
import com.benboer.boluo.factory.R2;
import com.benboer.boluo.message.fragment.main.bottom.BaseBottomFragment;
import com.benboer.boluo.message.fragment.main.bottom.BottomItemBuilder;
import com.benboer.boluo.message.fragment.main.bottom.BottomTabBean;
import com.benboer.boluo.message.fragment.user.PersonalFragment;
import com.benboer.boluo.message.fragment.search.SearchGroupFragment;
import com.benboer.boluo.message.fragment.search.SearchUserFragment;
import com.benboer.boluo.message.fragment.user.UpdateInfoFragment;
import com.benboer.boluo.module_common.base.fragment.SupportFragment;
import com.benboer.boluo.message.widget.PortraitView;
import com.benboer.boluo.module_common.persistence.Account;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.appbar.AppBarLayout;

import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by BenBoerBoluojiushiwo on 2019/7/2.
 */
public class BottomFragment extends BaseBottomFragment {

    @BindView(R2.id.appbar)
    AppBarLayout mLayAppbar;

    @BindView(R2.id.im_portrait)
    PortraitView mPortrait;

    @BindView(R2.id.txt_title)
    TextView mTitle;

    @Override
    public LinkedHashMap<BottomTabBean, SupportFragment> setItems(BottomItemBuilder builder) {
        final LinkedHashMap<BottomTabBean, SupportFragment> items = new LinkedHashMap<>();
        items.put(new BottomTabBean("{fa-home}","主页"), new ActiveFragment());
        items.put(new BottomTabBean("{fa-home}","主页"), new GroupFragment());
        items.put(new BottomTabBean("{fa-home}","主页"), new ContactFragment());
        return items;
    }

    @Override
    public int setIndexFragment() {
        return 0;
    }

    @Override
    public int setClickColor() {
        return Color.GREEN;
    }

    @OnClick(R2.id.im_search)
    void onSearchMenuClick(){
        switch (mCurrentFragment){
            case 1 : getSupportDelegate().start(new SearchGroupFragment());
                break;
            case 2 : getSupportDelegate().start(new SearchUserFragment());
                break;
            default:
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (false){//TODO
//            if (!Account.isComplete()){
            getSupportDelegate().start(new UpdateInfoFragment());
        }
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View root) {
        super.onBindView(savedInstanceState, root);
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
        mPortrait.setup(Glide.with(this),
//                Account.getUser()
                ""//TODO
        );
    }

    @OnClick(R2.id.im_portrait)
    void onPortraitClick() {
        getSupportDelegate().start(PersonalFragment.newInstance(Account.getUserId()));
    }
}
