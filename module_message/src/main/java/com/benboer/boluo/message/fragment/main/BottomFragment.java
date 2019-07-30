package com.benboer.boluo.message.fragment.main;

import android.content.Context;
import android.graphics.Color;

import com.benboer.boluo.common.base.fragment.SupportFragment;
import com.benboer.boluo.common.persistence.Account;
import com.benboer.boluo.message.fragment.MessageModuleFragment;
import com.benboer.boluo.message.fragment.main.bottom.BaseBottomFragment;
import com.benboer.boluo.message.fragment.main.bottom.BottomItemBuilder;
import com.benboer.boluo.message.fragment.main.bottom.BottomTabBean;

import java.util.LinkedHashMap;

/**
 * Created by BenBoerBoluojiushiwo on 2019/7/2.
 */
public class BottomFragment extends BaseBottomFragment {

    @Override
    public LinkedHashMap<BottomTabBean, SupportFragment> setItems(BottomItemBuilder builder) {
        final LinkedHashMap<BottomTabBean, SupportFragment> items = new LinkedHashMap<>();
//        items.put(new BottomTabBean("{fa-home}","主页"), new ActiveFragment());
        items.put(new BottomTabBean("{fa-home}","聊天"), new MessageModuleFragment());
        items.put(new BottomTabBean("{fa-home}","广场"), new GroupFragment());
        items.put(new BottomTabBean("{fa-user}","我的"), new ContactFragment());
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
//            getSupportDelegate().start(new UpdateInfoFragment());//TODO
        }
    }


}
