package com.github.wohaopa.GTNHModify.tweakers.gt;

import gregtech.api.util.GT_Recipe;

public class OneTickTweaker extends GT_RecipeTweaker {

    @Override
    protected void modifyGT_Recipe(GT_Recipe aRecipe) {
        if (aRecipe.mDuration > 1) aRecipe.mDuration = 1;
    }

    @Override
    protected void modifyGT_Recipe_AssemblyLine(GT_Recipe.GT_Recipe_AssemblyLine aRecipe) {
        if (aRecipe.mDuration > 1) aRecipe.mDuration = 1;
    }
}
