package com.github.wohaopa.GTNHModify.tweakers.handler;

import com.github.wohaopa.GTNHModify.tweakers.ITweaker;

public abstract class Handler extends ITweaker {

    private boolean enable = false;

    public int handle(Object owner, int number) {
        if (enable) return 1;
        else return number;
    }

    @Override
    protected void apply() {
        enable = true;
    }
}
