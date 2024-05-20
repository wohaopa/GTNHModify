package com.github.wohaopa.GTNHModify.handler;

import com.github.wohaopa.GTNHModify.ModHelper;
import com.github.wohaopa.GTNHModify.strategies.Strategy;

import gregtech.api.recipe.RecipeMap;
import gregtech.api.util.GT_Recipe;

@IHandler("init")
public class GregTechHandler {

    protected static void init() {
        if (!ModHelper.hasGregtech) return;

        RecipeMap.ALL_RECIPE_MAPS.forEach(
            (s, recipeMap) -> recipeMap.getAllRecipes()
                .forEach(recipe -> Strategy.strategy.handler_GT_Recipe(recipe)));
        GT_Recipe.GT_Recipe_AssemblyLine.sAssemblylineRecipes
            .forEach(gtRecipeAssemblyLine -> Strategy.strategy.handler_GT_Recipe_AssemblyLine(gtRecipeAssemblyLine));

    }

    public static int handle(Object owner, int number) {
        return Strategy.strategy.handler_GT_ProcessingTime(owner, number);
    }

}
