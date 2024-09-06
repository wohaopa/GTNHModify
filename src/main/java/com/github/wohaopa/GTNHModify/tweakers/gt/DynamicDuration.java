package com.github.wohaopa.GTNHModify.tweakers.gt;

import com.github.wohaopa.GTNHModify.GTNHModifyMod;

import gregtech.api.util.GT_Recipe;

public class DynamicDuration extends GT_RecipeTweaker {

    public static DynamicDuration instance = new DynamicDuration();
    private static final Thread updateThread = new Thread(() -> {
        while (true) {
            if (DynamicDuration.needsUpdate) {
                GTNHModifyMod.LOG.info("Updating DynamicDuration: f = {}", instance.aFloat);
                long a = System.currentTimeMillis();
                instance.apply();
                DynamicDuration.needsUpdate = false;
                GTNHModifyMod.LOG.info("Updating DynamicDuration: use time {}ms", System.currentTimeMillis() - a);
            }
            Thread.yield();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });
    private static boolean running = false;
    private static boolean needsUpdate = false;

    static {
        updateThread.setDaemon(true);
        updateThread.setName("GTNHModify DynamicDuration UpdateThread");
    }

    public static void update() {
        needsUpdate = true;
        if (!running) {
            running = true;
            updateThread.start();
        }
    }

    public void setF(float f) {
        f0 = f / f0 * aFloat;
    }

    private float aFloat = 0.9f;
    private float f0 = 1f;

    @Override
    protected void modifyGT_Recipe(GT_Recipe aRecipe) {
        aRecipe.mDuration *= (int) f0;
        if (aRecipe.mDuration < 1) aRecipe.mDuration = 1;
    }

    @Override
    protected void modifyGT_Recipe_AssemblyLine(GT_Recipe.GT_Recipe_AssemblyLine aRecipe) {
        aRecipe.mDuration *= (int) f0;
        if (aRecipe.mDuration < 1) aRecipe.mDuration = 1;
    }

    @Override
    public Object getSettings() {
        return aFloat;
    }

    @Override
    public void setSetting(Object s) {
        aFloat = (float) s;
    }
}
