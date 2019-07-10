package com.benboer.boluo.message.service;

import com.benboer.boluo.componentbase.service.IAccountService;
import com.benboer.boluo.message.model.db.User;
import com.benboer.boluo.module_common.base.model.Author;

import java.util.Date;

/**
 * Created by BenBoerBoluojiushiwo on 2019/7/9.
 */
public class AccountService implements IAccountService {

    @Override
    public void saveUser( String id,
                            String name,
                            String phone,
                            String portrait,
                            String desc,
                            int sex,
                            int follows,
                            int following,
                            boolean isFollow,
                            Date modifyAt) {

        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setPhone(phone);
        user.setPortrait(portrait);
        user.setDesc(desc);
        user.setSex(sex);
        user.setFollows(follows);
        user.setFollowing(following);
        user.setFollow(isFollow);
        user.setModifyAt(modifyAt);
        user.save();
    }
}
