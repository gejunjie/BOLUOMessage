package com.benboer.boluo.common.icon;

import com.joanzapata.iconify.Icon;

/**
 * @ClassName: FontIcons
 * @Description: java类作用描述
 * @Author:  BenBoerBoluojiushiwo
 * @CreateDate: 2019-08-04 13:27
 * @Version: 1.0
 */
public enum  FontIcons implements Icon {
    icon_close('\ue606'),
    icon_setup('\ue605'),
    icon_scan('\ue604'),
    icon_return_1('\ue602'),
    icon_return_2('\ue601');
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
    }}
