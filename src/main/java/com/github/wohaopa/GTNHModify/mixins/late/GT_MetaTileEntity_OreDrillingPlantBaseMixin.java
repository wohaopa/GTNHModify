package com.github.wohaopa.GTNHModify.mixins.late;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.github.wohaopa.GTNHModify.handler.GT_RecipesHandler;

import gregtech.common.tileentities.machines.multi.GT_MetaTileEntity_OreDrillingPlantBase;

@Mixin(value = GT_MetaTileEntity_OreDrillingPlantBase.class, remap = false)
public abstract class GT_MetaTileEntity_OreDrillingPlantBaseMixin {

    @Redirect(
        method = "setElectricityStats",
        at = @At(
            value = "INVOKE",
            target = "Lgregtech/common/tileentities/machines/multi/GT_MetaTileEntity_OreDrillingPlantBase;calculateMaxProgressTime(I)I"))
    private int injected(GT_MetaTileEntity_OreDrillingPlantBase drillingPlant, int x) {
        return GT_RecipesHandler.handle(drillingPlant, x);
    }
}
