package com.github.wohaopa.GTNHModify.tweakers.gt;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import gregtech.api.util.GT_Recipe;

public class Output64 extends GT_RecipeTweaker {

    @Override
    protected void modifyGT_Recipe(GT_Recipe aRecipe) {
        if (aRecipe.mOutputs != null) {
            for (ItemStack itemStack : aRecipe.mOutputs) {
                if (itemStack != null) {
                    if (itemStack.stackSize > 0 && itemStack.stackSize < 64) itemStack.stackSize = 64;
                }
            }
        }

        if (aRecipe.mFluidOutputs != null) {
            for (FluidStack fluidStack : aRecipe.mFluidOutputs) {
                if (fluidStack != null) {
                    if (fluidStack.amount > 0 && fluidStack.amount < 16000)
                        fluidStack.amount = fluidStack.amount % 36 == 0 ? 9216 : 16000;
                }
            }
        }
    }

    @Override
    protected void modifyGT_Recipe_AssemblyLine(GT_Recipe.GT_Recipe_AssemblyLine aRecipe) {
        if (aRecipe.mOutput != null) {
            if (aRecipe.mOutput.stackSize > 0 && aRecipe.mOutput.stackSize < 64) aRecipe.mOutput.stackSize = 64;
        }
    }
}
