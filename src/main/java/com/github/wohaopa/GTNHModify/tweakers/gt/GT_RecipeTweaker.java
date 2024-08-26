package com.github.wohaopa.GTNHModify.tweakers.gt;

import com.github.wohaopa.GTNHModify.tweakers.ITweaker;

import gregtech.api.recipe.RecipeMap;
import gregtech.api.util.GT_Recipe;

public abstract class GT_RecipeTweaker extends ITweaker {

    @Override
    public void apply() {
        RecipeMap.ALL_RECIPE_MAPS.forEach((s, recipeMap) -> {
            if (!recipeMap.unlocalizedName.equals("gg.recipe.naquadah_reactor")) recipeMap.getAllRecipes()
                .forEach(this::modifyGT_Recipe);
        });
        GT_Recipe.GT_Recipe_AssemblyLine.sAssemblylineRecipes.forEach(this::modifyGT_Recipe_AssemblyLine);
    }

    protected abstract void modifyGT_Recipe(GT_Recipe aRecipe);

    protected abstract void modifyGT_Recipe_AssemblyLine(GT_Recipe.GT_Recipe_AssemblyLine aRecipe);
}
