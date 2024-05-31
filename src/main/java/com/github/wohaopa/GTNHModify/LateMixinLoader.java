package com.github.wohaopa.GTNHModify;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.gtnewhorizon.gtnhmixins.ILateMixinLoader;
import com.gtnewhorizon.gtnhmixins.LateMixin;

@LateMixin
public class LateMixinLoader implements ILateMixinLoader {

    @Override
    public String getMixinConfig() {
        return "mixins.GTNHModify.late.json";
    }

    @Override
    public List<String> getMixins(Set<String> loadedMods) {

        for (Mods mod : Mods.values()) {
            if (loadedMods.contains(mod.modid)) mod.setLoaded();
        }

        List<String> mixins = new ArrayList<>();

        if (Mods.GregTech.isLoaded()) {
            // GregTech
            mixins.add("gregtech.GT_MetaTileEntity_ScannerMixin");
            mixins.add("gregtech.GT_MetaTileEntity_MinerMixin");
            mixins.add("gregtech.GT_MetaTileEntity_MultiFurnaceMixin");
            mixins.add("gregtech.GT_MetaTileEntity_DrillerBaseMixin");
        }
        if (Mods.Thaumcraft.isLoaded()) {
            mixins.add("thaumcraft.TileAlchemyFurnaceMixin");
            mixins.add("thaumcraft.TileNodeMixin");
        }
        if (Mods.GtnhIntergalactic.isLoaded()) {
            mixins.add("gtnhintergalactic.TileEntityModuleMinerMixin");
        }
        if (Mods.Botania.isLoaded()) {
            // mixins.add("botania.BotaniaMixin");
        }

        return mixins;
    }
}
