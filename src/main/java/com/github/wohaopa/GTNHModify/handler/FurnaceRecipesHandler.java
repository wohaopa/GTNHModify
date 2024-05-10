package com.github.wohaopa.GTNHModify.handler;

import net.minecraft.item.crafting.FurnaceRecipes;

public class FurnaceRecipesHandler {

    protected static void init() {
        FurnaceRecipes.smelting()
            .getSmeltingList();
    }
}
