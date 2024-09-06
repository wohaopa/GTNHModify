package com.github.wohaopa.GTNHModify.tweakers;

import com.github.wohaopa.GTNHModify.tweakers.gt.DynamicDuration;
import com.github.wohaopa.GTNHModify.tweakers.gt.EnergyLessTweaker;
import com.github.wohaopa.GTNHModify.tweakers.gt.InputOne;
import com.github.wohaopa.GTNHModify.tweakers.gt.OneTickTweaker;
import com.github.wohaopa.GTNHModify.tweakers.gt.Output64;
import com.github.wohaopa.GTNHModify.tweakers.gt.TenthsTweaker;
import com.github.wohaopa.GTNHModify.tweakers.handler.BotaniaHandler;
import com.github.wohaopa.GTNHModify.tweakers.handler.GregTechHandler;
import com.github.wohaopa.GTNHModify.tweakers.handler.MinecraftHandler;
import com.github.wohaopa.GTNHModify.tweakers.handler.ThaumcraftHandler;

public enum Tweakers {

    // GregTech common
    OneTick("GT_Recipe OneTick", "GT_Recipe duration is 1 tick.", new OneTickTweaker()),
    Tenths("GT_Recipe Tenths", "GT_Recipe duration is one-tenth of the previous.", new TenthsTweaker()),
    EnergyLess("GT_Recipe EnergyLess", "GT_Recipe requires no energy.", new EnergyLessTweaker()),
    Input_1("GT_Recipe Input_1", "The input of GT_Recipe is 1.", new InputOne()),
    Output("GT_Recipe Input_64", "The output of GT_Recipe is 64.", new Output64()),
    Dynamic_Duration("GT_Recipe DynamicDuration", "The duration will calculate the multiplier based on real time",
        DynamicDuration.instance),
    Fusion("Fusion’s power on energy", "The power on energy is all set to 1", new FusionTweaker()),

    // GG Naquadah reactor
    GGNaquadahReactor("Other GGNaquadahReactor", "Ten times the burn time.", new GGNaquadahReactorTweaker()),
    // Homo
    EyeOfHarmonyRecipe("Other EyeOfHarmonyRecipeTweaker", "Some enhancement.", new EyeOfHarmonyRecipeTweaker()),

    // Handler

    Botania("Handle Botania", "The time of some devices was changed to 1 tick.", BotaniaHandler.instance),
    GregTech("Handle GregTech", "The time of some devices was changed to 1 tick", GregTechHandler.instance),
    Minecraft("Handle Minecraft", "The time of some devices was changed to 1 tick", MinecraftHandler.instance),
    Thaumcraft("Handle Thaumcraft", "The time of some devices was changed to 1 tick", ThaumcraftHandler.instance),
    // :(
    ;

    public static void initialize() {
        for (Tweakers tweaker : Tweakers.values()) {
            if (tweaker.enabled) {
                tweaker.tweaker.apply0();
            }
        }
    }

    public final String name;
    public final String description;
    public final ITweaker tweaker;
    public boolean enabled;

    Tweakers(String name, String description, ITweaker tweaker) {
        this.name = name;
        this.description = description;
        this.tweaker = tweaker;
    }
}
