package com.github.wohaopa.GTNHModify;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.OreDictionary;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import gregtech.api.util.GT_Recipe;

public class DumperThread extends Thread {

    private static Gson gson = new GsonBuilder().setPrettyPrinting()
        .create();

    private static int SPLIT = 5;
    Function<String, Void> callback;

    public DumperThread(Function<String, Void> callback) {
        this.callback = callback;
        this.setName("GTNHModify Dumps Thread");
    }

    // 输入参数为json字符串和文件路径
    private void jsonToFile(JsonElement json, String path) {
        File file = new File(Minecraft.getMinecraft().mcDataDir, "GTNHModify/dumps/" + path + ".json");
        try {
            if (!file.getParentFile()
                .exists()) {
                file.getParentFile()
                    .mkdirs();
            }
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            // 将格式化后的字符串写入文件
            Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
            writer.write(gson.toJson(json));
            writer.flush();
            writer.close();
        } catch (Exception ignored) {}

    }

    private void dumpMinecraftRecipe() {
        JsonArray jsonArray = new JsonArray();
        Set<String> unknown = new HashSet<>();

        long a1 = System.currentTimeMillis();
        {
            List<IRecipe> recipes = new ArrayList<>(
                CraftingManager.getInstance()
                    .getRecipeList());

            int size = recipes.size();
            callback.apply("开始处理Minecraft配方！共计：" + size);
            int i = 0, step0 = size / SPLIT, step = step0;
            for (IRecipe recipe : recipes) {
                i++;
                if (i == step) {
                    callback.apply(String.format("已经完成：%d%%！", i * 100 / size));
                    step += step0;
                }
                JsonObject jsonObject = RecipeTools.recipeTOJsonObject(recipe);
                jsonArray.add(jsonObject);
                if (jsonObject.has("exception")) unknown.add(
                    jsonObject.get("category")
                        .getAsString());
            }
        }
        long a2 = System.currentTimeMillis();
        callback.apply(String.format("Minecraft配方处理完成！用时：%.4fs", (a2 - a1) / 1000.0));

        StringBuilder sb = new StringBuilder("未知类型：");
        for (String s : unknown) sb.append(s)
            .append("\n");

        jsonToFile(jsonArray, "minecraft");

        callback.apply(sb.toString());
    }

    private void dumpGregTechRecipe() {
        JsonArray jsonArray = new JsonArray();

        long a1 = System.currentTimeMillis();
        {
            int size = GT_Recipes.recipes.size();
            callback.apply("开始处理GregTech配方！共计：" + size);
            int i = 0, step0 = size / SPLIT, step = step0;
            for (GT_Recipe gtRecipe : GT_Recipes.recipes) {
                i++;
                if (i == step) {
                    callback.apply(String.format("已经完成：%d%%！", i * 100 / size));
                    step += step0;
                }
                jsonArray.add(RecipeTools.recipeTOJsonObject(gtRecipe));
            }
            long a2 = System.currentTimeMillis();
            callback.apply(String.format("GregTech配方处理完成！用时：%.4fs", (a2 - a1) / 1000.0));

            jsonToFile(jsonArray, "gregtech");

        }
    }

    private void dumpForgeOreDictionary() {
        JsonArray jsonArray = new JsonArray();

        long a1 = System.currentTimeMillis();
        {
            int size = OreDictionary.getOreNames().length;
            callback.apply("开始处理Forge矿物词典！共计：" + size);
            int i = 0, step0 = size / SPLIT, step = step0;
            for (String name : OreDictionary.getOreNames()) {
                i++;
                if (i == step) {
                    callback.apply(String.format("已经完成：%d%%！", i * 100 / size));
                    step += step0;
                }

                JsonObject jsonObject = new JsonObject();

                List<ItemStack> list = OreDictionary.getOres(name);
                RecipeTools.oreToName.put(list.hashCode(), name);

                jsonObject.addProperty("name", name);
                jsonObject.add("item", RecipeTools.itemStacksToJsonArray(list.toArray(new ItemStack[0])));
                jsonArray.add(jsonObject);
            }
        }
        long a2 = System.currentTimeMillis();
        callback.apply(String.format("Forge矿物词典处理完成！用时：%.4fs", (a2 - a1) / 1000.0));

        jsonToFile(jsonArray, "ore");
    }

    @Override
    public void run() {
        callback.apply("开始！");
        dumpForgeOreDictionary();
        dumpMinecraftRecipe();
        dumpGregTechRecipe();
        callback.apply("完成！");
    }
}
