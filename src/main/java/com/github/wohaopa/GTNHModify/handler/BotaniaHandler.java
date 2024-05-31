package com.github.wohaopa.GTNHModify.handler;

import com.github.wohaopa.GTNHModify.strategies.Strategy;

@IHandler("init")
public class BotaniaHandler {

    public static void init() {

    }

    public static int handle(Object owner, int number) {
        // todo new method
        return Strategy.strategy.handler_FurnaceProcessingTime(owner, number);
    }
}
