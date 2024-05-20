package com.github.wohaopa.GTNHModify.mixins;

import net.minecraft.tileentity.TileEntityFurnace;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import com.github.wohaopa.GTNHModify.handler.MinecraftHandler;

@Mixin(TileEntityFurnace.class)
public abstract class TileEntityFurnaceMixin {

    @ModifyConstant(method = "updateEntity", constant = @Constant(intValue = 200))
    private int injected(int value) {
        return MinecraftHandler.handle(this, value);
    }
}
