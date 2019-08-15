package com.benboer.boluo.main.fragment.bottom;

import android.content.Context;
import android.graphics.Color;

import com.benboer.boluo.common.app.BoLuo;
import com.benboer.boluo.common.app.ConfigKeys;
import com.benboer.boluo.common.base.fragment.SupportFragment;
import com.benboer.boluo.common.persistence.Account;
import com.benboer.boluo.componentbase.service.IMessageModuleFragmentService;
import com.benboer.boluo.componentbase.service.IPersonalFragmentService;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by BenBoerBoluojiushiwo on 2019/7/2.
 */
public class BottomFragment extends BaseBottomFragment {

    @Override
    public LinkedHashMap<BottomTabBean, SupportFragment> setItems(BottomItemBuilder builder) {
        final LinkedHashMap<BottomTabBean, SupportFragment> items = new LinkedHashMap<>();
        HashMap map = BoLuo.getConfiguration(ConfigKeys.SERVICE_FRAGMENT);
        items.put(new BottomTabBean("{fa-home}","聊天"),
                (SupportFragment)
                        ((IMessageModuleFragmentService)map.get(IMessageModuleFragmentService.class)).newMessageModuleFragment());
        items.put(new BottomTabBean("{fa-home}","广场"),
                (SupportFragment)
                        ((IMessageModuleFragmentService)map.get(IMessageModuleFragmentService.class)).newMessageModuleFragment());
        items.put(new BottomTabBean("{icon-user-unpressed}","我的"),
                (SupportFragment)
                        ((IPersonalFragmentService)map.get(IPersonalFragmentService.class)).newPersonalFragment());
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
        if (!Account.isComplete()){//检测账户信息是否完整
//            getSupportDelegate().start(new UpdateInfoFragment());//todo
        }
    }


}
