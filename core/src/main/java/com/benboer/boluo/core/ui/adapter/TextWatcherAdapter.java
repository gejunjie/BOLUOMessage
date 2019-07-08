package com.benboer.boluo.core.ui.adapter;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/31.
 *
 * 让实现类不用每个方法都实现
 */
public abstract class TextWatcherAdapter implements TextWatcher {

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }
    @Override
    public void afterTextChanged(Editable s) {

    }
}
