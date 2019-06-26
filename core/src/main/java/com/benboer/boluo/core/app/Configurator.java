package com.benboer.boluo.core.app;

import java.util.HashMap;

/**
 * Created by BenBoerBoluojiushiwo on 2019/6/26.
 *
 * 项目配置类
 */
public class Configurator {

    private static final HashMap<Object, Object> CONFIGS = new HashMap<>();

    private Configurator() {
        CONFIGS.put(ConfigKeys.CONFIG_READY, false);
    }

    public static Configurator getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }

    final HashMap<Object, Object> getConfigs() {
        return CONFIGS;
    }

    @SuppressWarnings("unchecked")
    final <T> T getConfiguration(Object key) {
        checkConfiguration();
        final Object value = CONFIGS.get(key);
        if (value == null) {
            throw new NullPointerException(key.toString() + " IS NULL");
        }
        return (T) CONFIGS.get(key);
    }

    private void checkConfiguration() {
        final boolean isReady = (boolean) CONFIGS.get(ConfigKeys.CONFIG_READY);
        if (!isReady) {
            throw new RuntimeException("Configuration is not ready,call configure");
        }
    }

    enum ConfigKeys {
        API_HOST,
        APPLICATION_CONTEXT,
        CONFIG_READY,
        ICON,
        LOADER_DELAYED,
        INTERCEPTOR,
        WE_CHAT_APP_ID,
        WE_CHAT_APP_SECRET,
        ACTIVITY,
        HANDLER,
        JAVASCRIPT_INTERFACE,
        WEB_HOST
    }
}
