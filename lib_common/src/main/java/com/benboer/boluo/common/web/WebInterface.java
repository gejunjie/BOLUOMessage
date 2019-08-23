package com.benboer.boluo.common.web;

/**
 * Created by BenBoerBoluojiushiwo on 2019/8/23.
 */
public class WebInterface {
    private final WebFragment FRAGMENT;

    public WebInterface(WebFragment fragment) {
        this.FRAGMENT = fragment;
    }

    //简单工厂方法
    static WebInterface create(WebFragment fragment) {
        return new WebInterface(fragment);
    }

//    @SuppressWarnings("unused")
//    @JavascriptInterface
//    public String event(String params) {
//        final String action = BoLuo.getGson().fromJson(params,)
//                .parseObject(params).getString("action");
//        final Event event = EventManager.getInstance().createEvent(action);
//        LogUtils.e("WEB_EVENT",params);
//        if (event != null) {
//            event.setAction(action);
//            event.setFragment(FRAGMENT);
//            event.setContext(FRAGMENT.getContext());
//            event.setUrl(FRAGMENT.getUrl());
//            return event.execute(params);
//        }
//        return null;
//    }
}
