package com.github.wohaopa.GTNHModify.tweakers.gt;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import gregtech.api.util.GT_Recipe;

public class Output64 extends GT_RecipeTweaker {

    int integer = 64;

    @Override
    protected void modifyGT_Recipe(GT_Recipe aRecipe) {
        if (aRecipe.mOutputs != null) {
            for (ItemStack itemStack : aRecipe.mOutputs) {
                if (itemStack != null) {
                    if (itemStack.stackSize > 0 && itemStack.stackSize < integer) itemStack.stackSize = integer;
                }
            }
        }

        if (aRecipe.mFluidOutputs != null) {
            for (FluidStack fluidStack : aRecipe.mFluidOutputs) {
                if (fluidStack != null) {
                    if (fluidStack.amount > 0 && fluidStack.amount < integer * 250)
                        fluidStack.amount = fluidStack.amount % 36 == 0 ? 144 * integer : integer * 250;
                }
            }
        }
    }

    @Override
    protected void modifyGT_Recipe_AssemblyLine(GT_Recipe.GT_Recipe_AssemblyLine aRecipe) {
        if (aRecipe.mOutput != null) {
            if (aRecipe.mOutput.stackSize > 0 && aRecipe.mOutput.stackSize < integer)
                aRecipe.mOutput.stackSize = integer;
        }
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
