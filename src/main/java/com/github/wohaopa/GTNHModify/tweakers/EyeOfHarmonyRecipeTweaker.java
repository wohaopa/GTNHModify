package com.github.wohaopa.GTNHModify.tweakers;

import java.lang.reflect.Field;
import java.util.HashMap;

import com.github.technus.tectech.TecTech;
import com.github.technus.tectech.recipe.EyeOfHarmonyRecipe;
import com.github.technus.tectech.recipe.EyeOfHarmonyRecipeStorage;

public class EyeOfHarmonyRecipeTweaker extends ITweaker {

    @Override
    protected void apply() {
        try {
            Class<EyeOfHarmonyRecipeStorage> clazz = EyeOfHarmonyRecipeStorage.class;
            Field field = clazz.getDeclaredField("recipeHashMap");
            field.setAccessible(true);
            HashMap<String, EyeOfHarmonyRecipe> recipeHashMap = (HashMap<String, EyeOfHarmonyRecipe>) field
                .get(TecTech.eyeOfHarmonyRecipeStorage);
            recipeHashMap.forEach((s, eyeOfHarmonyRecipe) -> handler_EyeOfHarmonyRecipe(eyeOfHarmonyRecipe));
        } catch (NoSuchFieldException | IllegalAccessException e) {}
    }

    private Field miningTimeSecondsField;
    private Field hydrogenRequirementField;
    private Field heliumRequirementField;
    private Field baseSuccessChanceField;
    private Field euOutputField;
    private Field euStartCostField;

    private void handler_EyeOfHarmonyRecipe(EyeOfHarmonyRecipe eyeOfHarmonyRecipe) {
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
                    hydrogenRequirementField.setLong(eyeOfHarmonyRecipe, 1);
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
                    heliumRequirementField.setLong(eyeOfHarmonyRecipe, 1);
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
