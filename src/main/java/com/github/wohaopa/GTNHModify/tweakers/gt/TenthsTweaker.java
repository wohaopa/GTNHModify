package com.github.wohaopa.GTNHModify.tweakers.gt;

import gregtech.api.util.GT_Recipe;

public class TenthsTweaker extends GT_RecipeTweaker {

    int integer = 10;

    @Override
    protected void modifyGT_Recipe(GT_Recipe aRecipe) {
        aRecipe.mDuration /= integer;
    }

    @Override
    protected void modifyGT_Recipe_AssemblyLine(GT_Recipe.GT_Recipe_AssemblyLine aRecipe) {
        aRecipe.mDuration /= integer;
    }

    @Override
    public Object getSettings() {
        return integer;
    }

    @Override
    public void setSetting(Object s) {
        integer = (int) s;
    }
}
