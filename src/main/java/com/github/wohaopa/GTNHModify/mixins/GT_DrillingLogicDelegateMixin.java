package com.github.wohaopa.GTNHModify.mixins;

import org.spongepowered.asm.mixin.Mixin;

import gregtech.common.misc.GT_DrillingLogicDelegate;

@Mixin(value = GT_DrillingLogicDelegate.class, remap = false)
public abstract class GT_DrillingLogicDelegateMixin {

    // @Redirect(
    // method = "onPostTickRetract",
    // at = @At(value = "INVOKE", target = "Lgregtech/common/misc/GT_IDrillingLogicDelegateOwner;getMachineSpeed()I"))
    // private int injected(GT_IDrillingLogicDelegateOwner owner) {
    // return 100;
    // }

}
