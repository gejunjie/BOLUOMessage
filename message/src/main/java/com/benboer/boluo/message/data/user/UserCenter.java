package com.benboer.boluo.message.data.user;

import com.benboer.boluo.message.model.card.UserCard;

/**
 * @ClassName: UserCenter
 * @Description: 用户中心
 * @Author:  BenBoerBoluojiushiwo
 * @CreateDate: 2019-05-26 13:50
 * @Version: 1.0
 */
public interface UserCenter {
    //分发处理用户卡片信息，更新到数据库
    void dispatch(UserCard... cards);
}
