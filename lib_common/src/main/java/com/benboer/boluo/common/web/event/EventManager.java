package com.benboer.boluo.common.web.event;

import android.util.ArrayMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.benboer.boluo.common.util.log.LogUtils;

/**
 * Created by BenBoerBoluojiushiwo on 2019/8/23.
 */
public class EventManager {
    private static final ArrayMap<String, Event> EVENTS = new ArrayMap<>();

    private EventManager() {
    }

    //惰性单例
    private static class Holder {
        private static final EventManager INSTANCE = new EventManager();
    }

    public static EventManager getInstance() {
        return Holder.INSTANCE;
    }

    public EventManager addEvent(@Nullable String name, @Nullable Event event) {
        EVENTS.put(name, event);
        return this;
    }

    public Event createEvent(@NonNull String action) {
        final Event event = EVENTS.get(action);
        if (event == null) {
            return new UndefineEvent();
        }
        return event;
    }

    class UndefineEvent extends Event {
        @Override
        public String execute(String params) {
            LogUtils.e("UndefineEvent", params);
            return null;
        }
    }
}
