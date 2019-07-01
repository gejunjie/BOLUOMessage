package com.benboer.boluo.factory.main;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.benboer.boluo.core.fragment.SupportFragment;
import com.benboer.boluo.core.fragment.bottom.BottomItemFragment;
import com.benboer.boluo.factory.R;

/**
 * Created by BenBoerBoluojiushiwo on 2019/7/1.
 */
public class TestFragment extends BottomItemFragment {
    @Override
    public Object setLayout() {
        return R.layout.fragment_launcher;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View root) {

    }
}
