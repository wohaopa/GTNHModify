package com.github.wohaopa.GTNHModify.tweakers.gt;

import gregtech.api.util.GT_Recipe;

public class TenthsTweaker extends GT_RecipeTweaker {

    @Override
    protected void modifyGT_Recipe(GT_Recipe aRecipe) {
        aRecipe.mDuration /= 10;
    }

    @Override
    protected void modifyGT_Recipe_AssemblyLine(GT_Recipe.GT_Recipe_AssemblyLine aRecipe) {
        aRecipe.mDuration /= 10;
    }
}
