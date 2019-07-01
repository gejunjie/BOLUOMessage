package com.benboer.boluo.boluomessage;

import android.graphics.Color;

import com.benboer.boluo.core.fragment.bottom.BaseBottomFragment;
import com.benboer.boluo.core.fragment.bottom.BottomItemBuilder;
import com.benboer.boluo.core.fragment.bottom.BottomItemFragment;
import com.benboer.boluo.core.fragment.bottom.BottomTabBean;
import com.benboer.boluo.factory.main.TestFragment;

import java.util.LinkedHashMap;

/**
 * Created by BenBoerBoluojiushiwo on 2019/7/1.
 */
public class BottomFragment extends BaseBottomFragment {
    @Override
    public LinkedHashMap<BottomTabBean, BottomItemFragment> setItems(BottomItemBuilder builder) {
        final LinkedHashMap<BottomTabBean, BottomItemFragment> items = new LinkedHashMap<>();
        items.put(new BottomTabBean("{fa-home}","主页"), new TestFragment());

        return builder.addItems(items).build();
    }

    @Override
    public int setIndexFragment() {
        return 0;
    }

    @Override
    public int setClickColor() {
        return Color.parseColor("#ffff8800");
    }
}
