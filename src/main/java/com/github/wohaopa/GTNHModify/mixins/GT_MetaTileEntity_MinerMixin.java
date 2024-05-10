package com.github.wohaopa.GTNHModify.mixins;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import gregtech.common.tileentities.machines.basic.GT_MetaTileEntity_Miner;

@Mixin(value = GT_MetaTileEntity_Miner.class, remap = false)
public class GT_MetaTileEntity_MinerMixin {

    @Mutable
    @Final
    @Shadow(remap = false)
    private int mSpeed;

    @Inject(method = "name=/bar$/ desc=/.+/", at = @At("TAIL"))
    public void injectInit(CallbackInfo callbackInfo) {
        mSpeed = 1;
    }
}
