package com.github.wohaopa.GTNHModify.mixins.late.botania;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.github.wohaopa.GTNHModify.tweakers.handler.BotaniaHandler;

import vazkii.botania.common.block.tile.mana.TileSpreader;

@Mixin(value = TileSpreader.class, remap = false)
public abstract class TileSpreaderMixin {

    @Redirect(
        method = "pingback",
        at = @At(
            value = "FIELD",
            target = "Lvazkii/botania/common/block/tile/mana/TileSpreader;pingbackTicks:I",
            opcode = Opcodes.PUTFIELD))
    private void injectedPingback(TileSpreader tileSpreader, int pingbackTicks) {
        tileSpreader.pingbackTicks = BotaniaHandler.instance.handle(tileSpreader, pingbackTicks);
    }
}
