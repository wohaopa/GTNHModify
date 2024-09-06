package com.github.wohaopa.GTNHModify.tweakers;

import gregtech.api.recipe.RecipeMaps;

public class FusionTweaker extends ITweaker {

    int value = 1;

    @Override
    protected void apply() {
        RecipeMaps.fusionRecipes.getAllRecipes()
            .forEach(gtRecipe -> gtRecipe.mSpecialValue = value);
    }

    @Override
    public Object getSettings() {
        return value;
    }

    @Override
    public void setSetting(Object setting) {
        value = (int) setting;
    }
}
