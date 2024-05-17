package com.github.wohaopa.GTNHModify;

import net.minecraftforge.client.ClientCommandHandler;

import com.github.wohaopa.GTNHModify.client.command.GTNHModifyCommand;

import cpw.mods.fml.common.event.FMLInitializationEvent;

public class ClientProxy extends CommonProxy {

    // Override CommonProxy methods here, if you want a different behaviour on the client (e.g. registering renders).
    // Don't forget to call the super methods as well.

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);

        ClientCommandHandler.instance.registerCommand(new GTNHModifyCommand());
    }
}
