package com.benboer.boluo.common.app;

import android.app.Activity;

import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.Utils;
import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Interceptor;

/**
 * Created by BenBoerBoluojiushiwo on 2019/6/26.
 *
 * 项目配置类
 */
public class Configurator {

    private static final HashMap<Object, Object> CONFIGS = new HashMap<>();
    //字体
    private static final ArrayList<IconFontDescriptor> ICONS = new ArrayList<>();
    //拦截器
    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();
    //各个模块提供的fragment对象
    private HashMap<Class<? extends Fragment>, ? extends Fragment> SERVICES = new HashMap<>();

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

    public final void configure(){
        initIcons();
        //log第三方库
        Logger.addLogAdapter(new AndroidLogAdapter());
        CONFIGS.put(ConfigKeys.CONFIG_READY, true);
        //初始化util code第三方开源
//        Utils.init(BoLuo.getApplicationContext());
    }

    /**
     * 检查是否已经初始化
     */
    private void checkConfiguration() {
        final boolean isReady = (boolean) CONFIGS.get(ConfigKeys.CONFIG_READY);
        if (!isReady) {
            throw new RuntimeException("Configuration is not ready,call configure");
        }
    }

    public final Configurator withActivity(Activity activity) {
        CONFIGS.put(ConfigKeys.ACTIVITY, activity);
        return this;
    }

    public final Configurator withIcon(IconFontDescriptor descriptor) {
        ICONS.add(descriptor);
        return this;
    }

    private void initIcons() {
        if (ICONS.size() > 0) {
            final Iconify.IconifyInitializer initializer = Iconify.with(ICONS.get(0));
            for (int i = 1; i < ICONS.size(); i++) {
                initializer.with(ICONS.get(i));
            }
        }
    }

    public final Configurator withApiHost(String host) {
        CONFIGS.put(ConfigKeys.API_HOST, host);
        return this;
    }

    public final Configurator withInterceptor(ArrayList<Interceptor> interceptors) {
        INTERCEPTORS.addAll(interceptors);
        CONFIGS.put(ConfigKeys.INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    public final Configurator withInterceptor(Interceptor interceptor) {
        INTERCEPTORS.add(interceptor);
        CONFIGS.put(ConfigKeys.INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    /**
     * 获取各个模块提供的fragment对象
     * @param services
     * @return
     */
    public final Configurator withFragmentService(HashMap services){
        SERVICES.putAll(services);
        CONFIGS.put(ConfigKeys.SERVICE_FRAGMENT, services);
        return this;
    }
}
