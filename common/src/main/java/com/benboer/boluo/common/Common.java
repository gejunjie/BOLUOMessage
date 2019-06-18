package com.benboer.boluo.common;

public class Common {
    /**
     */
    public interface Constance {
        // 手机号的正则,11位手机号
        String REGEX_MOBILE = "[1][3,4,5,7,8][0-9]{9}$";

        // 基础的网络请求地址http://localhost:6000/Gradle___boluo___boluo_1_0_SNAPSHOT_war/api
        String API_URL = "http://172.20.10.2:6000/Gradle___boluo___boluo_1_0_SNAPSHOT_war/api/";

    }

}
