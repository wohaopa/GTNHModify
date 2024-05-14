package com.github.wohaopa.GTNHModify.handler;

import com.github.wohaopa.GTNHModify.config.Config;

import gregtech.api.recipe.RecipeMap;
import gregtech.api.util.GT_Recipe;

@IHandler("init")
public class GT_RecipesHandler {

    protected static void init() {
        RecipeMap.ALL_RECIPE_MAPS.forEach(
            (s, recipeMap) -> recipeMap.getAllRecipes()
                .forEach(recipe -> Config.strategy.handler_GT_Recipe(recipe)));
        GT_Recipe.GT_Recipe_AssemblyLine.sAssemblylineRecipes
            .forEach(gtRecipeAssemblyLine -> { Config.strategy.handler_GT_Recipe_AssemblyLine(gtRecipeAssemblyLine); });

    }

    public static int handle(Object owner, int number) {
        return Config.strategy.handler_GT_ProcessingTime(owner, number);
    }

}
