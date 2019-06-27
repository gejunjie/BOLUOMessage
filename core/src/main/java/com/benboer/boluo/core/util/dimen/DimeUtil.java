package com.benboer.boluo.core.util.dimen;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.benboer.boluo.core.app.BoLuo;

/**
 * Created by BenBoerBoluojiushiwo on 2019/6/27.
 */
public class DimeUtil {

    /**
     * 得到屏幕宽
     * @return
     */
    public static int getScreenWidth() {
        final Resources resources = BoLuo.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 得到屏幕高
     * @return
     */
    public static int getScreenHeight() {
        final Resources resources = BoLuo.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }
}
