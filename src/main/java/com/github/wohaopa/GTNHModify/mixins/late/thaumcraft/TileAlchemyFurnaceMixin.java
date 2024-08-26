package com.github.wohaopa.GTNHModify.mixins.late.thaumcraft;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.github.wohaopa.GTNHModify.tweakers.handler.ThaumcraftHandler;

import thaumcraft.common.tiles.TileAlchemyFurnace;

@Mixin(value = TileAlchemyFurnace.class, remap = false)
public abstract class TileAlchemyFurnaceMixin {

    @Redirect(
        method = "canSmelt",
        at = @At(
            value = "FIELD",
            target = "Lthaumcraft/common/tiles/TileAlchemyFurnace;smeltTime:I",
            opcode = Opcodes.PUTFIELD))
    private void injectedSmeltTime(TileAlchemyFurnace furnace, int smeltTime) {
        furnace.smeltTime = ThaumcraftHandler.instance.handle(furnace, smeltTime);
    }
}
