package com.github.wohaopa.GTNHModify.client.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

import com.github.wohaopa.GTNHModify.GTNHModifyMod;
import com.github.wohaopa.GTNHModify.config.Config;

import cpw.mods.fml.client.config.GuiConfig;

public class GTNHModifyGuiConfig extends GuiConfig {

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public GTNHModifyGuiConfig(GuiScreen guiScreen) {
        super(
            guiScreen,
            new ConfigElement(Config.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
            GTNHModifyMod.MODID,
            true,
            false,
            GuiConfig.getAbridgedConfigPath(Config.config.toString()));
    }
}
