package com.github.wohaopa.GTNHModify.tweakers.gt;

import gregtech.api.util.GT_Recipe;

public class EnergyLessTweaker extends GT_RecipeTweaker {

    Integer integer = 0;

    @Override
    protected void modifyGT_Recipe(GT_Recipe aRecipe) {
        aRecipe.mEUt = integer;
    }

    @Override
    protected void modifyGT_Recipe_AssemblyLine(GT_Recipe.GT_Recipe_AssemblyLine aRecipe) {
        aRecipe.mEUt = integer;
    }

    @Override
    public Object getSettings() {
        return integer;
    }
}
