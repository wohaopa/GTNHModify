package com.github.wohaopa.GTNHModify.mixins.late;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import com.github.wohaopa.GTNHModify.handler.GT_RecipesHandler;

import gregtech.common.tileentities.machines.basic.GT_MetaTileEntity_Scanner;

@Mixin(value = GT_MetaTileEntity_Scanner.class, remap = false)
public abstract class GT_MetaTileEntity_ScannerMixin {

    @ModifyArg(method = "checkRecipe", at = @At(value = "INVOKE", target = "calculateOverclockedNess"), index = 1)
    private int injected(int x) {
        return GT_RecipesHandler.handle(this, x);
    }
}
