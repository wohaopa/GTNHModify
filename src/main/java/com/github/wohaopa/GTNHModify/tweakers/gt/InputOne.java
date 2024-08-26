package com.github.wohaopa.GTNHModify.tweakers.gt;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import gregtech.api.util.GT_Recipe;

public class InputOne extends GT_RecipeTweaker {

    @Override
    protected void modifyGT_Recipe(GT_Recipe aRecipe) {
        if (aRecipe.mInputs != null) {
            for (ItemStack itemStack : aRecipe.mInputs) {
                if (itemStack != null) {
                    if (itemStack.stackSize > 0) itemStack.stackSize = 1;
                }
            }
        }
        if (aRecipe.mFluidInputs != null) {
            for (FluidStack fluidStack : aRecipe.mFluidInputs) {
                if (fluidStack != null) {
                    if (fluidStack.amount > 0) fluidStack.amount = 1;
                }
            }
        }
    }

    @Override
    protected void modifyGT_Recipe_AssemblyLine(GT_Recipe.GT_Recipe_AssemblyLine aRecipe) {
        if (aRecipe.mInputs != null) {
            for (ItemStack itemStack : aRecipe.mInputs) {
                if (itemStack != null) {
                    if (itemStack.stackSize > 0) itemStack.stackSize = 1;
                }
            }
        }
        if (aRecipe.mFluidInputs != null) {
            for (FluidStack fluidStack : aRecipe.mFluidInputs) {
                if (fluidStack != null) {
                    if (fluidStack.amount > 0) fluidStack.amount = 1;
                }
            }
        }
    }
}
