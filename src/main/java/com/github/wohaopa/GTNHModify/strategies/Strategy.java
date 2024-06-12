package com.github.wohaopa.GTNHModify.strategies;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;

import com.github.technus.tectech.recipe.EyeOfHarmonyRecipe;
import com.github.wohaopa.GTNHModify.GTNHModifyMod;

import gregtech.api.util.GT_Recipe;

public abstract class Strategy {

    public static Strategy strategy = null;
    private static final Map<String, Strategy> instants = new HashMap<>();
    private static Strategy NONE;

    public static void init() {
        NONE = new None();
        instants.put("Energyless", new Energyless());
        instants.put("None", NONE);
        instants.put("OneTick", new OneTick());
        instants.put("Output64", new Output64());
        instants.put("Tenths", new Tenths());
    }

    public static void setStrategy(String strategyName) {
        Strategy strategy1 = instants.getOrDefault(strategyName, NONE);
        if (strategy1 != strategy) {
            GTNHModifyMod.LOG.info("New strategy enable: " + strategyName);
            strategy1.changeFrom(strategy);
            strategy = strategy1;
            dirty = true;
        }
    }

    private static boolean dirty = true;

    public static boolean prevInit() {
        if (!dirty) return false;
        return strategy.loading();
    }

    public static void postInit() {
        dirty = false;
        strategy.loaded();
    }

    protected Strategy() {}

    protected void changeFrom(Strategy strategy) {}

    protected boolean loading() {
        return true;
    }

    protected void loaded() {}

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

    public void handler_EyeOfHarmonyRecipe(EyeOfHarmonyRecipe eyeOfHarmonyRecipe) {}
}
