package com.benboer.boluo.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.benboer.boluo.common.widget.convention.PlaceHolderView;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/20.
 */
public class EmptyView extends LinearLayout implements PlaceHolderView {

    public EmptyView(Context context) {
        super(context);
    }

    public EmptyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EmptyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void triggerEmpty() {

    }

    @Override
    public void triggerNetError() {

    }

    @Override
    public void triggerError(int strRes) {

    }

    @Override
    public void triggerLoading() {

    }

    @Override
    public void triggerOk() {

    }

    @Override
    public void triggerOkOrEmpty(boolean isOk) {

    }
}
