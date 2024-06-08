package com.github.wohaopa.GTNHModify.dumpers;

import java.util.function.Consumer;

public abstract class Dumper implements Runnable {

    protected final Consumer<String> callback;
    private final String name;

    public Dumper(String name, Consumer<String> callback) {

        this.callback = callback;
        this.name = "[" + name + "] ";
    }

    @Override
    public void run() {
        callback.accept(name + "开始");
        long start = System.currentTimeMillis();
        this.dump();
        long end = System.currentTimeMillis();
        callback.accept(name + "结束, 用时: " + (end - start) + "ms");
    }

    protected abstract void dump();
}
