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
        recipe.mEUt = 1;
        recipe.mDuration = 1;
        for (ItemStack itemStack : recipe.mOutputs) {
            if (itemStack == null) continue;
            itemStack.stackSize = 64;
        }
        for (ItemStack itemStack : recipe.mInputs) {
            if (itemStack == null) continue;
            itemStack.stackSize = 1;
        }
        for (FluidStack fluidStack : recipe.mFluidOutputs) {
            if (fluidStack == null) continue;
            fluidStack.amount = 1;
        }
        for (FluidStack fluidStack : recipe.mFluidInputs) {
            if (fluidStack == null) continue;
            fluidStack.amount = 1;
        }

        recipes.add(recipe);
    }

    public static void setDuration(GT_Recipe gtRecipe, int aDuration) {
        // gtRecipe.mDuration = aDuration;
        gtRecipe.mDuration = 1;
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
        // gtRecipe.mEUt = aEUt;
        gtRecipe.mEUt = 1;
    }
}
