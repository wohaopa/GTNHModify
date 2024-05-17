package com.github.wohaopa.GTNHModify.strategies;

import gregtech.api.util.GT_Recipe;

public class Tenths extends Strategy {

    protected Tenths() {}

    private boolean inited = false;

    @Override
    protected void changeFrom(Strategy strategy) {
        if (strategy != null) inited = true;
    }

    @Override
    protected boolean loading() {
        return !inited;
    }

    @Override
    protected void loaded() {
        inited = true;
    }

    @Override
    public void handler_GT_Recipe(GT_Recipe gtRecipe) {
        gtRecipe.mDuration = gtRecipe.mDuration / 10 == 0 ? 1 : gtRecipe.mDuration / 10;
    }

    @Override
    public void handler_GT_Recipe_AssemblyLine(GT_Recipe.GT_Recipe_AssemblyLine gtRecipe) {
        gtRecipe.mDuration = gtRecipe.mDuration / 10 == 0 ? 1 : gtRecipe.mDuration / 10;
    }

    @Override
    public int handler_GT_ProcessingTime(Object owner, int number) {
        return number / 10 == 0 ? 1 : number / 10;
    }

    @Override
    public int handler_FurnaceProcessingTime(Object owner, int number) {
        return number / 10 == 0 ? 1 : number / 10;
    }
}
