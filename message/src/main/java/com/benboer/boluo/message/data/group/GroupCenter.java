package com.benboer.boluo.message.data.group;

import com.benboer.boluo.message.model.card.GroupCard;
import com.benboer.boluo.message.model.card.GroupMemberCard;

/**
 * @ClassName: GroupCenter
 * @Description: 群中心接口定义
 * @Author:  BenBoerBoluojiushiwo
 * @CreateDate: 2019-05-26 21:54
 * @Version: 1.0
 */
public interface GroupCenter {

    void dispatch(GroupCard... cards);

    void dispatch(GroupMemberCard... cards);
}
