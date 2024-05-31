package com.github.wohaopa.GTNHModify;

public enum Mods {

    GregTech("gregtech"),
    Thaumcraft("Thaumcraft"),
    GtnhIntergalactic("gtnhintergalactic", false),
    Botania("Botania"),;

    public final String modid;
    private boolean isLoaded = false;
    private final boolean hasHandler;

    Mods(String modid) {
        this(modid, true);

    }

    Mods(String modid, boolean hasHandler) {
        this.modid = modid;
        this.hasHandler = hasHandler;
    }

    public String getHandler() {
        if (hasHandler) return null;
        return name();
    }

    public void setLoaded() {
        this.isLoaded = true;
    }

    public boolean isLoaded() {
        return isLoaded;
    }
}
