package com.benboer.boluo.module_common.ui.scanner;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import me.dm7.barcodescanner.core.ViewFinderView;

public class BoluoViewFinderView extends ViewFinderView {
    public BoluoViewFinderView(Context context) {
        this(context, null);
    }

    public BoluoViewFinderView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        mSquareViewFinder = true;
        mBorderPaint.setColor(Color.GREEN);
        mLaserPaint.setColor(Color.GREEN);
    }
}
