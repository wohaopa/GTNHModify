package com.github.wohaopa.GTNHModify.strategies;

import java.lang.reflect.Field;

import com.github.technus.tectech.recipe.EyeOfHarmonyRecipe;

import gregtech.api.util.GT_Recipe;

public class OneTick extends Strategy {

    @Override
    public void handler_GT_Recipe(GT_Recipe gtRecipe) {
        gtRecipe.mDuration = 1;
    }

    @Override
    public void handler_GT_Recipe_AssemblyLine(GT_Recipe.GT_Recipe_AssemblyLine gtRecipe) {
        gtRecipe.mDuration = 1;
    }

    @Override
    public int handler_GT_ProcessingTime(Object owner, int number) {
        return 1;
    }

    @Override
    public int handler_FurnaceProcessingTime(Object owner, int number) {
        return 1;
    }

    private Field field;

    public void handler_EyeOfHarmonyRecipe(EyeOfHarmonyRecipe eyeOfHarmonyRecipe) {
        if (field == null) {
            try {
                Class<?> clazz = EyeOfHarmonyRecipe.class;
                field = clazz.getDeclaredField("miningTimeSeconds");
                field.setAccessible(true);

            } catch (NoSuchFieldException ignored) {}
        }
        try {
            if (field != null) {
                field.setLong(eyeOfHarmonyRecipe, 1);
            }
        } catch (IllegalAccessException ignored) {}
    }

}
