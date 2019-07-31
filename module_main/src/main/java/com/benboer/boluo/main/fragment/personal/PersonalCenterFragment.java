package com.benboer.boluo.main.fragment.personal;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.benboer.boluo.common.mvp.PresenterFragment;
import com.benboer.boluo.common.mvp.presenter.BaseContract;
import com.benboer.boluo.main.R;

/**
 * Created by BenBoerBoluojiushiwo on 2019/7/31.
 */
public class PersonalCenterFragment extends PresenterFragment {

    @Override
    protected BaseContract.Presenter initPresenter() {
        return null;
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_personal_center;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View root) {

    }
}
