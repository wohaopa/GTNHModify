package com.github.wohaopa.GTNHModify.tweakers.handler;

import com.github.wohaopa.GTNHModify.tweakers.ITweaker;

public abstract class Handler extends ITweaker {

    private boolean enable = false;

    int integer = 1;

    public int handle(Object owner, int number) {
        if (enable) return integer;
        else return number;
    }

    @Override
    protected void apply() {
        enable = true;
    }

    @Override
    public Object getSettings() {
        return integer;
    }

    @Override
    public void setSetting(Object s) {
        integer = (int) s;
    }
}
