package com.github.wohaopa.GTNHModify.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

import com.github.wohaopa.GTNHModify.GTNHModifyMod;
import com.github.wohaopa.GTNHModify.strategies.Energyless;
import com.github.wohaopa.GTNHModify.strategies.None;
import com.github.wohaopa.GTNHModify.strategies.OneTick;
import com.github.wohaopa.GTNHModify.strategies.Output64;
import com.github.wohaopa.GTNHModify.strategies.Strategy;
import com.github.wohaopa.GTNHModify.strategies.Tenths;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class Config {

    public static Configuration config;

    public static Strategy strategy = new None();

    private static boolean doSave;

    public static void init(File configFile) {
        if (config == null) {
            config = new Configuration(configFile);
        }
        doSave = false;
        loadConfig();
        FMLCommonHandler.instance()
            .bus()
            .register(new Config());
    }

    @SubscribeEvent
    public void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equalsIgnoreCase(GTNHModifyMod.MODID)) {
            loadConfig();
        }
    }

    private static void loadConfig() {

        String strategyName = config.getString(
            "Strategy",
            Configuration.CATEGORY_GENERAL,
            "None",
            "Possible values: [None, OneTick, Tenths, Output64, Energyless]");

        switch (strategyName) {
            case "OneTick" -> strategy = new OneTick();
            case "Tenths" -> strategy = new Tenths();
            case "Output64" -> strategy = new Output64();
            case "Energyless" -> strategy = new Energyless();
            default -> strategy = new None();
        }

        if (config.hasChanged() || doSave) {
            config.save();

            doSave = false;
        }
    }

}
