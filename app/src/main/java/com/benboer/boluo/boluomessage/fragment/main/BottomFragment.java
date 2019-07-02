package com.benboer.boluo.boluomessage.fragment.main;

import android.graphics.Color;

import com.benboer.boluo.core.fragment.SupportFragment;
import com.benboer.boluo.core.fragment.bottom.BaseBottomFragment;
import com.benboer.boluo.core.fragment.bottom.BottomItemBuilder;
import com.benboer.boluo.core.fragment.bottom.BottomTabBean;

import java.util.LinkedHashMap;

/**
 * Created by BenBoerBoluojiushiwo on 2019/7/2.
 */
public class BottomFragment extends BaseBottomFragment {
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
}
