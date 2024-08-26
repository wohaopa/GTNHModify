package com.github.wohaopa.GTNHModify.mixins.late.gregtech;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.wohaopa.GTNHModify.tweakers.handler.GregTechHandler;

import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.common.tileentities.machines.multi.GT_MetaTileEntity_DrillerBase;

@Mixin(value = GT_MetaTileEntity_DrillerBase.class, remap = false)
public abstract class GT_MetaTileEntity_DrillerBaseMixin {

    @Inject(
        method = "checkProcessing",
        at = @At(
            value = "INVOKE",
            target = "Lgregtech/common/tileentities/machines/multi/GT_MetaTileEntity_DrillerBase;setElectricityStats()V",
            shift = At.Shift.AFTER))
    private void injected(CallbackInfoReturnable<CheckRecipeResult> cir) {

        ((GT_MetaTileEntity_DrillerBase) ((Object) this)).mMaxProgresstime = GregTechHandler.instance
            .handle(this, ((GT_MetaTileEntity_DrillerBase) ((Object) this)).mMaxProgresstime);
    }
}
