package com.benboer.boluo.core.fragment.bottom;

import com.benboer.boluo.core.fragment.SupportFragment;

import java.util.LinkedHashMap;

/**
 * Created by BenBoerBoluojiushiwo on 2019/7/1.
 */
public class BottomItemBuilder {

    private final LinkedHashMap<BottomTabBean, SupportFragment> ITEMS = new LinkedHashMap<>();

    static BottomItemBuilder builder() {
        return new BottomItemBuilder();
    }

    public final BottomItemBuilder addItem(BottomTabBean bean, SupportFragment fragment) {
        ITEMS.put(bean, fragment);
        return this;
    }

    public final BottomItemBuilder addItems(LinkedHashMap<BottomTabBean, SupportFragment> items) {
        ITEMS.putAll(items);
        return this;
    }

    public final LinkedHashMap<BottomTabBean, SupportFragment> build() {
        return ITEMS;
    }
}
