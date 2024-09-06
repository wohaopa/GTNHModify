package com.github.wohaopa.GTNHModify.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

import com.github.wohaopa.GTNHModify.GTNHModifyMod;
import com.github.wohaopa.GTNHModify.tweakers.Tweakers;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class Config {

    public static Configuration config;

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

        for (Tweakers tweaker : Tweakers.values()) {
            tweaker.enabled = config
                .getBoolean(tweaker.name, Configuration.CATEGORY_GENERAL, false, tweaker.description);
            Object setting = tweaker.tweaker.getSettings();
            if (setting != null) {
                if (setting instanceof String) {
                    tweaker.tweaker.setSetting(
                        config.getString(
                            tweaker.name + "_setting",
                            Configuration.CATEGORY_GENERAL,
                            (String) setting,
                            tweaker.description));
                } else if (setting instanceof Boolean) {
                    tweaker.tweaker.setSetting(
                        config.getBoolean(
                            tweaker.name + "_setting",
                            Configuration.CATEGORY_GENERAL,
                            (Boolean) setting,
                            tweaker.description));
                } else if (setting instanceof Integer) {
                    tweaker.tweaker.setSetting(
                        config.getInt(
                            tweaker.name + "_setting",
                            Configuration.CATEGORY_GENERAL,
                            (int) setting,
                            Integer.MIN_VALUE,
                            Integer.MAX_VALUE,
                            tweaker.description));
                } else if (setting instanceof String[]) {
                    tweaker.tweaker.setSetting(
                        config.getStringList(
                            tweaker.name + "_setting",
                            Configuration.CATEGORY_GENERAL,
                            (String[]) setting,
                            tweaker.description));
                } else if (setting instanceof Float) {
                    tweaker.tweaker.setSetting(
                        config.getFloat(
                            tweaker.name + "_setting",
                            Configuration.CATEGORY_GENERAL,
                            (Float) setting,
                            Float.MIN_VALUE,
                            Float.MAX_VALUE,
                            tweaker.description));
                }

            }
        }

        if (config.hasChanged() || doSave) {
            config.save();
            doSave = false;
        }
    }

}
