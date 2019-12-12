package com.benboer.boluo.main.fragment.bottom;

import android.content.Context;
import android.graphics.Color;
import android.util.ArrayMap;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.benboer.boluo.common.app.BoLuo;
import com.benboer.boluo.common.app.ConfigKeys;
import com.benboer.boluo.common.base.fragment.SupportFragment;
import com.benboer.boluo.common.service.AccountService;
import com.benboer.boluo.main.serviceImpl.Account;
import com.benboer.boluo.message.fragment.user.UpdateInfoFragment;

import java.util.LinkedHashMap;

/**
 * Created by BenBoerBoluojiushiwo on 2019/7/2.
 */
@Route(path = "/main/bottomFragment")
public class BottomFragment extends BaseBottomFragment {

    @Autowired(name = "/main/account_service")
    protected AccountService mAccountService;

    public BottomFragment(){
        ARouter.getInstance().inject(this);
    }

    @Override
    public LinkedHashMap<BottomTabBean, SupportFragment> setItems(BottomItemBuilder builder) {
        final LinkedHashMap<BottomTabBean, SupportFragment> items = new LinkedHashMap<>();
        items.put(new BottomTabBean("{fa-home}","聊天"), (SupportFragment) ARouter.getInstance().build("/message/messageFragment").navigation() );
        items.put(new BottomTabBean("{fa-home}","广场"), (SupportFragment) ARouter.getInstance().build("/message/messageFragment").navigation() );
        items.put(new BottomTabBean("{icon-user-unpressed}","我的"),
                (SupportFragment) ARouter.getInstance().build("/message/personalFragment")
                        .withString("id", Account.getUserId()).navigation() );
        return items;
    }

    @Override
    public int setIndexFragment() {
        return 0;
    }

    @Override
    public int setClickColor() {
        return Color.GREEN;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!mAccountService.isComplete()){//检测账户信息是否完整
            getSupportDelegate().start(new UpdateInfoFragment());
        }
    }
}
