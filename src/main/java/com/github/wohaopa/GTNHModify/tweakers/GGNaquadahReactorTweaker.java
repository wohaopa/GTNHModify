package com.github.wohaopa.GTNHModify.tweakers;

import goodgenerator.api.recipe.GoodGeneratorRecipeMaps;

public class GGNaquadahReactorTweaker extends ITweaker {

    @Override
    public void apply() {
        GoodGeneratorRecipeMaps.naquadahReactorFuels.getAllRecipes()
            .forEach(gtRecipe -> gtRecipe.mDuration *= 10);
    }
}
