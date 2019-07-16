package com.benboer.boluo.message.service;

import com.benboer.boluo.componentbase.service.IAccountService;
import com.benboer.boluo.message.model.db.Group;
import com.benboer.boluo.message.model.db.GroupMember;
import com.benboer.boluo.message.model.db.Message;
import com.benboer.boluo.message.model.db.Session;
import com.benboer.boluo.message.model.db.User;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.Date;

/**
 * Created by BenBoerBoluojiushiwo on 2019/7/9.
 *
 * 向其他业务模块暴露
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

    @Override
    public void logout() {
        SQLite.delete().tables(new Class[]{GroupMember.class, Message.class,
                Group.class, Session.class, User.class});
    }
}
