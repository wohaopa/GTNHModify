package com.github.wohaopa.GTNHModify.handler;

import gregtech.api.recipe.RecipeMap;
import gregtech.api.util.GT_Recipe;

public class GT_RecipesHandler {

    protected static void init() {
        RecipeMap.ALL_RECIPE_MAPS.forEach(
            (s, recipeMap) -> recipeMap.getAllRecipes()
                .forEach(recipe -> { recipe.mDuration = 1; }));
        GT_Recipe.GT_Recipe_AssemblyLine.sAssemblylineRecipes
            .forEach(gtRecipeAssemblyLine -> { gtRecipeAssemblyLine.mDuration = 1; });

    }

}
