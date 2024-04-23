package com.github.wohaopa.GTNHModify.mixins;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.wohaopa.GTNHModify.GT_Recipes;

import gregtech.api.util.GT_Recipe;
import gregtech.api.util.extensions.ArrayExt;

@Mixin(value = GT_Recipe.class, remap = false)
public abstract class GT_RecipeMixin {

    /**
     * @author 初夏同学
     * @reason hook
     */
    @Overwrite(remap = false)
    public GT_Recipe setInputs(ItemStack... aInputs) {
        GT_Recipes.setInputs((GT_Recipe) (Object) this, ArrayExt.withoutTrailingNulls(aInputs, ItemStack[]::new));
        return ((GT_Recipe) (Object) this);
    }

    /**
     * @author 初夏同学
     * @reason hook
     */
    @Overwrite(remap = false)
    public GT_Recipe setOutputs(ItemStack... aOutputs) {
        GT_Recipes.setOutputs((GT_Recipe) (Object) this, ArrayExt.withoutTrailingNulls(aOutputs, ItemStack[]::new));
        return ((GT_Recipe) (Object) this);
    }

    /**
     * @author 初夏同学
     * @reason hook
     */
    @Overwrite(remap = false)
    public GT_Recipe setFluidInputs(FluidStack... aInputs) {
        GT_Recipes.setFluidInputs((GT_Recipe) (Object) this, ArrayExt.withoutTrailingNulls(aInputs, FluidStack[]::new));

        return ((GT_Recipe) (Object) this);
    }

    /**
     * @author 初夏同学
     * @reason hook
     */
    @Overwrite(remap = false)
    public GT_Recipe setFluidOutputs(FluidStack... aOutputs) {
        GT_Recipes
            .setFluidOutputs((GT_Recipe) (Object) this, ArrayExt.withoutTrailingNulls(aOutputs, FluidStack[]::new));

        return ((GT_Recipe) (Object) this);
    }

    /**
     * @author 初夏同学
     * @reason hook
     */
    @Overwrite(remap = false)
    public GT_Recipe setDuration(int aDuration) {
        GT_Recipes.setDuration((GT_Recipe) (Object) this, aDuration);

        return ((GT_Recipe) (Object) this);
    }

    /**
     * @author 初夏同学
     * @reason hook
     */
    @Overwrite(remap = false)
    public GT_Recipe setEUt(int aEUt) {
        GT_Recipes.setEUt((GT_Recipe) (Object) this, aEUt);

        return ((GT_Recipe) (Object) this);
    }

    @Inject(method = "name=/bar$/ desc=/.+/", at = @At("TAIL"))
    public void injectInit(CallbackInfo callbackInfo) {
        GT_Recipes.addRecipe(((GT_Recipe) (Object) this));
    }
}
