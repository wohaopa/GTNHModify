package com.github.wohaopa.GTNHModify.mixins.late.thaumcraft;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.github.wohaopa.GTNHModify.tweakers.handler.ThaumcraftHandler;

import thaumcraft.common.tiles.TileNode;

@Mixin(value = TileNode.class, remap = false)
public abstract class TileNodeMixin {

    @Shadow(remap = false)
    int regeneration;

    @Redirect(
        method = "handleRecharge",
        at = @At(
            value = "FIELD",
            target = "Lthaumcraft/common/tiles/TileNode;regeneration:I",
            opcode = Opcodes.PUTFIELD))
    private void injectedHandleRecharge(TileNode tileNode, int regeneration) {
        this.regeneration = ThaumcraftHandler.instance.handle(tileNode, regeneration);
    }
}
