package com.github.wohaopa.GTNHModify.tweakers.gt;

import gregtech.api.util.GT_Recipe;

public class EnergyLessTweaker extends GT_RecipeTweaker {

    @Override
    protected void modifyGT_Recipe(GT_Recipe aRecipe) {
        aRecipe.mEUt = 0;
    }

    @Override
    protected void modifyGT_Recipe_AssemblyLine(GT_Recipe.GT_Recipe_AssemblyLine aRecipe) {
        aRecipe.mEUt = 0;
    }
}
