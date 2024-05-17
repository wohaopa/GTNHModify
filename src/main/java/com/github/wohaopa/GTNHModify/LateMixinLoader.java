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
        List<String> mixins = new ArrayList<>();
        // GregTech
        mixins.add("GT_MetaTileEntity_ScannerMixin");
        mixins.add("GT_MetaTileEntity_MinerMixin");
        mixins.add("GT_MetaTileEntity_MultiFurnaceMixin");

        return mixins;
    }
}
