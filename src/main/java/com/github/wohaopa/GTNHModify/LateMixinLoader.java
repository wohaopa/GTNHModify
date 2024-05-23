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

        for (String modId : loadedMods) {
            if (modId.equals("gregtech")) {
                ModHelper.hasGregtech = true;
            } else if (modId.equals("Thaumcraft")) {
                ModHelper.hasThaumcraft = true;
            }
        }

        List<String> mixins = new ArrayList<>();

        if (ModHelper.hasGregtech) {
            // GregTech
            mixins.add("gregtech.GT_MetaTileEntity_ScannerMixin");
            mixins.add("gregtech.GT_MetaTileEntity_MinerMixin");
            mixins.add("gregtech.GT_MetaTileEntity_MultiFurnaceMixin");
            mixins.add("gregtech.GT_MetaTileEntity_DrillerBaseMixin");
        }
        if (ModHelper.hasThaumcraft) {
            mixins.add("thaumcraft.TileAlchemyFurnaceMixin");
        }

        return mixins;
    }
}
