package com.benboer.boluo.main.fragment.bottom;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.benboer.boluo.common.base.fragment.SupportFragment;
import com.benboer.boluo.common.service.AccountService;
import com.benboer.boluo.main.serviceImpl.Account;
import com.benboer.boluo.message.db.User;
import com.benboer.boluo.message.fragment.user.UpdateInfoFragment;

import java.util.LinkedHashMap;

/**
 * Created by BenBoerBoluojiushiwo on 2019/7/2.
 *
 * 根Fragment
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
        items.put(new BottomTabBean("{icon-chat}", "聊天"),
                (SupportFragment) ARouter.getInstance().build("/message/messageFragment").navigation());
        items.put(new BottomTabBean("{icon-discover}", "发现"),
                (SupportFragment) ARouter.getInstance().build("/discover/discoverFragment").navigation());
        items.put(new BottomTabBean("{icon-user}", "我的"),
                (SupportFragment) ARouter.getInstance().build("/message/personalFragment").navigation());
        return items;
    }

    @Override
    public int setIndexFragment() {
        return 0;
    }

    @Override
    public int setClickColor() {
        return Color.BLUE;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!isComplete()) {//检测账户信息是否完整
            getSupportDelegate().start(new UpdateInfoFragment());
        }
    }

    /**
     * 是否已经完善了用户信息（描述信息和头像）
     *
     * @return True 是完成了
     */
    private boolean isComplete() {
        if (Account.isLogin()) {
            User self = Account.getUser();
            if (self == null) return false;
            return !TextUtils.isEmpty(self.getDesc())
                    && !TextUtils.isEmpty(self.getPortrait())
                    && self.getSex() != 0;
        }
        return false;
    }

}
