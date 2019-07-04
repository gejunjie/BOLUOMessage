package com.benboer.boluo.factory.fragment.main;

import android.widget.Button;

import com.benboer.boluo.common.app.PresenterFragment;
import com.benboer.boluo.factory.R;
import com.benboer.boluo.factory.R2;
import com.benboer.boluo.factory.presenter.account.LogoutContract;
import com.benboer.boluo.factory.presenter.account.LogoutPresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by BenBoerBoluojiushiwo on 2019/6/21.
 */
public class HomeFragment extends PresenterFragment<LogoutContract.Presenter>
        implements LogoutContract.View{
//    @BindView(R2.id.btn_logout)
//    Button logoutBtn;
//
//    @OnClick(R.id.btn_logout)
//    void onLogout(){
//        mPresenter.logout();
//        getActivity().finish();
//    }

    @Override
    protected LogoutContract.Presenter initPresenter() {
        return new LogoutPresenter(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void logoutSuccess() {
//        AccountActivity.show(getActivity());
    }
}
