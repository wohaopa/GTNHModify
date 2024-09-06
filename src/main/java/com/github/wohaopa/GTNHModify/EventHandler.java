package com.github.wohaopa.GTNHModify;

import com.github.wohaopa.GTNHModify.tweakers.Tweakers;
import com.github.wohaopa.GTNHModify.tweakers.gt.DynamicDuration;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class EventHandler {

    long lastUpdateTime = 0;
    long ticks = 0;

    @SubscribeEvent
    public void onTickPost(TickEvent.ServerTickEvent event) {
        if (Tweakers.Dynamic_Duration.enabled) if (event.phase == TickEvent.Phase.END) {
            ticks++;
            long now = System.currentTimeMillis();
            if (now - lastUpdateTime > 10000) { // 10秒
                if (lastUpdateTime != 0) {
                    if (ticks < 195) {
                        DynamicDuration.instance.setF((float) (ticks * 50.0) / (now - lastUpdateTime));
                        DynamicDuration.update();
                    }
                    ticks = 0;
                }
                lastUpdateTime = now;
            }
        }
    }
}
