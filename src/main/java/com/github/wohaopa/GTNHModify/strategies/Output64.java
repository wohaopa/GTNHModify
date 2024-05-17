package com.github.wohaopa.GTNHModify.strategies;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import gregtech.api.util.GT_Recipe;

public class Output64 extends Strategy {

    protected Output64() {}

    @Override
    public void handler_GT_Recipe(GT_Recipe gtRecipe) {
        if (gtRecipe.mEUt > 1) gtRecipe.mEUt = 1;
        if (gtRecipe.mDuration > 1) gtRecipe.mDuration = 1;
        if (gtRecipe.mInputs != null) {
            for (ItemStack itemStack : gtRecipe.mInputs) {
                if (itemStack != null) {
                    if (itemStack.stackSize > 0) itemStack.stackSize = 1;
                }
            }
        }
        if (gtRecipe.mOutputs != null) {
            for (ItemStack itemStack : gtRecipe.mOutputs) {
                if (itemStack != null) {
                    if (itemStack.stackSize > 0 && itemStack.stackSize < 64) itemStack.stackSize = 64;
                }
            }
        }
        if (gtRecipe.mFluidInputs != null) {
            for (FluidStack fluidStack : gtRecipe.mFluidInputs) {
                if (fluidStack != null) {
                    if (fluidStack.amount > 0) fluidStack.amount = 1;
                }
            }
        }
        if (gtRecipe.mFluidOutputs != null) {
            for (FluidStack fluidStack : gtRecipe.mFluidOutputs) {
                if (fluidStack != null) {
                    if (fluidStack.amount > 0 && fluidStack.amount < 16000)
                        fluidStack.amount = fluidStack.amount % 144 == 0 ? 144 * 64 : 16000;
                }
            }
        }
    }

    @Override
    public void handler_GT_Recipe_AssemblyLine(GT_Recipe.GT_Recipe_AssemblyLine gtRecipe) {
        if (gtRecipe.mEUt > 1) gtRecipe.mEUt = 1;
        if (gtRecipe.mDuration > 1) gtRecipe.mDuration = 1;
        if (gtRecipe.mInputs != null) {
            for (ItemStack itemStack : gtRecipe.mInputs) {
                if (itemStack != null) {
                    if (itemStack.stackSize > 0) itemStack.stackSize = 1;
                }
            }
        }
        if (gtRecipe.mOutput != null) {

            if (gtRecipe.mOutput.stackSize > 0 && gtRecipe.mOutput.stackSize < 64) gtRecipe.mOutput.stackSize = 64;

        }
        if (gtRecipe.mFluidInputs != null) {
            for (FluidStack fluidStack : gtRecipe.mFluidInputs) {
                if (fluidStack != null) {
                    if (fluidStack.amount > 0) fluidStack.amount = 1;
                }
            }
        }
    }

    @Override
    public int handler_GT_ProcessingTime(Object owner, int number) {
        return 1;
    }

    @Override
    public int handler_FurnaceProcessingTime(Object owner, int number) {
        return 1;
    }
}
