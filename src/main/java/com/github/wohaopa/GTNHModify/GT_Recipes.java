package com.github.wohaopa.GTNHModify;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import gregtech.api.util.GT_Recipe;
import gregtech.api.util.extensions.ArrayExt;

public class GT_Recipes {

    private static Set<GT_Recipe> recipes = new HashSet<>();

    public static void addRecipe(GT_Recipe recipe) {
        if (recipes.contains(recipe)) return;

        recipe.mDuration /= 10;

        recipes.add(recipe);
    }

    public static void setDuration(GT_Recipe gtRecipe, int aDuration) {
        // gtRecipe.mDuration = aDuration;
        gtRecipe.mDuration = aDuration / 10;
    }

    public static void setInputs(GT_Recipe gtRecipe, ItemStack[] aInputs) {
        gtRecipe.mInputs = ArrayExt.withoutTrailingNulls(aInputs, ItemStack[]::new);
    }

    public static void setOutputs(GT_Recipe gtRecipe, ItemStack[] aOutputs) {
        gtRecipe.mOutputs = ArrayExt.withoutTrailingNulls(aOutputs, ItemStack[]::new);
    }

    public static void setFluidInputs(GT_Recipe gtRecipe, FluidStack[] aInputs) {
        gtRecipe.mFluidInputs = ArrayExt.withoutTrailingNulls(aInputs, FluidStack[]::new);
    }

    public static void setFluidOutputs(GT_Recipe gtRecipe, FluidStack[] aOutputs) {
        gtRecipe.mFluidOutputs = ArrayExt.withoutTrailingNulls(aOutputs, FluidStack[]::new);
    }

    public static void setEUt(GT_Recipe gtRecipe, int aEUt) {
        gtRecipe.mEUt = aEUt;
    }
}
