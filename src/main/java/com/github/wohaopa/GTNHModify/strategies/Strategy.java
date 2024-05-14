package com.github.wohaopa.GTNHModify.strategies;

import net.minecraft.item.ItemStack;

import gregtech.api.util.GT_Recipe;

public abstract class Strategy {

    public void handler_GT_Recipe(GT_Recipe gtRecipe) {

    }

    public void handler_GT_Recipe_AssemblyLine(GT_Recipe.GT_Recipe_AssemblyLine gtRecipe) {

    }

    public int handler_GT_ProcessingTime(Object owner, int number) {
        return number;
    }

    public void handler_FurnaceRecipe(ItemStack input, ItemStack output) {

    }

    public int handler_FurnaceProcessingTime(Object owner, int number) {
        return number;
    }

}
