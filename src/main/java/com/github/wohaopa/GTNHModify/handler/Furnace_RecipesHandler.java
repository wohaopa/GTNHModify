package com.github.wohaopa.GTNHModify.handler;

import net.minecraft.item.crafting.FurnaceRecipes;

import com.github.wohaopa.GTNHModify.config.Config;

@IHandler("init")
public class Furnace_RecipesHandler {

    public static void init() {
        FurnaceRecipes.smelting()
            .getSmeltingList()
            .forEach((itemStack, itemStack2) -> Config.strategy.handler_FurnaceRecipe(itemStack, itemStack2));
    }

    public static int handle(Object owner, int number) {
        return Config.strategy.handler_FurnaceProcessingTime(owner, number);
    }
}
