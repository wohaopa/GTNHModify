package com.github.wohaopa.GTNHModify.tweakers;

import goodgenerator.api.recipe.GoodGeneratorRecipeMaps;

public class GGNaquadahReactorTweaker extends ITweaker {

    Integer integer = 10;

    @Override
    public void apply() {
        GoodGeneratorRecipeMaps.naquadahReactorFuels.getAllRecipes()
            .forEach(gtRecipe -> gtRecipe.mDuration *= integer);
    }

    @Override
    public Object getSettings() {
        return integer;
    }
}
