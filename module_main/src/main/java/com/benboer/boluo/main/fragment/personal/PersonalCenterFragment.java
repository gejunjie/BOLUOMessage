package com.benboer.boluo.main.fragment.personal;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.benboer.boluo.common.R2;
import com.benboer.boluo.common.mvp.PresenterFragment;
import com.benboer.boluo.common.mvp.presenter.BaseContract;
import com.benboer.boluo.main.R;

import butterknife.BindView;

import static com.blankj.utilcode.util.BarUtils.getStatusBarHeight;

/**
 * Created by BenBoerBoluojiushiwo on 2019/7/31.
 */
public class PersonalCenterFragment extends PresenterFragment {

    private Toolbar mToolbar;

    @Override
    protected BaseContract.Presenter initPresenter() {
        return null;
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_personal_center;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View root){
        mToolbar = bind(R.id.personal_toolbar);
        mToolbar.setPadding(0, getStatusBarHeight(), 0, 0);
    }
}
