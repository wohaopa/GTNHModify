package com.github.wohaopa.GTNHModify.strategies;

import java.lang.reflect.Field;

import com.github.technus.tectech.recipe.EyeOfHarmonyRecipe;

import gregtech.api.util.GT_Recipe;

public class OneTick extends Strategy {

    @Override
    public void handler_GT_Recipe(GT_Recipe gtRecipe) {
        if (gtRecipe.getRecipeCategory().unlocalizedName.equals("gg.recipe.naquadah_reactor")) {
            gtRecipe.mDuration *= 10;
        } else gtRecipe.mDuration = 1;

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

    private Field miningTimeSecondsField;
    private Field hydrogenRequirementField;
    private Field heliumRequirementField;
    private Field baseSuccessChanceField;
    private Field euOutputField;
    private Field euStartCostField;

    public void handler_EyeOfHarmonyRecipe(EyeOfHarmonyRecipe eyeOfHarmonyRecipe) {
        Class<?> clazz = EyeOfHarmonyRecipe.class;

        // miningTimeSeconds
        {
            if (miningTimeSecondsField == null) {
                try {
                    miningTimeSecondsField = clazz.getDeclaredField("miningTimeSeconds");
                    miningTimeSecondsField.setAccessible(true);
                } catch (NoSuchFieldException ignored) {}
            }
            if (miningTimeSecondsField != null) {
                try {
                    miningTimeSecondsField.setLong(eyeOfHarmonyRecipe, 1);
                } catch (IllegalAccessException ignored) {}
            }
        }

        // hydrogenRequirement
        {
            if (hydrogenRequirementField == null) {
                try {
                    hydrogenRequirementField = clazz.getDeclaredField("hydrogenRequirement");
                    hydrogenRequirementField.setAccessible(true);

                } catch (NoSuchFieldException ignored) {}
            }
            if (hydrogenRequirementField != null) {
                try {
                    long hydrogenRequirement = hydrogenRequirementField.getLong(eyeOfHarmonyRecipe);
                    hydrogenRequirementField.setLong(eyeOfHarmonyRecipe, 1000);
                } catch (IllegalAccessException ignored) {}
            }
        }

        // heliumRequirement
        {
            if (heliumRequirementField == null) {
                try {
                    heliumRequirementField = clazz.getDeclaredField("heliumRequirement");
                    heliumRequirementField.setAccessible(true);

                } catch (NoSuchFieldException ignored) {}
            }
            if (heliumRequirementField != null) {
                try {
                    long heliumRequirement = heliumRequirementField.getLong(eyeOfHarmonyRecipe);
                    heliumRequirementField.setLong(eyeOfHarmonyRecipe, 1000);
                } catch (IllegalAccessException ignored) {}
            }
        }

        // baseSuccessChance
        {
            if (baseSuccessChanceField == null) {
                try {
                    baseSuccessChanceField = clazz.getDeclaredField("baseSuccessChance");
                    baseSuccessChanceField.setAccessible(true);

                } catch (NoSuchFieldException ignored) {}
            }
            if (baseSuccessChanceField != null) {
                try {
                    double baseSuccessChance = baseSuccessChanceField.getDouble(eyeOfHarmonyRecipe);
                    baseSuccessChanceField.setDouble(eyeOfHarmonyRecipe, 5);
                } catch (IllegalAccessException ignored) {}
            }
        }

        // euOutput
        {
            if (euOutputField == null) {
                try {
                    euOutputField = clazz.getDeclaredField("euOutput");
                    euOutputField.setAccessible(true);
                } catch (NoSuchFieldException ignored) {}
            }
            if (euOutputField != null) {
                try {
                    euOutputField.setLong(eyeOfHarmonyRecipe, 999999999);
                } catch (IllegalAccessException ignored) {}
            }
        }

        // euStartCost
        {
            if (euStartCostField == null) {
                try {
                    euStartCostField = clazz.getDeclaredField("euStartCost");
                    euStartCostField.setAccessible(true);
                } catch (NoSuchFieldException ignored) {}
            }
            if (euStartCostField != null) {
                try {
                    euStartCostField.setLong(eyeOfHarmonyRecipe, 114);
                } catch (IllegalAccessException ignored) {}
            }
        }
    }

}
