package com.github.wohaopa.GTNHModify.dumpers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;

import com.github.wohaopa.GTNHModify.DumpUtil;
import com.google.gson.JsonObject;

public class MinecraftDumper extends Dumper {

    public MinecraftDumper(Consumer<String> callback) {
        super("Minecraft", callback);
    }

    private void dumpWorldInfo() {
        callback.accept("世界信息导出");
        WorldServer[] worldServers = MinecraftServer.getServer().worldServers;

        callback.accept("共计: " + worldServers.length + "个世界");

        List<JsonObject> worlds = new ArrayList<>();
        for (WorldServer worldServer : worldServers) {
            JsonObject world = new JsonObject();
            world.addProperty("name", worldServer.provider.getDimensionName());
            world.addProperty("id", worldServer.provider.dimensionId);
            world.addProperty(
                "class",
                worldServer.provider.getClass()
                    .getName());
            worlds.add(world);
        }
        DumpUtil.writeFile("worlds.json", worlds);
    }

    @Override
    protected void dump() {
        dumpWorldInfo();
    }
}
