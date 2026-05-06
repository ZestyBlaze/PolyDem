package dev.zestyblaze.demeter.util;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.level.ServerLevel;

public interface NewDayCallback {
    Event<NewDayCallback> EVENT = EventFactory.createArrayBacked(NewDayCallback.class,
            (listeners) -> (level) -> {
        for (NewDayCallback listener : listeners) {
            listener.onNewDay(level);
        }
    });

    void onNewDay(ServerLevel level);
}
