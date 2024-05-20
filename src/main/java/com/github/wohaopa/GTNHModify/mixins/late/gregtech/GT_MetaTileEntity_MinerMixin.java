package com.github.wohaopa.GTNHModify.mixins.late.gregtech;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.github.wohaopa.GTNHModify.handler.GregTechHandler;

import gregtech.common.tileentities.machines.basic.GT_MetaTileEntity_Miner;

@Mixin(value = GT_MetaTileEntity_Miner.class, remap = false)
public class GT_MetaTileEntity_MinerMixin {

    @Mutable
    @Final
    @Shadow(remap = false)
    private int mSpeed;

    @Redirect(
        method = "onPostTick",
        at = @At(
            value = "FIELD",
            target = "Lgregtech/common/tileentities/machines/basic/GT_MetaTileEntity_Miner;mSpeed:I",
            opcode = Opcodes.GETFIELD))
    private int injected(GT_MetaTileEntity_Miner miner) {
        return GregTechHandler.handle(miner, mSpeed);
    }
}
