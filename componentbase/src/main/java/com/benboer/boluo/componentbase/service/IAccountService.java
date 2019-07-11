package com.benboer.boluo.componentbase.service;

import java.util.Date;

/**
 * Created by BenBoerBoluojiushiwo on 2019/7/9.
 */

public interface IAccountService {
//        "id":"0ad140b4-2aa1-4248-82fd-bb4c1e74895e",
//        "name":"aaa",
//        "phone":"13122267963",
//        "portrait":"http://italker-new.oss-cn-hongkong.aliyuncs.com/portrait/201906/97f0227c6535182da43f3ff2f4ea0200.jpg",
//        "desc":"111\n",
//        "sex":1,
//        "follows":5,
//        "following":5,
//        "isFollow":false,
//        "modifyAt":"2019-07-10T16:48:55.492"},
    void saveUser(
        String id,
        String name,
        String phone,
        String portrait,
        String desc,
        int sex,
        int follows,
        int following,
        boolean isFollow,
        Date modifyAt
    );

    void logout();
}
