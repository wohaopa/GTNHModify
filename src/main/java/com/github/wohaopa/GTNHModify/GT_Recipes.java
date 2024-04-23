package com.github.wohaopa.GTNHModify;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import gregtech.api.util.GT_Recipe;

public class GT_Recipes {

    private static Set<GT_Recipe> recipes = new HashSet<>();

    public static void addRecipe(GT_Recipe recipe) {

        if (recipes.contains(recipe)) return;
        setDuration(recipe, recipe.mDuration);
        setInputs(recipe, recipe.mInputs);
        setOutputs(recipe, recipe.mOutputs);
        setFluidInputs(recipe, recipe.mFluidInputs);
        setFluidOutputs(recipe, recipe.mFluidOutputs);
        setEUt(recipe, recipe.mEUt);

        recipes.add(recipe);
    }

    public static void setDuration(GT_Recipe gtRecipe, int aDuration) {
        // gtRecipe.mDuration = aDuration;
        gtRecipe.mDuration = aDuration == 0 ? 0 : 1;
    }

    public static void setInputs(GT_Recipe gtRecipe, ItemStack[] aInputs) {
        gtRecipe.mInputs = aInputs;
        for (ItemStack itemStack : aInputs) {
            if (itemStack == null) continue;
            if (itemStack.stackSize > 0) itemStack.stackSize = 1;
        }
    }

    public static void setOutputs(GT_Recipe gtRecipe, ItemStack[] aOutputs) {
        gtRecipe.mOutputs = aOutputs;
        for (ItemStack itemStack : aOutputs) {
            if (itemStack == null) continue;
            if (itemStack.stackSize < 64) itemStack.stackSize = 64;
        }
    }

    public static void setFluidInputs(GT_Recipe gtRecipe, FluidStack[] aInputs) {
        gtRecipe.mFluidInputs = aInputs;
        for (FluidStack fluidStack : aInputs) {
            if (fluidStack == null) continue;
            if (fluidStack.amount > 0) fluidStack.amount = 1;
        }
    }

    public static void setFluidOutputs(GT_Recipe gtRecipe, FluidStack[] aOutputs) {
        gtRecipe.mFluidOutputs = aOutputs;

        for (FluidStack fluidStack : aOutputs) {
            if (fluidStack == null) continue;
            if (fluidStack.amount < 9216) fluidStack.amount = 9216;
        }
    }

    public static void setEUt(GT_Recipe gtRecipe, int aEUt) {
        // gtRecipe.mEUt = aEUt;
        gtRecipe.mEUt = aEUt == 0 ? 0 : 1;
    }
}
