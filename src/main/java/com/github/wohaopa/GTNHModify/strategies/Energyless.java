package com.github.wohaopa.GTNHModify.strategies;

import gregtech.api.util.GT_Recipe;

public class Energyless extends Strategy {

    protected Energyless() {}

    @Override
    public void handler_GT_Recipe(GT_Recipe gtRecipe) {
        if (gtRecipe.mEUt > 0) gtRecipe.mEUt = 0;
    }
}
