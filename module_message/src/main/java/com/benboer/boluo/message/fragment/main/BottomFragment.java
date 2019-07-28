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
import com.benboer.boluo.message.fragment.MessageModuleFragment;
import com.benboer.boluo.message.fragment.main.bottom.BaseBottomFragment;
import com.benboer.boluo.message.fragment.main.bottom.BottomItemBuilder;
import com.benboer.boluo.message.fragment.main.bottom.BottomTabBean;
import com.benboer.boluo.message.fragment.user.PersonalFragment;
import com.benboer.boluo.message.fragment.search.SearchGroupFragment;
import com.benboer.boluo.message.fragment.search.SearchUserFragment;
import com.benboer.boluo.message.fragment.user.UpdateInfoFragment;
import com.benboer.boluo.common.base.fragment.SupportFragment;
import com.benboer.boluo.message.widget.PortraitView;
import com.benboer.boluo.common.persistence.Account;
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

    @Override
    public LinkedHashMap<BottomTabBean, SupportFragment> setItems(BottomItemBuilder builder) {
        final LinkedHashMap<BottomTabBean, SupportFragment> items = new LinkedHashMap<>();
//        items.put(new BottomTabBean("{fa-home}","主页"), new ActiveFragment());
        items.put(new BottomTabBean("{fa-home}","主页"), new MessageModuleFragment());
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
            if (!Account.isComplete()){//检测账户信息是否完整
            getSupportDelegate().start(new UpdateInfoFragment());
        }
    }


}
