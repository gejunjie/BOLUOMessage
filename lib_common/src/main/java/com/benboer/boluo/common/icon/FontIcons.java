package com.benboer.boluo.common.icon;

import com.joanzapata.iconify.Icon;

/**
 * 字体图标库
 */
public enum  FontIcons implements Icon {
    icon_close('\ue606'),
    icon_setup('\ue605'),
    icon_scan('\ue604'),
    icon_add('\ue69a'),
    //主界面底部图标
    icon_chat('\ue6f0'),
    icon_user('\ue68d'),
    icon_discover('\ue68e');

    private char character;

    FontIcons(char character) {
        this.character = character;
    }

    @Override
    public String key() {
        return name().replace('_', '-');
    }

    @Override
    public char character() {
        return character;
    }
}
