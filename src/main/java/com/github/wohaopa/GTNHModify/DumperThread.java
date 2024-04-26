package com.github.wohaopa.GTNHModify;

import java.util.function.Function;

public class DumperThread extends Thread {

    private static boolean flag;

    Function<String, Void> callback;

    public DumperThread(Function<String, Void> callback) {
        if (flag) throw new RuntimeException("重复线程！");
        synchronized (this) {
            flag = true;
        }
        this.callback = callback;
        this.setName("GTNHModify Dumps Thread");
    }

    // 输入参数为json字符串和文件路径

    @Override
    public void run() {
        callback.apply("开始！");
        RecipeTools.dumpItems(callback);
        RecipeTools.dumpForgeOreDictionary(callback);
        RecipeTools.dumpMinecraftRecipe(callback);
        RecipeTools.dumpGregTechRecipe(callback);
        callback.apply("完成！");

        synchronized (this) {
            flag = false;
        }
    }
}
