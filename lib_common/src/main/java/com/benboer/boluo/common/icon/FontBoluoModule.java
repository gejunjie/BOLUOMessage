package com.benboer.boluo.common.icon;

import com.joanzapata.iconify.Icon;
import com.joanzapata.iconify.IconFontDescriptor;

/**
 * @ClassName: FontBoluoModule
 * @Description: java类作用描述
 * @Author:  BenBoerBoluojiushiwo
 * @CreateDate: 2019-08-04 13:27
 * @Version: 1.0
 */
public class FontBoluoModule implements IconFontDescriptor {
    @Override
    public String ttfFileName() {
        return "iconfont.ttf";
    }

    @Override
    public Icon[] characters() {
        return FontIcons.values();
    }
}
