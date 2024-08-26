package com.github.wohaopa.GTNHModify.mixins.late.gregtech;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import com.github.wohaopa.GTNHModify.tweakers.handler.GregTechHandler;

import gregtech.common.tileentities.machines.multi.GT_MetaTileEntity_MultiFurnace;

@Mixin(value = GT_MetaTileEntity_MultiFurnace.class, remap = false)
public class GT_MetaTileEntity_MultiFurnaceMixin {

    @ModifyConstant(method = "checkProcessing", constant = @Constant(intValue = 512))
    private int injected(int value) {
        return GregTechHandler.instance.handle(this, value);
    }
}
