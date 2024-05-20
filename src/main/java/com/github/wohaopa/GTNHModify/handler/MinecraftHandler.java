package com.github.wohaopa.GTNHModify.handler;

import net.minecraft.item.crafting.FurnaceRecipes;

import com.github.wohaopa.GTNHModify.strategies.Strategy;

@IHandler("init")
public class MinecraftHandler {

    public static void init() {
        // todo recipe
        FurnaceRecipes.smelting()
            .getSmeltingList()
            .forEach((itemStack, itemStack2) -> Strategy.strategy.handler_FurnaceRecipe(itemStack, itemStack2));
    }

    public static int handle(Object owner, int number) {
        return Strategy.strategy.handler_FurnaceProcessingTime(owner, number);
    }
}
