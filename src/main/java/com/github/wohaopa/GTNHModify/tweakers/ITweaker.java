package com.github.wohaopa.GTNHModify.tweakers;

public abstract class ITweaker {

    private boolean done = false;

    public void apply0() {
        if (done) return;
        done = true;
        apply();
    }

    protected abstract void apply();

    void exportRecipe() {}

    public Object getSettings() {
        return null;
    }
}
